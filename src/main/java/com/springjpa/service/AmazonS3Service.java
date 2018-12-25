package com.springjpa.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

	String uploadFile(MultipartFile multipartFile);

	boolean deleteFileFromS3Bucket(String fileName);

	String getFileUrl(String fileName);

}