package com.example.demo.dataActionMngtSet.dataActionMngt.service;

import com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.data.JsonRuleEngineConfigs;

public interface DataActionManager {
	//
	public Boolean isInitialized();
	
	public void checkAndDoAction(String message);
	
	public JsonRuleEngineConfigs getJsonRuleEngineConfigs();
}