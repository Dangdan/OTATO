package com.shop.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.pojo.ShopResult;
import com.shop.search.mapper.ItemMapper;
import com.shop.search.pojo.Item;
import com.shop.search.service.ItemService;
import com.shop.utils.ExceptionUtil;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	//将所有数据导入索引库
	@Override
	public ShopResult importAllItems() {
		try {
			List<Item> list=itemMapper.getItemList();
			for (Item item : list) {
				SolrInputDocument solrInputDocument=new SolrInputDocument();
				solrInputDocument.addField("id", item.getId());
				solrInputDocument.addField("item_title", item.getTitle());
				solrInputDocument.addField("item_sell_point", item.getSell_point());
				solrInputDocument.addField("item_price", item.getPrice());
				solrInputDocument.addField("item_image", item.getImage());
				solrInputDocument.addField("item_category_name", item.getCategory_name());
				solrInputDocument.addField("item_desc", item.getItem_des());
				solrServer.add(solrInputDocument);
			}
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return ShopResult.ok();
	}

}
