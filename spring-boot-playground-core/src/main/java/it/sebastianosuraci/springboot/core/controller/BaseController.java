package it.sebastianosuraci.springboot.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController<T extends BaseEntity<K>, D extends BaseDTO<K>, K extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected Validator validator;

	final protected IBaseService<T, K> service;

	final protected IBaseMapper<T, D, K> mapper;

	protected void afterSave(T entity, D dto) {

	}

	protected void beforeSave(D dto) throws AppException {

	}

	protected void beforeUpdate(D dto) {

	}

	protected void afterUpdate(T entity, D dto) {

	}

	@GetMapping(path = "/{id}")
	@ResponseBody
	public D getOne(@PathVariable K id, HttpServletRequest request) throws AppException {
		Optional<T> entity = service.findById(id, FetchOptions.builder().userPermFilter(true).build());
		return entity.map(x -> mapper.entityToDto(x)).orElse(null);
	}

	@GetMapping
	@ResponseBody
	public List<D> getList(PageModel pageModel, HttpServletRequest request, HttpServletResponse response) {
		// security check
		if (pageModel == null) {
			pageModel = new PageModel();
		}
		beforeGetList(pageModel, request);

		List<T> res = service.getList(FetchOptions.builder().userPermFilter(true).pageModel(pageModel).build());
		response.setHeader("X-Total-Count", Integer.toString(pageModel.getFetchedRows()));
		return mapper.entityToDtoList(res);
	}

	@PostMapping
	@ResponseBody
	public ValidationResponse<T> insert(@RequestBody D dto) throws AppException {
		// security check
		ValidationResponse<T> validationResponse = new ValidationResponse<>();
		try {
			beforeSave(dto);
			validate(dto, true);
			T entity = mapper.dtoToEntity(dto);
			service.insert(entity);
			afterSave(entity, dto);
		} catch (AppException appException) {
			if (appException.getErrCode().equals(ErrCode.VALIDATION)) {
				validationResponse.setValidationErrorList(appException.getValidationErrorList());
			} else {
				throw appException;
			}
		}
		validationResponse.setErrCode(validationResponse.getValidationErrorList() == null
				|| validationResponse.getValidationErrorList().size() == 0 ? ErrCode.OK : ErrCode.VALIDATION);
		return validationResponse;
	}

	@PutMapping(path = "/{id}")
	@ResponseBody
	public ValidationResponse<T> update(@PathVariable K id, @RequestBody D dto) throws AppException {
		ValidationResponse<T> validationResponse = new ValidationResponse<>();
		try {
			beforeUpdate(dto);
			validate(dto, false);
			mapper.dtoToEntity(dto).setId(id);
			T entity = mapper.dtoToEntity(dto);
			service.update(entity, FetchOptions.builder().userPermFilter(true).build());
			afterUpdate(entity, dto);
		} catch (AppException appException) {
			if (appException.getErrCode().equals(ErrCode.VALIDATION)) {
				validationResponse.setValidationErrorList(appException.getValidationErrorList());
			} else {
				throw appException;
			}
		}
		validationResponse.setErrCode(validationResponse.getValidationErrorList() == null
				|| validationResponse.getValidationErrorList().size() == 0 ? ErrCode.OK : ErrCode.VALIDATION);
		return validationResponse;
	}

	@DeleteMapping(path = "/{id}")
	@ResponseBody
	public ValidationResponse<T> delete(@PathVariable K id) throws AppException {
		// security check
		ValidationResponse<T> validationResponse = new ValidationResponse<>();
		try {
			service.delete(id, FetchOptions.builder().userPermFilter(true).build());
		} catch (AppException appException) {
			if (appException.getErrCode().equals(ErrCode.VALIDATION)) {
				validationResponse.setValidationErrorList(appException.getValidationErrorList());
			} else {
				throw appException;
			}
		}
		validationResponse.setErrCode(ErrCode.OK);
		return validationResponse;
	}

	protected void beforeGetList(PageModel pageModel, HttpServletRequest request) {

	}

	protected void addToValidationErrorList(Map<String, List<String>> validationErrorList, String field, String error) {
		String[] errMsg = new String[] { error };
		validationErrorList.put(field, Arrays.asList(errMsg));
	}

	protected void addToValidationErrorList(Map<String, List<String>> validationErrorList, Set<ConstraintViolation<D>> violations) {
		if (!violations.isEmpty()) {
			for (ConstraintViolation<D> constraintViolation : violations) {
				String property = constraintViolation.getPropertyPath().toString();
				String message = constraintViolation.getMessage();
				List<String> messageList = validationErrorList.containsKey(property)
						? validationErrorList.get(property)
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
