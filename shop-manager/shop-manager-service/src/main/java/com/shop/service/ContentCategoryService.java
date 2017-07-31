package com.shop.service;

import java.util.List;

import com.shop.pojo.EUTreeNode;
import com.shop.pojo.ShopResult;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);
	ShopResult insertContentCategory(long parentId,String name);
	ShopResult deleteContentCategory(long id);
	ShopResult updateContentCategory(long id,String name);
}
