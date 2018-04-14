package com.springjpa.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Services {
	public String uploadFile(MultipartFile uploadFile);
	public String deleteFile(String keyName);
}
