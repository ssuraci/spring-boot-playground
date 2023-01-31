package it.sebastianosuraci.springboot.core.controller;

import it.sebastianosuraci.springboot.core.domain.BaseEntity;
import it.sebastianosuraci.springboot.core.dto.BaseDTO;
import it.sebastianosuraci.springboot.core.dto.WsTypedResp;
import it.sebastianosuraci.springboot.core.exception.AppException;
import it.sebastianosuraci.springboot.core.mapper.IBaseMapper;
import it.sebastianosuraci.springboot.core.service.FetchOptions;
import it.sebastianosuraci.springboot.core.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.Serializable;

public abstract class BaseController<T extends BaseEntity<K>, D extends BaseDTO<K>, K extends Serializable>
        extends BaseReadOnlyController<T, D, K> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    final protected IBaseService<T, K> service;

    public BaseController(IBaseService<T, K> service, IBaseMapper<T, D, K> mapper) {
        super(service, mapper);
        this.service = service;
    }

    @PostMapping
    @ResponseBody
    public WsTypedResp<D> insert(@Valid @RequestBody D dto) throws AppException {
        // security check
        WsTypedResp<D> wsTypedResp = new WsTypedResp<>();
        T entity = mapper.dtoToEntity(dto);
        wsTypedResp.setData(mapper.entityToDto(service.insert(entity)));
        return wsTypedResp;
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public WsTypedResp<D> update(@PathVariable K id, @Valid @RequestBody D dto) throws AppException {
        WsTypedResp<D> wsTypedResp = new WsTypedResp<>();
        mapper.dtoToEntity(dto).setId(id);
        T entity = mapper.dtoToEntity(dto);
        wsTypedResp.setData(mapper.entityToDto(service.update(entity, FetchOptions.builder().userPermFilter(true).build())));
        return wsTypedResp;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public WsTypedResp<D> delete(@PathVariable K id) throws AppException {
        // security check
        WsTypedResp<D> wsTypedResp = new WsTypedResp<>();
        service.delete(id, FetchOptions.builder().userPermFilter(true).build());
        return wsTypedResp;
    }


}
