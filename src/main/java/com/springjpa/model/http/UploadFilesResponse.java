package com.springjpa.model.http;

import java.util.ArrayList;

public class UploadFilesResponse {
	private ArrayList<String> fileNames;
	private int defaultIndex;
	
	public ArrayList<String> getFileNames() {
		return fileNames;
	}
	public void setFileNames(ArrayList<String> fileNames) {
		this.fileNames = fileNames;
	}
	public int getDefaultIndex() {
		return defaultIndex;
	}
	public void setDefaultIndex(int defaultIndex) {
		this.defaultIndex = defaultIndex;
	}
	public UploadFilesResponse() {
		this.fileNames = new ArrayList<String>();
	}
}
