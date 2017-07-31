package com.shop.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbItemDescMapper;
import com.shop.mapper.TbItemMapper;
import com.shop.mapper.TbItemParamItemMapper;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;
import com.shop.pojo.TbItemParam;
import com.shop.pojo.TbItemParamItem;
import com.shop.pojo.TbItemParamItemExample;
import com.shop.pojo.TbItemParamItemExample.Criteria;
import com.shop.rest.dao.JedisClient;
import com.shop.rest.service.ItemService;
import com.shop.utils.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;

	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public ShopResult getItemBaseInfo(long itemId) {
		// 添加缓存不能影响正常逻辑，所以要加try-catch块
		try {
			// 添加缓存逻辑
			// 从缓存中根据id取出商品信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json串转换成Java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return ShopResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有，则在数据库中查询
		// 根据id查询商品描述

		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		try {
			// 查询之后存入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
			// 设置key的有效时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用shopResult包装下
		return ShopResult.ok(item);
	}

	@Override
	public ShopResult getItemDesc(long itemId) {
		// 添加缓存不能影响正常逻辑，所以要加try-catch块
		try {
			// 添加缓存逻辑
			// 从缓存中根据id取出商品信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json串转换成Java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return ShopResult.ok(itemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有，则在数据库中查询
		// 根据id查询商品信息

		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		try {
			// 查询之后存入缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
			// 设置key的有效时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用shopResult包装下
		return ShopResult.ok(itemDesc);
	}

	@Override
	public ShopResult getItemParam(long itemId) {
		// 添加缓存不能影响正常逻辑，所以要加try-catch块
		try {
			// 添加缓存逻辑
			// 从缓存中根据id取出商品信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			// 判断是否有值
			if (!StringUtils.isBlank(json)) {
				// 把json串转换成Java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return ShopResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有，则在数据库中查询
		// 根据id查询商品规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria c = example.createCriteria();
		c.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				// 查询之后存入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				// 设置key的有效时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ShopResult.ok(paramItem);
		}

		// 使用shopResult包装下
		return ShopResult.build(400, "无此商品规格");
	}

}
