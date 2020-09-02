package com.example.demo.dataActionMngtSet.dataActionMngt.service.util.jsonRuleEngine.data;

public class JsonRuleEngineResultSet {
	//
	private Object result;
	
	private Object event;
	
	public JsonRuleEngineResultSet() {
		
	}

	public JsonRuleEngineResultSet(Object result, Object event) {
		this.result = result;
		this.event = event;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getEvent() {
		return event;
	}

	public void setEvent(Object event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "JsonRuleEngineResultSet [result=" + result + ", event=" + event + "]";
	}
}