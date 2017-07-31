package com.shop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.springframework.stereotype.Component;
@Component
public class FTPTest {
	@Test
	public void testftp() throws SocketException, IOException{
		//创建FTPClient
		FTPClient fc=new FTPClient();
		//建立连接
		fc.connect("192.168.1.121", 21);
		//登录
		fc.login("ftpuser", "1896");
		
		//System.out.println(fc.isConnected());
		//创建上传文件
		FileInputStream file=new FileInputStream(new File("D:\\imagetest\\444.jpg"));
		//改变工作空间
		fc.changeWorkingDirectory("/home/ftpuser/images");
		//设置上传文件类型
		fc.setFileType(FTPClient.BINARY_FILE_TYPE);
		//System.out.println("before");
		fc.storeFile("444.jpg", file);
		//System.out.println("after");
		//退出登录
		fc.logout();
	}

}
