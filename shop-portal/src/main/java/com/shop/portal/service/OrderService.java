package com.shop.portal.service;

import java.util.List;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbOrder;
import com.shop.pojo.TbOrderItem;
import com.shop.pojo.TbOrderShipping;
import com.shop.portal.pojo.Order;

public interface OrderService {
	String createOrder(Order order);

}
