package com.shop.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.rest.dao.JedisClient;
import com.shop.rest.service.JedisService;
import com.shop.utils.ExceptionUtil;

import redis.clients.jedis.Jedis;
@Service
public class JedisServiceImpl implements JedisService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public ShopResult syncContent(long contentCid) {
		try {
			//删除缓存中的内容，进行缓存清空
			jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid+"");
		} catch (Exception e) {
			
			e.printStackTrace();
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return ShopResult.ok();
	}

}
