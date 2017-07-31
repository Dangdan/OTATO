package com.shop.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.portal.pojo.CartItem;
import com.shop.portal.pojo.Order;
import com.shop.portal.service.CartService;
import com.shop.portal.service.OrderService;
import com.shop.utils.ExceptionUtil;


@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response,Model model) {
		List<CartItem> cartItemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", cartItemList);
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Order order,Model model) {
		try {
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			//给日期加3天
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("message", ExceptionUtil.getStackTrace(e)+"创建订单出错，请稍后重试！");
			//通知开发人员
			//...
			return "error/exception";
		}
	}
}
