package com.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbItemCatMapper;
import com.shop.pojo.EUTreeNode;
import com.shop.pojo.TbItemCat;
import com.shop.pojo.TbItemCatExample;
import com.shop.pojo.TbItemCatExample.Criteria;
import com.shop.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	//根据parentId查询，返回一个EuTreenode的列表
	@Override
	public List<EUTreeNode> getItemCatList(long parentId) {
		//创建example
		TbItemCatExample example =new TbItemCatExample();
		//创建条件，SELECT * FROM `tb_item_cat` where parent_id=父节点id;
		Criteria c=example.createCriteria();
		c.andParentIdEqualTo(parentId);
		//查询出一些item
		List<TbItemCat> list=tbItemCatMapper.selectByExample(example);
		//用来存放叶子节点列表
		List<EUTreeNode> resultTreeList=new ArrayList<>();
		//遍历刚才查到的商品列表
		for(TbItemCat tc:list){
			//将其封装成一个node结点，如果是父亲节点，那就closed，叶子节点就open
			EUTreeNode node = new EUTreeNode(tc.getId(), tc.getName(), 
					tc.getIsParent()?"closed":"open");
			//加入结点集合中去
			resultTreeList.add(node);
		}
		return resultTreeList;
		
		
	}

}
