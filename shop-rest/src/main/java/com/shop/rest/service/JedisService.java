package com.shop.rest.service;

import com.shop.pojo.ShopResult;

public interface JedisService {
	ShopResult syncContent(long contentCid);
}
