package com.shop.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.shop.mapper.TbUserMapper;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbUser;
import com.shop.pojo.TbUserExample;
import com.shop.pojo.TbUserExample.Criteria;
import com.shop.sso.dao.JedisClient;
import com.shop.sso.service.UserService;
import com.shop.utils.CookieUtils;
import com.shop.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	// 校验数据
	@Override
	public ShopResult checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria c = example.createCriteria();
		// 对数据进行校验，1:usrname,2:phone,3:email
		// 用户名校验
		if (1 == type) {
			c.andUsernameEqualTo(content);
		} else if (2 == type) {
			c.andPhoneEqualTo(content);
		} else if (3 == type) {
			c.andEmailEqualTo(content);
		}
		// 执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			// 数据可用
			return ShopResult.ok(true);
		}
		// 数据不可用
		return ShopResult.ok(false);
	}

	// 用户注册
	@Override
	public ShopResult createUser(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		tbUserMapper.insert(user);
		return ShopResult.ok();
	}

	// 用户登录
	@Override
	public ShopResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria c = example.createCriteria();
		c.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		// 如果没有此用户名
		if (null == list || list.size() == 0) {
			return ShopResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		// 比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return ShopResult.build(400, "用户名或密码错误");
		}

		// 生成token
		String token = UUID.randomUUID().toString();
		// 保存用户之前，把用户对象中的密码清空。
		user.setPassword(null);
		// 把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		// 设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

		// 添加写cookie的逻辑，cookie的有效期是关闭浏览器
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);

		// 返回token

		return ShopResult.ok(token);
	}
	// 用户退出

	@Override
	public ShopResult getUserByToken(String token) {
		// 根据token从redis中查询用户信息
		String json = jedisClient.get((REDIS_USER_SESSION_KEY) + ":" + token);
		// 判断是否为空
		if (StringUtils.isBlank(json)) {
			return ShopResult.build(400, "此session已经过期，请重新登录！");
		}
		// 更新过期时间
		jedisClient.expire((REDIS_USER_SESSION_KEY) + ":" + token, SSO_SESSION_EXPIRE);
		// 返回用户信息（需要将json转换为user对象再转换成shopResult）
		return ShopResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

	@Override
	public ShopResult userLogout(String username,HttpServletRequest request,HttpServletResponse response) {
		//退出
		return null;
	}

}
