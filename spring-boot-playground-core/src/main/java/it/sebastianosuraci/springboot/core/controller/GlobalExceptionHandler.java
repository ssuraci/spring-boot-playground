package it.sebastianosuraci.springboot.core.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.sebastianosuraci.springboot.core.dto.WsResp;
import it.sebastianosuraci.springboot.core.exception.AppException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	protected ResponseEntity<Object> notFound(EntityNotFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logger.warn("Returning HTTP 404 Bad Request", ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		logger.warn("Returning HTTP 500 Bad Request", ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AuthenticationServiceException.class)
	public ResponseEntity<String> handleSamlException(AuthenticationServiceException ex) {
		logger.warn("SAML Exception", ex);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(headers,HttpStatus.FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.warn("Returning HTTP 400 Bad Request", ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AppException.class)
	@ResponseBody
	public WsResp handleGenericException(AppException ex) {
		return new WsResp(ex);
	}



}