package it.sebastianosuraci.springboot.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ValidationResponse extends WsResp {

	protected Map<String, List<String>> validationErrorList=new TreeMap<>();

	public ValidationResponse(){
		super();
	}

	public Map<String, List<String>> getValidationErrorList() {
		return validationErrorList;
	}

	public void addError(String property, String message) {
		List<String> messageList = validationErrorList.containsKey(property) ? validationErrorList.get(property) : new ArrayList<>();
		messageList.add(message);
		validationErrorList.put(property,  messageList);
	}
	
	public boolean hasErrors() {
		return validationErrorList.size() > 0;
	}
}
