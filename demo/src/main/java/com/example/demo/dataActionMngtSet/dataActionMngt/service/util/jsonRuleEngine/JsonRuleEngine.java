package com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine;

import java.util.List;

import com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.data.JsonRuleEngineConfigs;
import com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.data.JsonRuleEngineResultSet;

public interface JsonRuleEngine {
	
	public void insertConfigs(String jsonRuleEngineConfigs);
	
	public void insertConfigs(JsonRuleEngineConfigs jsonRuleEngineConfigs);
	
	public JsonRuleEngineConfigs getConfigs();
	
	public List<JsonRuleEngineResultSet> execute(String jsonString);
}