package jsonRuleEngine.data;

import java.util.ArrayList;
import java.util.List;

public class JsonRuleEngineConfigs {
	//
	private List<JsonRuleEngineConfig> configs;
	
	public JsonRuleEngineConfigs() {
		
	}
	
	public JsonRuleEngineConfigs(JsonRuleEngineConfigs object) {
		//
		this.configs = new ArrayList<>();
		for (JsonRuleEngineConfig iter : object.getConfigs()) {
			JsonRuleEngineConfig tmpConfig = new JsonRuleEngineConfig(iter);
			this.configs.add(tmpConfig);
		}
	}

	public JsonRuleEngineConfigs(List<JsonRuleEngineConfig> configs) {
		this.configs = configs;
	}

	public List<JsonRuleEngineConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<JsonRuleEngineConfig> configs) {
		this.configs = configs;
	}

	@Override
	public String toString() {
		return "JsonRuleEngineConfigs [configs=" + configs + "]";
	}
}