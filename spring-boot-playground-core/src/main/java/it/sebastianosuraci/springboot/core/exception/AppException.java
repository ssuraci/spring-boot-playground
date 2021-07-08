package it.sebastianosuraci.springboot.core.exception;

import java.util.List;
import java.util.Map;

public class AppException extends Exception {
	public enum ErrCode { OK, BAD_INPUT, NOT_FOUND, VALIDATION, DATA_INTEGRITY, DEVICE_ERROR }

	protected final ErrCode errCode; 
	
	private final Map<String, List<String>> validationErrorList;
	
	private static final long serialVersionUID = -195778065791225557L;

	public AppException(ErrCode errCode, String message) {
        super(message);
        this.errCode=errCode;
		validationErrorList=null;
    }

	public AppException(ErrCode errCode) {
		super();
		this.errCode = errCode;
		validationErrorList=null;
	}

	public AppException(ErrCode errCode, Map<String, List<String>> validationErrorMap) {
		this.errCode = errCode;
		this.validationErrorList = validationErrorMap;
	}

	public ErrCode getErrCode() {
		return errCode;
	}

	public Map<String, List<String>> getValidationErrorList() {
		return validationErrorList;
	}
	
}
