package com.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.mapper.TbItemDescMapper;
import com.shop.mapper.TbItemMapper;
import com.shop.mapper.TbItemParamItemMapper;
import com.shop.mapper.TbItemParamMapper;
import com.shop.pojo.EUGridResult;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;
import com.shop.pojo.TbItemExample;
import com.shop.pojo.TbItemExample.Criteria;
import com.shop.pojo.TbItemParam;
import com.shop.pojo.TbItemParamItem;
import com.shop.service.ItemService;
import com.shop.utils.IDUtils;

/**
 * 商品管理Service
 * 
 * @author Dan
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	// 将item的代理接口注入进来
	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public TbItem getTBItemById(long itemId) {
		// 直接根据主键查询
		// return itemMapper.selectByPrimaryKey(itemId);

		// 根据条件查询
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}

		return null;
	}

	// 根据页码和显示条数返回一个结果
	@Override
	public EUGridResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		EUGridResult er = new EUGridResult();
		er.setRows(list);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		er.setTotal(pageInfo.getTotal());
		return er;
	}

	@Override
	public ShopResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		// id没有，需要生成商品id
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 状态没有，需要设置为1 1->正常 2->下架 3->删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//商品基本信息 插入到数据库
		itemMapper.insert(item);

		// 添加商品描述信息
		ShopResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//规格参数
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return ShopResult.ok();
	}
	//插入商品描述
	private ShopResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return ShopResult.ok();
	}

	//插入商品规格参数
	private ShopResult insertItemParamItem(Long itemId, String itemParam) {
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setParamData(itemParam);
		tbItemParamItem.setCreated(new Date());
		tbItemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(tbItemParamItem);
		return ShopResult.ok();
	}


}
