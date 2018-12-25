package com.springjpa.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private AmazonS3 s3client;

    private String endpointUrl;
    
    @Value("${amazonProperties.region}")
    private String region;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
    	this.endpointUrl = String.format("https://s3.%s.amazonaws.com", this.region);
    	BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey); 
    	this.s3client = AmazonS3ClientBuilder.standard()
    											.withRegion(this.region)
    											.withCredentials(new AWSStaticCredentialsProvider(creds))
    											.build();
    }

    /* (non-Javadoc)
	 * @see com.springjpa.service.AmazonS3Service#uploadFile(org.springframework.web.multipart.MultipartFile)
	 */
    @Override
	public String uploadFile(MultipartFile multipartFile) {
        String fileName = generateFileName(multipartFile);
        try {
            File file = convertMultiPartToFile(multipartFile);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
           e.printStackTrace();
           return "";
        } 
        return fileName;
    }

    @Override
	public String getFileUrl(String fileName) {
        return endpointUrl + "/" + bucketName + "/" + fileName;
    }
    
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return UUID.randomUUID().toString() + ":" + new Date().getTime();
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    /* (non-Javadoc)
	 * @see com.springjpa.service.AmazonS3Service#deleteFileFromS3Bucket(java.lang.String)
	 */
    @Override
	public boolean deleteFileFromS3Bucket(String fileName) {
    	try {
    		s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }

}
