package it.sebastianosuraci.springboot.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.dto.BaseDTO;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.dto.ValidationResponse;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.exception.AppException.ErrCode;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.core.service.IBaseService;

public abstract class BaseController<T extends BaseEntity<K>, D extends BaseDTO<K>, K extends Serializable>
		extends BaseReadOnlyController<T, D, K> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	final protected IBaseService<T, K> service;

	@Autowired
	protected Validator validator;

	public BaseController(IBaseService<T, K> service, IBaseMapper<T, D, K> mapper) {
		super(service, mapper);
		this.service = service;
	}

	protected void afterSave(T entity, D dto) {

	}

	protected void beforeSave(D dto) throws AppException {

	}

	protected void beforeUpdate(D dto) {

	}

	protected void afterUpdate(T entity, D dto) {

	}

	protected ResponseEntity<ValidationResponse<D>> sendValidationResponse(ValidationResponse<D> validationResponse) {
		if (validationResponse.getValidationErrorList() != null
				&& validationResponse.getValidationErrorList().size() > 0) {
			validationResponse.setErrCode(ErrCode.VALIDATION);
		}
		return new ResponseEntity<>(validationResponse,
				validationResponse.getErrCode().equals(ErrCode.OK) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<ValidationResponse<D>> insert(@RequestBody D dto) throws AppException {
		// security check
		ValidationResponse<D> validationResponse = new ValidationResponse<>();
		try {
			beforeSave(dto);
			validate(dto, true);
			T entity = mapper.dtoToEntity(dto);
			validationResponse.setData(mapper.entityToDto(service.insert(entity)));
			afterSave(entity, dto);
		} catch (AppException appException) {
			if (appException.getErrCode().equals(ErrCode.VALIDATION)) {
				validationResponse.setValidationErrorList(appException.getValidationErrorList());
			} else {
				throw appException;
			}
		}
		return sendValidationResponse(validationResponse);
	}

	@PutMapping(path = "/{id}")
	@ResponseBody
	public ResponseEntity<ValidationResponse<D>> update(@PathVariable K id, @RequestBody D dto,
			HttpServletResponse response) throws AppException {
		ValidationResponse<D> validationResponse = new ValidationResponse<>();
		try {
			beforeUpdate(dto);
			validate(dto, false);
			mapper.dtoToEntity(dto).setId(id);
			T entity = mapper.dtoToEntity(dto);
			validationResponse.setData(mapper.entityToDto(service.update(entity, FetchOptions.builder().userPermFilter(true).build())));
			afterUpdate(entity, dto);
		} catch (AppException appException) {
			if (appException.getErrCode().equals(ErrCode.VALIDATION)) {
				validationResponse.setValidationErrorList(appException.getValidationErrorList());
			} else {
				throw appException;
			}
		}
		return sendValidationResponse(validationResponse);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseBody
	public ResponseEntity<ValidationResponse<D>> delete(@PathVariable K id) throws AppException {
		// security check
		ValidationResponse<D> validationResponse = new ValidationResponse<>();
		try {
			service.delete(id, FetchOptions.builder().userPermFilter(true).build());
		} catch (AppException appException) {
			if (appException.getErrCode().equals(ErrCode.VALIDATION)) {
				validationResponse.setValidationErrorList(appException.getValidationErrorList());
			} else {
				throw appException;
			}
		}
		return sendValidationResponse(validationResponse);
	}

	protected void beforeGetList(PageModel pageModel, HttpServletRequest request) {

	}

	protected void addToValidationErrorList(Map<String, List<String>> validationErrorList, String field, String error) {
		String[] errMsg = new String[] { error };
		validationErrorList.put(field, Arrays.asList(errMsg));
	}

	protected void addToValidationErrorList(Map<String, List<String>> validationErrorList,
			Set<ConstraintViolation<D>> violations) {
		if (!violations.isEmpty()) {
			for (ConstraintViolation<D> constraintViolation : violations) {
				String property = constraintViolation.getPropertyPath().toString();
				String message = constraintViolation.getMessage();
				List<String> messageList = validationErrorList.containsKey(property) ? validationErrorList.get(property)
						: new ArrayList<>();
				messageList.add(message);
				validationErrorList.put(property, messageList);
			}
		}
	}

	public void validate(D dto, boolean isNew) throws AppException {
		Map<String, List<String>> validationErrorList = new TreeMap<>();
		try {
			if (dto == null) {
				addToValidationErrorList(validationErrorList, "entity", "null entity");
			} else {
				addToValidationErrorList(validationErrorList, validator.validate(dto));

				if (isNew) {
					if (dto.getId() != null) {
						validationErrorList.put("id", Arrays.asList("Id must be null for insert"));
					}
				} else {
					if (dto.getId() == null) {
						validationErrorList.put("id", Arrays.asList("Id must not be null for insert"));
					}
				}
			}
			if (validationErrorList.size() > 0) {
				throw new AppException(ErrCode.VALIDATION, validationErrorList);
			}
		} catch (ValidationException e) {
			logger.warn(e.getMessage());
		}
	}

}
