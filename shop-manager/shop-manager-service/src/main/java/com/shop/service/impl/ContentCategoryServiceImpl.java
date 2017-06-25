package com.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbContentCategoryMapper;
import com.shop.pojo.EUTreeNode;
import com.shop.pojo.TbContentCategory;
import com.shop.pojo.TbContentCategoryExample;
import com.shop.pojo.TbContentCategoryExample.Criteria;
import com.shop.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据id查询子结点
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria c=example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<EUTreeNode> resultList=new ArrayList<>();
		List<TbContentCategory> list=contentCategoryMapper.selectByExample(example);
		//返回一个node列表
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node=new EUTreeNode(tbContentCategory.getId(),
					tbContentCategory.getName(), tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

}
