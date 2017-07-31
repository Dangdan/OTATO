package com.shop.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbItemCatMapper;
import com.shop.pojo.TbItemCat;
import com.shop.pojo.TbItemCatExample;
import com.shop.pojo.TbItemCatExample.Criteria;
import com.shop.rest.pojo.CatNode;
import com.shop.rest.pojo.CatResult;
import com.shop.rest.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public CatResult getTbItemCatList() {
		CatResult result = new CatResult();
		result.setData(getCatList(0));
		return result;
	}

	private List<?> getCatList(long parentId) {
		//执行查询
		TbItemCatExample example = new TbItemCatExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		//返回一个含有多个节点的resultlist
		List resultList = new ArrayList<>();
		//遍历查询的list
		int count=0;
		for (TbItemCat tbItemCat : list) {
			//判断是否是父节点
			if (tbItemCat.getIsParent()) {
				CatNode node = new CatNode();
				//判断拿到的结点是否是第一层
				if (parentId == 0) {
					node.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					//如果不是第一层则不用加<a href...>
					node.setName(tbItemCat.getName());
				}
				//每一层的url都一样
				node.setUrl("/products/" + tbItemCat.getId() + ".html");
				//递归调用当前结点下的子节点
				node.setItem(getCatList(tbItemCat.getId()));
				//将node添加到返回结果中去
				resultList.add(node);
				count++;
				if(parentId==0&&count==14)
					break;
			} else {
				//不是父节点则直接是文本，没有node
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}
