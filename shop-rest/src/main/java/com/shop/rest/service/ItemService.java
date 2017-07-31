package com.shop.rest.service;

import com.shop.pojo.ShopResult;
/**
 * 商品信息展示接口
 * @author Dan
 *
 */
public interface ItemService {
	ShopResult getItemBaseInfo(long itemId);
	ShopResult getItemDesc(long itemId);
	ShopResult getItemParam(long itemId);
}
