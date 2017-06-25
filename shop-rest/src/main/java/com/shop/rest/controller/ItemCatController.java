package com.shop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shop.rest.pojo.CatResult;
import com.shop.rest.service.ItemCatService;
import com.shop.utils.JsonUtils;

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	//设置编码，防止乱码
//	@RequestMapping(value="/itemcat/list", 
//			produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public String getItemCatList(String callback){
//		CatResult list=itemCatService.getTbItemCatList();
//		String json=JsonUtils.objectToJson(list);
//		String result=callback+"("+json+");";
//		return result;
//	}
	@RequestMapping(value="/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		CatResult list=itemCatService.getTbItemCatList();
		MappingJacksonValue mjv=new MappingJacksonValue(list);
		mjv.setJsonpFunction(callback);
		return mjv;
	}
	
}
