package it.sebastianosuraci.springboot.demo.repository;

import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.demo.domain.QCourse;


public interface CourseRepository extends BaseRepository<Course, Integer>, CourseRepositoryCustom {
    @Override
    default QBaseEntity getQBaseEntity() {
        return QCourse.course._super._super;    
    }


}
