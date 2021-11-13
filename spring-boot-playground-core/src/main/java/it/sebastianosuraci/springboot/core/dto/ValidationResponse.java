package it.sebastianosuraci.springboot.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;

import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;

/**
 *  Web Service response returned by {@link it.qualibit.qbframework.core.controller.BaseController#entitySave(EntitySaveRequest, BindingResult)}
 *  that contains information about the saving attempt. It returns eventual validation errors if the entity which you tried to save
 *  was not a valid entity.
 * @author enrico
 *
 */
public class ValidationResponse<T> extends WsTypedResp<T>{
	
	/**
	 * A Map of the errors occurred when validating the entity before saving it.
	 */
	protected Map<String, List<String>> validationErrorList=new TreeMap<>();

	public ValidationResponse(){
		super(null);
	}
	
	public ValidationResponse(ErrCode errCode, String message) {
		super(errCode, message);
	}
	
	/**
	 * Constructor that takes a {@link BindingResult} and repackages its information in a {@link ValidationResponse} object. 
	 * @param bindingResult A {@link BindingResult} object containing information about the validation process.
	 */
	public ValidationResponse(Set<ConstraintViolation<T>> constraintViolationSet) {
		super(null);
		if (!constraintViolationSet.isEmpty()) {
			for (ConstraintViolation<T> cv : constraintViolationSet) {
				addError(cv.getPropertyPath().toString().replaceAll("newEntity.", ""), cv.getMessage());
			}
			this.setMessage("");
			this.setErrCode(ErrCode.VALIDATION);
		} else {
			this.setErrCode(ErrCode.OK);
		}
	}

	public Map<String, List<String>> getValidationErrorList() {
		return validationErrorList;
	}

	public void setValidationErrorList(Map<String, List<String>> validationErrorMap) {
		this.validationErrorList = validationErrorMap;
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
