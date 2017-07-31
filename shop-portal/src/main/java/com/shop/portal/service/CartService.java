package com.shop.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.pojo.ShopResult;
import com.shop.portal.pojo.CartItem;

public interface CartService {
	ShopResult addCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
	ShopResult updateCartItemNum(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
	ShopResult deleteCartItem(long itemId,HttpServletRequest request,HttpServletResponse response);
}
