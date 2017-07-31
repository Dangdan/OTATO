package com.shop.portal.service.Impl;

import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbUser;
import com.shop.portal.service.UserService;
import com.shop.utils.HttpClientUtil;
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_LOGIN}")
	private String SSO_PAGE_LOGIN;

	public String getSSO_PAGE_LOGIN() {
		return SSO_PAGE_LOGIN;
	}

	public void setSSO_PAGE_LOGIN(String sSO_PAGE_LOGIN) {
		SSO_PAGE_LOGIN = sSO_PAGE_LOGIN;
	}

	@Override
	public TbUser getUserByToken(String token) {
		try {
			//调用sso的服务，很久token获取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN+token);
			//转换json为ShopResult
			ShopResult result=ShopResult.formatToPojo(json, TbUser.class);
			if(result.getStatus()==200){
				TbUser user=(TbUser) result.getData();
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getSSO_BASE_URL() {
		return SSO_BASE_URL;
	}

	public void setSSO_BASE_URL(String sSO_BASE_URL) {
		SSO_BASE_URL = sSO_BASE_URL;
	}

	public String getSSO_USER_TOKEN() {
		return SSO_USER_TOKEN;
	}

	public void setSSO_USER_TOKEN(String SSO_USER_TOKEN) {
		SSO_USER_TOKEN = SSO_USER_TOKEN;
	}

}
