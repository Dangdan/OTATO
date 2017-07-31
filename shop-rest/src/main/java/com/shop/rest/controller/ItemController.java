package com.shop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Url：/rest/item/info/{itemId}
 * @author Dan
 *
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.ShopResult;
import com.shop.rest.service.ItemService;
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public ShopResult getItemBaseInfo(@PathVariable Long itemId){
		ShopResult itemResult = itemService.getItemBaseInfo(itemId);
		return itemResult;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public ShopResult getItemDesc(@PathVariable Long itemId){
		ShopResult itemResult = itemService.getItemDesc(itemId);
		return itemResult;
	}
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public ShopResult getItemParam(@PathVariable Long itemId){
		ShopResult itemResult = itemService.getItemParam(itemId);
		return itemResult;
	}
}
