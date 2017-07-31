package com.shop.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 购物车请求
 * @author Dan
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.ShopResult;
import com.shop.portal.pojo.CartItem;
import com.shop.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping("/add/{itemId}")
	@ResponseBody
	public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		ShopResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}

	// 防止/cart/add刷新反复执行
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}

	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", list);
		return "cart";
	}

	@RequestMapping("/updateNum/{num}")
	@ResponseBody
	public String updateCartItemNum(@PathVariable Long itemId, @RequestParam Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		ShopResult result = cartService.updateCartItemNum(itemId, num, request, response);
		return "redirect:/cart/cart.html";
	}

	@RequestMapping("/delete/{itemId}")
	@ResponseBody
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {

		ShopResult result = cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}

}
