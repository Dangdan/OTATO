package com.shop.portal.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.portal.pojo.SearchResult;
import com.shop.portal.service.SearchService;
import com.shop.utils.HttpClientUtil;
@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	

	@Override
	public SearchResult search(String query, int page) {
		
		//封装查询参数
		Map<String,String> param=new HashMap<>();
		param.put("q", query);
		param.put("page", page+"");
		try {
			//使用httpclient调用shop-search的服务
			String json=HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			//把查询到的字符串数据转换成shopResult的Java对象
			ShopResult result=ShopResult.formatToPojo(json, SearchResult.class);
			if(result.getStatus()==200){
				SearchResult searchResult=(SearchResult) result.getData();
				return searchResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
