package com.shop.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shop.pojo.TbUser;
import com.shop.portal.service.Impl.UserServiceImpl;
import com.shop.utils.CookieUtils;
/**
 * 登录拦截器，需要在springmvc中进行拦截器的配置，让它拦截指定的url
 * 
 * @author Dan
 *
 */
public class LoginInteceptor implements HandlerInterceptor {
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// 返回modelandview之后，响应用户之后

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// handler执行之后，返回modelandview之前

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 在handler执行之前处理
		//判断用户是否登录
		//从cookie中取到token
		String token=CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token获取用户信息，调用sso的接口
		TbUser user=userService.getUserByToken(token);
		//如果取不到
		if(null==user){
			//跳转到登录页面,把用户请求的url作为参数传递给登录页面
			response.sendRedirect(userService.getSSO_BASE_URL()+userService.getSSO_PAGE_LOGIN()+"?redirect="+request.getRequestURL());
			//返回false
			return false;
		}
		//取到用户信息，放行
		//返回值决定handler是否放行，true：放行，false：不执行
		//为订单页面获取用户信息做铺垫
		request.setAttribute("user", user);
		return true;
	}

}
