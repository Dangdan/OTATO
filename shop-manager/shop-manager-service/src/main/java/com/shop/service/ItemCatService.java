package com.shop.service;

import java.util.List;

import com.shop.pojo.EUTreeNode;
import com.shop.pojo.TbItemCat;

public interface ItemCatService {
	public List<EUTreeNode> getItemCatList(long parentId);
}
