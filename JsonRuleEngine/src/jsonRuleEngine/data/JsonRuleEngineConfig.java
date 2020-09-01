package jsonRuleEngine.data;

public class JsonRuleEngineConfig {
	//
	private Object trigger;
	
	private String result;
	
	public JsonRuleEngineConfig() {
		
	}
	
	public JsonRuleEngineConfig(JsonRuleEngineConfig object) {
		setTrigger(object.getTrigger());
		setResult(new String(object.getResult()));
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "JsonRuleEngineConfig [trigger=" + trigger + ", result=" + result + "]";
	}
}
