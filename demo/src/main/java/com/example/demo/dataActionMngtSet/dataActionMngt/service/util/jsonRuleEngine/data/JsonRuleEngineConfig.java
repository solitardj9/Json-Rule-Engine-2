package com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.data;

public class JsonRuleEngineConfig {
	//
	private Object trigger;
	
	private Object result;
	
	public JsonRuleEngineConfig() {
		
	}
	
	public JsonRuleEngineConfig(JsonRuleEngineConfig object) {
		setTrigger(object.getTrigger());
		setResult(object.getResult());
	}

	public JsonRuleEngineConfig(Object trigger, String result) {
		this.trigger = trigger;
		this.result = result;
	}

	public Object getTrigger() {
		return trigger;
	}

	public void setTrigger(Object trigger) {
		this.trigger = trigger;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "JsonRuleEngineConfig [trigger=" + trigger + ", result=" + result + "]";
	}
}
