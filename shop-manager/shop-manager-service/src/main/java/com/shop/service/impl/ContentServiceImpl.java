package com.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.shop.mapper.TbContentMapper;
import com.shop.pojo.EUGridResult;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbContent;
import com.shop.pojo.TbContentExample;
import com.shop.pojo.TbContentExample.Criteria;
import com.shop.service.ContentService;
import com.shop.utils.HttpClientUtil;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public ShopResult insertContent(TbContent tbContent) {
		// 补全pojo
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insert(tbContent);
		
		//添加缓存逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+tbContent.getCategoryId());
		} catch (Exception e) {
			//异常处理，通知管理员
			e.printStackTrace();
		}
		
		return ShopResult.ok();
	}
	@Override
	public ShopResult deleteContent(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ShopResult updateContent(long id, String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EUGridResult getContentList(int page, int rows, long categoryId) {
		{
			TbContentExample example = new TbContentExample();
			Criteria c = example.createCriteria();
			c.andCategoryIdEqualTo(categoryId);
			List<TbContent> list = tbContentMapper.selectByExample(example);
			EUGridResult er = new EUGridResult();
			er.setRows(list);
			PageInfo<TbContent> pageInfo = new PageInfo<>(list);
			er.setTotal(pageInfo.getTotal());
			return er;
		}

	}

}
