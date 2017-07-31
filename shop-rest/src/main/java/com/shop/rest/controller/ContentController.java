package com.shop.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbContent;
import com.shop.pojo.TbContentExample;
import com.shop.pojo.TbContentExample.Criteria;
import com.shop.rest.service.ContentService;
import com.shop.utils.ExceptionUtil;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public ShopResult getContentList(@PathVariable Long contentCategoryId) {
		try {
			List<TbContent> list=contentService.getContentList(contentCategoryId);
			return ShopResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
