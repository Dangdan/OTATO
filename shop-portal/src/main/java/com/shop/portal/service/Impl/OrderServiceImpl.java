package com.shop.portal.service.Impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public String createOrder(Order order,HttpServletRequest request) {
		/**
		 * 方案一：在cookie中根据token获取用户信息
		 * String token=CookieUtils.getCookieValue(request,"TT_TOKEN")
		 * 然后调用sso系统中的userservice接口中的方法getUserByToken获取用户信息
		 */
		/**
		 * 方案二:
		 * 获取用户信息，展示用户id（此时因为已经经过拦截器，肯定属于已经登陆的状态）,而在springmvc中，当得到一个
		 * 请求时经过dispatcherServerlet，要去HandlerMapping中获取一个handler，而映射器会返回一个
		 * executorChain，这里面就包含了一个interceptor，所以在这个返回地拦截器中我们可以将user的信息
		 * 保存在request中，在这里获取即可。
		 * 只需要将用户的id从当前浏览器获取
		 */
		
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
