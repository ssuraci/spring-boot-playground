package it.sebastianosuraci.springboot.core.dto;

import java.util.Map;

import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;

public class WsResp {

	protected ErrCode errCode=ErrCode.OK;
	protected String message;
	protected Map<String, String> errorList;
	
	public WsResp(ErrCode errCode, String message) {
		super();
		this.errCode = errCode;
		this.message = message;
	}

	public WsResp(ErrCode errCode) {
		super();
		this.errCode = errCode;
	}

	public WsResp() {
		super();
	}

	public WsResp(AppException appException) {
		super();
		this.errCode=appException.getErrCode();
		this.message=appException.getMessage();
	}

	public ErrCode getErrCode() {
		return errCode;
	}

	public void setErrCode(ErrCode errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getErrorList() {
		return errorList;
	}

	public void setErrorList(Map<String, String> errorList) {
		this.errorList = errorList;
	}
	public Integer getCode() {
		return errCode.equals(ErrCode.OK) ? 0 : -1;
	}
	
}
