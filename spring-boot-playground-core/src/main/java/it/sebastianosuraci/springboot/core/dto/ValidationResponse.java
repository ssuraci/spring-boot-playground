package it.sebastianosuraci.springboot.core.dto;

import java.util.TreeMap;

import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;

public class ValidationResponse extends WsResp {


	public ValidationResponse(){
		super();
		this.errorList = new TreeMap<>();
	}


	public void addError(String property, String message) {
		String messageList = errorList.containsKey(property) ? errorList.get(property) +", " : "";
		messageList += message;
		this.errCode = ErrCode.BAD_INPUT;
		this.message = "Validation error";
		errorList.put(property,  messageList);
	}
	
	public boolean hasErrors() {
		return errorList.size() > 0;
	}
}
