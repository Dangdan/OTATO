package com.shop.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.service.PictureService;
import com.shop.utils.FTPUtil;
import com.shop.utils.IDUtils;

/**
 * 要将Controller层传来的一个Multipart文件为其产生一个url还有新的文件名
 * 
 * @author Dan
 *
 */
@Service
public class PictureServiceImpl implements PictureService {

	@Value("${ftp.host}")
	private String host;
	@Value("${ftp.port}")
	private Integer port;
	@Value("${ftp.username}")
	private String username;
	@Value("${ftp.password}")
	private String password;
	@Value("${ftp.basePath}")
	private String basePath;
	@Value("${ftp.image_base_url}")
	private String image_base_url;
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap=new HashMap();
		if(uploadFile.isEmpty()||uploadFile==null){
			resultMap.put("error", 1);
			return resultMap;
		}
		
		try {
			//获取旧名称
			String originalFilename=uploadFile.getOriginalFilename();
			//产生新的名称
			String newName=IDUtils.genImageName();
			//追加文件后缀：jpg,png,jpeg等
			newName=newName+
					originalFilename.substring(originalFilename.lastIndexOf('.'));
			String filePath=new DateTime().toString("/yyyy/MM/dd");
			//上传
			boolean result = FTPUtil.uploadFile(host, port, username, password, basePath, filePath,
					newName, uploadFile.getInputStream());
			if(!result){
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败！");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url",image_base_url+filePath+"/"+newName);
			resultMap.put("message", "文件上传成功！");
			return resultMap;
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "发生异常！");
			return resultMap;
		}
	}

}
