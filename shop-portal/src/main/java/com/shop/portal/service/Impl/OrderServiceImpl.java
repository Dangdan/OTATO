package com.shop.portal.service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.portal.pojo.Order;
import com.shop.portal.service.OrderService;
import com.shop.utils.HttpClientUtil;
import com.shop.utils.JsonUtils;


/**
 * 订单服务(前台系统调用order系统的服务)
 * 
 * @author Dan
 *
 */

@Service
public class OrderServiceImpl implements OrderService{
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String createOrder(Order order) {
		// 调用order系统的url
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//将json转换成shopResult
		if(json!=null){
			ShopResult result = ShopResult.format(json);
			if(result.getStatus()==200){
				Object orderId=result.getData();
				return orderId.toString();
			}
		}
		
		return "";
	}

	
}
