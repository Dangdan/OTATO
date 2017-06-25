package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 内容分类管理
 * @author Dan
 *
 */
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.EUTreeNode;
import com.shop.service.ContentCategoryService;
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0")Long parentId){
		return contentCategoryService.getCategoryList(parentId);
	}

}
