package com.arnavsankaran.training;

public class TrainingItem {
	
	private String fileName;
	private int configPosition;
	
	public TrainingItem(String fileName, int configPosition) {
		this.fileName = fileName;
		this.configPosition = configPosition;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getConfigPosition() {
		return configPosition;
	}
	
}
