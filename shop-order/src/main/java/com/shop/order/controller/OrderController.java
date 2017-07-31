package com.shop.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.order.pojo.Order;
import com.shop.order.service.OrderService;
import com.shop.pojo.ShopResult;
import com.shop.utils.ExceptionUtil;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	
	
	@RequestMapping("/create")
	@ResponseBody
	public ShopResult createOrder(@RequestBody Order order) {
		try {
			ShopResult result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
}
