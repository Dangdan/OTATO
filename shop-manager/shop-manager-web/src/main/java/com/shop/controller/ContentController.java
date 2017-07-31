package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.EUGridResult;
import com.shop.pojo.EUTreeNode;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbContent;
import com.shop.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUGridResult getContendList(Integer page,Integer rows,Long categoryId) {
		EUGridResult euGridResult=contentService.getContentList(page, rows, categoryId);
		return euGridResult;
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public ShopResult insertContend(TbContent tbContent) {
		ShopResult shopResult=contentService.insertContent(tbContent);
		return shopResult;
	}
	

}
