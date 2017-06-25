package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.service.ItemParamItemService;

@Controller
public class ItemParamItemController {
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showitem/{itemid}")
	public String showItemParamItem(@PathVariable Long itemid,Model model){
		String result=itemParamItemService.getItemItemParamItem(itemid);
		model.addAttribute("itemParam", result);
		return "item";
		
	}

}
