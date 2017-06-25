package com.shop.service;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItemParam;

public interface ItemParamService {
	public ShopResult getItemParamByCid(long cid);
	public ShopResult insertItemParam(TbItemParam itemParam);
	

}
