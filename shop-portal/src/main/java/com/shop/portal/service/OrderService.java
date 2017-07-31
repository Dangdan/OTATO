package com.shop.portal.service;

import javax.servlet.http.HttpServletRequest;
import com.shop.portal.pojo.Order;

public interface OrderService {
	String createOrder(Order order,HttpServletRequest request);

}
