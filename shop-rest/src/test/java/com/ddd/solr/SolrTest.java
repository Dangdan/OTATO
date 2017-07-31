package com.ddd.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SolrTest {
	@Test
	public void addDocument() throws SolrServerException, IOException {
		// 创建一个连接
		SolrServer solrServer=new HttpSolrServer("http://128.0.9.16:8080/solr");
		
		// 创建一个文档对象
		SolrInputDocument solrInputDocument=new SolrInputDocument();
		solrInputDocument.addField("id", "test001");
		solrInputDocument.addField("item_title", "测试商品111");
		solrInputDocument.addField("item_price", 10000);
		//把文档写入索引库
		solrServer.add(solrInputDocument);
		//提交
		solrServer.commit();
	}
	@Test
	public void deleteDocument() throws SolrServerException, IOException {
		// 创建一个连接
		SolrServer solrServer=new HttpSolrServer("http://128.0.9.16:8080/solr");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}

}
