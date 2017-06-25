package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.EUGridResult;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItem;
import com.shop.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody  //这样会返回一个json数据,方法中返回的TbItem对象会将其包装成一个json对象
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem item=itemService.getTBItemById(itemId);
		return item;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EUGridResult getItemList(Integer page, Integer rows) {
		EUGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public ShopResult createItem(TbItem item,String desc,String itemParams) throws Exception{
		return itemService.createItem(item, desc, itemParams);
	}
}
