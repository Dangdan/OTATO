package com.shop.portal.service.Impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItemDesc;
import com.shop.pojo.TbItemParamItem;
import com.shop.portal.pojo.ItemInfo;
import com.shop.portal.service.ItemService;
import com.shop.utils.HttpClientUtil;
import com.shop.utils.JsonUtils;

/**
 * 商品信息
 * 
 * @author Dan
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;

	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			// 调用rest的服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				ShopResult itemResult = ShopResult.formatToPojo(json, ItemInfo.class);
				if (itemResult.getStatus() == 200) {
					ItemInfo itemInfo = (ItemInfo) itemResult.getData();
					return itemInfo;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemDescById(Long itemId) {
		try {
			// 调用rest的服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				ShopResult itemDescResult = ShopResult.formatToPojo(json, TbItemDesc.class);
				if (itemDescResult.getStatus() == 200) {
					TbItemDesc itemDesc = (TbItemDesc) itemDescResult.getData();
					String result = itemDesc.getItemDesc();
					return result;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemParam(Long itemId) {
		try {
			// 调用rest的服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				ShopResult itemParamResult = ShopResult.formatToPojo(json, TbItemParamItem.class);
				if (itemParamResult.getStatus() == 200) {
					TbItemParamItem itemParamItem = (TbItemParamItem) itemParamResult.getData();
					String paramData = itemParamItem.getParamData();
					// 生成html
					// 把规格参数json数据转换成java对象
					List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append(
							"<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("    <tbody>\n");
					for (Map m1 : jsonList) {
						sb.append("        <tr>\n");
						sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
						sb.append("        </tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for (Map m2 : list2) {
							sb.append("        <tr>\n");
							sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
							sb.append("            <td>" + m2.get("v") + "</td>\n");
							sb.append("        </tr>\n");
						}
					}
					sb.append("    </tbody>\n");
					sb.append("</table>");
					// 返回html片段
					return sb.toString();
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
