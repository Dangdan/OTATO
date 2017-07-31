package com.shop.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.ShopResult;
import com.shop.search.service.ItemService;
/**
 * 索引库维护，导入所有商品数据到索引库
 * @author Dan
 *
 */
@Controller
@RequestMapping("/manager/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/importAll")
	@ResponseBody
	public ShopResult importAllItems(){
		ShopResult result=itemService.importAllItems();
		return result;
	}
	
}
