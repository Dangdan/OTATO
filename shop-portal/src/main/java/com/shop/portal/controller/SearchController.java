package com.shop.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.portal.pojo.SearchResult;
import com.shop.portal.service.SearchService;
/**
 * 接受前台参数，调用后台服务返回数据
 * @author Dan
 *
 */
@Controller
public class SearchController {
	@Autowired 
	private SearchService searchService;
	//接收前台传来的参数，为了防止空指针异常，这里为page设置默认值
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
			@RequestParam(defaultValue="1")Integer page,Model model){
		if(queryString!=null){
			try {
				queryString=new String(queryString.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		//将参数返回
		SearchResult result=searchService.search(queryString, page);
		//为用户回显查询条件
		model.addAttribute("query", queryString);
		//总记录天数
		model.addAttribute("totalPages", result.getRecordCount());
		//商品列表
		model.addAttribute("itemList", result.getItemList());
		//当前页码
		model.addAttribute("page", page);
		//返回逻辑视图
		return "search";
	}
}
