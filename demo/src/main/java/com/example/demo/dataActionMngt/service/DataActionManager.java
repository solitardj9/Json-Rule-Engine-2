package com.example.demo.dataActionMngt.service;

public interface DataActionManager {
	//
	public Boolean isInitialized();
	
	public void checkAndDoAction(String message);
}