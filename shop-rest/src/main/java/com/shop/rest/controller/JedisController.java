package com.shop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.pojo.ShopResult;
import com.shop.rest.service.JedisService;

@Controller
@RequestMapping("/cache/sync")
public class JedisController {

	@Autowired
	private JedisService jedisService;
	
	@RequestMapping("/content/{contentCid}")
	public ShopResult contentCacheSync(@PathVariable Long contentCid){
		ShopResult result=jedisService.syncContent(contentCid);
		return result;
	}
}
