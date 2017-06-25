package com.shop.service;

import java.util.List;

import com.shop.pojo.EUGridResult;
import com.shop.pojo.EUTreeNode;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItem;

public interface ItemService {
	TbItem getTBItemById(long itemId);
	EUGridResult getItemList(int page,int rows);
	ShopResult createItem(TbItem item, String desc, String itemParam) throws Exception;
}
