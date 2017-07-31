package com.shop.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbUser;

public interface UserService {
	ShopResult checkData(String content,Integer type);
	ShopResult createUser(TbUser user);
	ShopResult userLogin(String username,String password,HttpServletRequest request,HttpServletResponse response);
	ShopResult userLogout(String username,HttpServletRequest request,HttpServletResponse response);
	ShopResult getUserByToken(String token);
}
