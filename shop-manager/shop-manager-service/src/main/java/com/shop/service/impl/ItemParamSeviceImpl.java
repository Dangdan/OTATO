package com.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbItemParamMapper;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItemParam;
import com.shop.pojo.TbItemParamExample;
import com.shop.pojo.TbItemParamExample.Criteria;
import com.shop.service.ItemParamService;
@Service
public class ItemParamSeviceImpl implements ItemParamService{
	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Override
	public ShopResult getItemParamByCid(long cid) {
		TbItemParamExample example=new TbItemParamExample();
		Criteria c=example.createCriteria();
		c.andItemCatIdEqualTo(cid);
		List<TbItemParam> list =itemParamMapper.selectByExampleWithBLOBs(example);//包含大文本的
		
		if(list!=null&&list.size()>0){
			return ShopResult.ok(list.get(0));
		}
		return ShopResult.ok();
	}
	@Override
	public ShopResult insertItemParam(TbItemParam itemParam) {
		//只更新日期数据
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入数据库
		itemParamMapper.insert(itemParam);
		//返回响应数据，因为是提交到数据库，所以不用给controller返回数据，只给返回一个ok，就是一个不带数据的对象
		return ShopResult.ok();
	}
	
}
