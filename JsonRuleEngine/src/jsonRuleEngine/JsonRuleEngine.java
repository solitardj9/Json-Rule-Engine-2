package jsonRuleEngine;

import java.util.List;

import jsonRuleEngine.data.JsonRuleEngineConfigs;
import jsonRuleEngine.data.JsonRuleEngineResultSet;

public interface JsonRuleEngine {
	
	public void insertConfigs(String jsonRuleEngineConfigs);
	
	public void insertConfigs(JsonRuleEngineConfigs jsonRuleEngineConfigs);
	
	public JsonRuleEngineConfigs getConfigs();
	
	public List<JsonRuleEngineResultSet> execute(String jsonString);
}