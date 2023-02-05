package it.sebastianosuraci.springboot.demo.repository;

import java.util.Arrays;
import java.util.List;

import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.QSubject;
import it.sebastianosuraci.springboot.demo.domain.Subject;

public interface SubjectRepository extends BaseRepository<Subject, Integer> {
    default QBaseEntity getQBaseEntity() {
        return QSubject.subject._super._super;    
    }
    @Override
    default List<String> getAllowedOrderByList() {
		String[] s = { "id", "code" };
		return Arrays.asList(s);
	}
}
