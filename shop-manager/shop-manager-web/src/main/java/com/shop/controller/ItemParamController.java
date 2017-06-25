package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItemParam;
import com.shop.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemcatid}")
	@ResponseBody	
	public ShopResult getItemParamByCid(@PathVariable Long itemcatid){
		return itemParamService.getItemParamByCid(itemcatid);
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public ShopResult insertItemParam(@PathVariable Long cid,String paramData){
		//创建对象，接受前台传来的数据
		TbItemParam itemParam=new TbItemParam();
		//设置id和数据
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		//插入
		return itemParamService.insertItemParam(itemParam);
		
	}
}
