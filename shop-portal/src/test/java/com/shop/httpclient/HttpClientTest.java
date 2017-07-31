package com.shop.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void doGet() throws Exception {
		// 创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个GET对象
		HttpGet get = new HttpGet("http://www.baidu.com");
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 取得响应结果
		HttpEntity httpEntity = response.getEntity();
		String s = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(s);
		// 取状态
		int statuscode = response.getStatusLine().getStatusCode();
		System.out.println(statuscode);
		// 关闭httpClient
		httpClient.close();
		// 关闭response
		response.close();
	}

	@Test
	public void doGetWithParams() throws Exception {
		// 创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个uri对象
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("query", "花千骨");

		// 创建一个GET对象
		HttpGet get = new HttpGet(uriBuilder.build());
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 取得响应结果
		HttpEntity httpEntity = response.getEntity();
		String s = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(s);
		// 取状态
		int statuscode = response.getStatusLine().getStatusCode();
		System.out.println(statuscode);
		// 关闭httpClient
		httpClient.close();
		// 关闭response
		response.close();
	}

	@Test
	public void doPost() throws Exception {
		// 创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建HttpPost对象
		HttpPost post = new HttpPost("http://localhost:8083/httpclient/post.html");
		// 执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		// 取得响应结果
		HttpEntity httpEntity = response.getEntity();
		String s = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(s);
		response.close();
		httpClient.close();
	}
	
	@Test
	public void doPostWithParams() throws Exception {
		// 创建一个HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建HttpPost对象
		HttpPost post = new HttpPost("http://localhost:8083/httpclient/userpost.html");
		List<NameValuePair> kvList=new ArrayList<>();
		kvList.add(new BasicNameValuePair("username", "zhangsan三"));
		kvList.add(new BasicNameValuePair("password", "1234"));
		//包装成一个entity对象
		StringEntity entity=new UrlEncodedFormEntity(kvList,"utf-8");
		post.setEntity(entity);
		
		
		// 执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		// 取得响应结果
		HttpEntity httpEntity = response.getEntity();
		String s = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(s);
		response.close();
		httpClient.close();
	}

}
