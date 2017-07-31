package com.shop.rest.service;

import java.util.List;

import com.shop.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(long contentCid);

}
