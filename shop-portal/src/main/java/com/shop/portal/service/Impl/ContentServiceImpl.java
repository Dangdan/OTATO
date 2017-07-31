package com.shop.portal.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbContent;
import com.shop.portal.service.ContentService;
import com.shop.utils.HttpClientUtil;
import com.shop.utils.JsonUtils;

/**
 * 调用服务层服务
 * @author Dan
 *
 */
@Service
public class ContentServiceImpl implements ContentService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@Override
	public String getContentList() {
		//调用服务层的服务，得到结果
		String result=HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		try {
			//转换成shopResult
			ShopResult shopResult=ShopResult.formatToList(result, TbContent.class);
			//取内容列表
			List<TbContent> list=(List<TbContent>) shopResult.getData();
			List<Map> resultList=new ArrayList<>();
			//创建一个jsp页码要求的pojo列表
			for (TbContent tbContent : list) {
				Map map=new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("heightB", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
				
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
