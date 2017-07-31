package com.shop.order.service;

import java.util.List;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbOrder;
import com.shop.pojo.TbOrderItem;
import com.shop.pojo.TbOrderShipping;

public interface OrderService {
	ShopResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping);

}
