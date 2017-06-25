package com.shop.service;

import java.util.List;

import com.shop.pojo.EUTreeNode;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);

}
