package com.shop.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.portal.pojo.ItemInfo;
import com.shop.portal.service.ItemService;
/**
 * 接收商品页面传递的参数，调用service，返回逻辑视图
 * @author Dan
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//商品基本信息
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId,Model model){
		ItemInfo itemInfo = itemService.getItemById(itemId);
		model.addAttribute("item", itemInfo);
		//返回逻辑视图
		return "item";
	}
	//商品描述，返回一个html的字符串片段
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	public String getItemDesc(@PathVariable Long itemId){
		String itemDesc = itemService.getItemDescById(itemId);
		return itemDesc;
	}
	
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	public String getItemParam(@PathVariable Long itemId){
		String itemParam = itemService.getItemParam(itemId);
		return itemParam;
	}

}
