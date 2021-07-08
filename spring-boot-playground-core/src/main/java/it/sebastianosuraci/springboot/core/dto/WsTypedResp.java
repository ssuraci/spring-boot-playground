package it.sebastianosuraci.springboot.core.dto;

import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;

public class WsTypedResp<T> extends WsResp {
	protected T data;
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public WsTypedResp(ErrCode errCode, String message, T data) {
		super(errCode, message);
		this.data = data;
	}

	public WsTypedResp(ErrCode errCode, String message) {
		super(errCode, message);
	}

	
	public WsTypedResp(T data) {
		super();
		this.data = data;
	}
	
}
