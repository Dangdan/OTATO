package com.shop.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbContentMapper;
import com.shop.pojo.TbContent;
import com.shop.pojo.TbContentExample;
import com.shop.pojo.TbContentExample.Criteria;
import com.shop.rest.dao.JedisClient;
import com.shop.rest.service.ContentService;
import com.shop.utils.JsonUtils;
/**
 * 
 * @author Dan
 *
 */
@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	//根据内容分类id查询内容列表
	@Override
	public List<TbContent> getContentList(long contentCid) {
		//从缓存中取内容
		try {
			String result=jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid+"");
			if(!StringUtils.isBlank(result)){
				//把字符串转换成list
				List<TbContent>  resultList=JsonUtils.jsonToList(result, TbContent.class);
				return resultList;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//从数据库中取内容
		TbContentExample tbContentExample=new TbContentExample();
		Criteria c=tbContentExample.createCriteria();
		c.andCategoryIdEqualTo(contentCid);
		List<TbContent> list= tbContentMapper.selectByExample(tbContentExample);
		
		//向缓存中添加内容
		try {
			//把list转换成字符串
			String cacheString=JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid+"", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
