package com.shop.portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItem;
import com.shop.portal.pojo.CartItem;
import com.shop.portal.service.CartService;
import com.shop.utils.CookieUtils;
import com.shop.utils.HttpClientUtil;
import com.shop.utils.JsonUtils;

/**
 * 购物车service
 * 
 * @author Dan
 *
 */
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	// 添加商品到购物车
	@Override
	public ShopResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// 先取购物车商品列表
		// 取商品信息
		CartItem cartItem = null;
		List<CartItem> itemList = getCartItemList(request);
		for (CartItem c : itemList) {
			if (c.getId() == itemId) {
				c.setNum(c.getNum() + num);
				cartItem = c;
				break;
			}
		}
		if (cartItem == null) {
			cartItem = new CartItem();
			// 调用rest中的查询商品信息服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			// 包装成shopResult类型
			if (json != null) {
				ShopResult result = JsonUtils.jsonToPojo(json, ShopResult.class);
				// 判断商品状态是否正常
				if (result.getStatus() == 200) {
					TbItem item = (TbItem) result.getData();
					cartItem.setId(item.getId());
					cartItem.setTitle(item.getTitle());
					cartItem.setPrice(item.getPrice());
					cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
					cartItem.setNum(num);
				}
				// 添加到购物车列表
				itemList.add(cartItem);
			}
		}
		// 将购物车列表重新写到cookie中
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return ShopResult.ok();
	}

	/**
	 * 从cookie中取商品列表
	 * 
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		try {
			// 从cookie中取商品列表
			String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
			// 转换json为购物车商品列表
			if (cartJson != null) {
				List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
				return list;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 没有商品时返回一个list对象
		return new ArrayList<>();
	}

	/**
	 * 从cookie中把购物车列表取出来，没有参数，返回购物车中的商品列表。做展示
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	@Override
	public ShopResult updateCartItemNum(long itemId, int num, HttpServletRequest request,
			HttpServletResponse response) {
		// 先取购物车商品列表
		// 取商品信息
		CartItem cartItem = null;
		List<CartItem> itemList = getCartItemList(request);
		for (CartItem c : itemList) {
			if (c.getId() == itemId) {
				c.setNum(num);
				cartItem = c;
				break;
			}
		}
		// 将购物车列表重新写到cookie中
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return ShopResult.ok();
	}

	@Override
	public ShopResult deleteCartItem(long itemId,HttpServletRequest request, HttpServletResponse response) {
		// 先取购物车商品列表
		// 取商品信息
		CartItem cartItem = null;
		List<CartItem> itemList = getCartItemList(request);
		for (CartItem c : itemList) {
			if (c.getId() == itemId) {
				itemList.remove(c);
				break;
			}
		}
		// 将购物车列表重新写到cookie中
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return ShopResult.ok();
	}

}
