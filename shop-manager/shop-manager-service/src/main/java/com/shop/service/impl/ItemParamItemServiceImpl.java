package com.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.mapper.TbItemParamItemMapper;
import com.shop.pojo.ShopResult;
import com.shop.pojo.TbItemParamItem;
import com.shop.pojo.TbItemParamItemExample;
import com.shop.pojo.TbItemParamItemExample.Criteria;
import com.shop.service.ItemParamItemService;
import com.shop.utils.JsonUtils;
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public String getItemItemParamItem(Long id) {
		// 按照id查询
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria c = example.createCriteria();
		c.andItemIdEqualTo(id);
		List<TbItemParamItem> listresult = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (listresult == null || listresult.size() <= 0) {
			return "";
		}
		TbItemParamItem tbItemParamItem = listresult.get(0);
		// 获取规格参数属性
		String itemParam = tbItemParamItem.getParamData();
		// 生成html展示给用户

		// 把规格参数json数据转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(itemParam, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
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
		return sb.toString();
	}

}
