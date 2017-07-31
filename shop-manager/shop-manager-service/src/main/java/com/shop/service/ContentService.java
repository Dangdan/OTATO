package com.shop.service;

import java.util.List;

import com.shop.pojo.EUGridResult;
import com.shop.pojo.EUTreeNode;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbContent;

public interface ContentService {
	EUGridResult getContentList(int page,int rows,long categoryId);
	ShopResult insertContent(TbContent tbContent);
	ShopResult deleteContent(long id);
	ShopResult updateContent(long id,String name);
}
