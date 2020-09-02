package jsonRuleEngine.data;

public class JsonRuleEngineConfig {
	//
	private Object trigger;
	
	private Object result;
	
	private Object event;
	
	public JsonRuleEngineConfig() {
		
	}
	
	public JsonRuleEngineConfig(JsonRuleEngineConfig object) {
		setTrigger(object.getTrigger());
		setResult(object.getResult());
		setEvent(object.getEvent());
	}

	public JsonRuleEngineConfig(Object trigger, Object result, Object event) {
		this.trigger = trigger;
		this.result = result;
		this.event = event;
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

	public Object getEvent() {
		return event;
	}

	public void setEvent(Object event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "JsonRuleEngineConfig [trigger=" + trigger + ", result=" + result + ", event=" + event + "]";
	}
}