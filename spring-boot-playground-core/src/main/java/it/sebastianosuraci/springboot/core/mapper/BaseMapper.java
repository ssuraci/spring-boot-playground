package it.sebastianosuraci.springboot.core.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.mapstruct.BeforeMapping;
import org.mapstruct.TargetType;

public abstract class BaseMapper {
    @BeforeMapping
	public <T> Set<T> fixLazyLoadingSet(Collection<?> c, @TargetType Class<?> targetType) {
		if (!MapperUtils.wasInitialized(c)) {
			return Collections.emptySet();
		}
		return null;
	}

	@BeforeMapping
	public <T> List<T> fixLazyLoadingList(Collection<?> c, @TargetType Class<?> targetType) {
		if (!MapperUtils.wasInitialized(c)) {
			return Collections.emptyList();
		}
		return null;
	}
}
