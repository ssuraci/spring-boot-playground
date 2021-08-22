package it.sebastianosuraci.springboot.core.service;

import it.sebastianosuraci.springboot.core.dto.PageModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FetchOptions {
    protected PageModel pageModel;
    protected boolean userPermFilter;
    protected boolean exceptionIfNotFound;
}
