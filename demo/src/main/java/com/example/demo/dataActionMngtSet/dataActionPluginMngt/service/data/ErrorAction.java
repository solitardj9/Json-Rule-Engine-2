package com.example.demo.dataActionMngtSet.dataActionPluginMngt.service.data;

public class ErrorAction {
	//
	private String siteId;
	
	private String eqpId;
	
	private String eqpType;
	
	private Object errorCode;
	
	public ErrorAction() {
		
	}

	public ErrorAction(String siteId, String eqpId, String eqpType, Object errorCode) {
		this.siteId = siteId;
		this.eqpId = eqpId;
		this.eqpType = eqpType;
		this.errorCode = errorCode;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getEqpType() {
		return eqpType;
	}

	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}

	public Object getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Object errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ErrorAction [siteId=" + siteId + ", eqpId=" + eqpId + ", eqpType=" + eqpType + ", errorCode="
				+ errorCode + "]";
	}
}