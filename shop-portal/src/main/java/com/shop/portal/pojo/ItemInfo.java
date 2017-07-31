package com.shop.portal.pojo;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;
import com.shop.utils.HttpClientUtil;

public class ItemInfo extends TbItem{

	//搜索中的图片展示不出来，image存储字段中存储的是多张图片，使用逗号分隔，这里把它拆分成数组，在页面中只取第一个
	@JsonIgnore
	public String[] getImages() {
		String image=getImage();
		if (image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
	
	
}
