package it.sebastianosuraci.springboot.core.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.dto.BaseDTO;
import it.sebastianosuraci.springboot.core.dto.DropdownDTO;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.core.service.IBaseReadOnlyService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseReadOnlyController<T extends BaseEntity<K>, D extends BaseDTO<K>, K extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected Validator validator;

	final protected IBaseReadOnlyService<T, K> readOnlyService;

	final protected IBaseMapper<T, D, K> mapper;

	@GetMapping(path = "/{id}")
	@ResponseBody
	public D getOne(@PathVariable K id, HttpServletRequest request) throws AppException {
		Optional<T> entity = readOnlyService.findById(id, FetchOptions.builder().userPermFilter(true).build());
		return entity.map(x -> mapper.entityToDto(x)).orElse(null);
	}

	protected List<T> getEntityList(PageModel pageModel, HttpServletRequest request, HttpServletResponse response) {
		// security check
		if (pageModel == null) {
			pageModel = new PageModel();
		}
		beforeGetList(pageModel, request);

		List<T> res = readOnlyService.getList(FetchOptions.builder().userPermFilter(true).pageModel(pageModel).build());
		response.setHeader("X-Total-Count", Integer.toString(pageModel.getFetchedRows()));
		return res;
	}

	@GetMapping
	@ResponseBody
	public List<D> getList(PageModel pageModel, HttpServletRequest request, HttpServletResponse response) {
		
		return mapper.entityToDtoList(getEntityList(pageModel, request, response));
	}

	@GetMapping("dropdown")
	@ResponseBody
	public List<DropdownDTO> getDropdownList(PageModel pageModel, HttpServletRequest request, HttpServletResponse response) {
		return mapper.entityToDropdownDtoList(getEntityList(pageModel, request, response));
	}

	protected void beforeGetList(PageModel pageModel, HttpServletRequest request) {

	}


}
