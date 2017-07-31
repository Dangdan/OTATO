package com.shop.portal.service;

import com.shop.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(String token);

}
