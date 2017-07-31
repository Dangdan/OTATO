package com.shop.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbUser;
import com.shop.sso.service.UserService;
import com.shop.utils.ExceptionUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
		ShopResult result = null;
		// 参数有效性检验
		if (StringUtils.isBlank(param)) {
			result = ShopResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			result = ShopResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = ShopResult.build(400, "校验内容类型错误");
		}

		// 校验出错
		if (null != result) {
			if (null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}

		//没有出错就调用检验服务
		try {
			result = userService.checkData(param, type);
		} catch (Exception e) {
			result = ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		//查看是否有回调
		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}

	}

	@RequestMapping("/register")
	@ResponseBody
	public ShopResult createUser(TbUser user) {

		try {
			ShopResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ShopResult userLogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {

		try {
			ShopResult result = userService.userLogin(username, password, request, response);
			return result;
		} catch (Exception e) {
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public ShopResult userLogout(String username,HttpServletRequest request,HttpServletResponse response) {

		try {
			ShopResult result = userService.userLogout(username, request, response);
			return result;
		} catch (Exception e) {
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		ShopResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}

	}
}
