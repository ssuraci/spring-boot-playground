package it.sebastianosuraci.springboot.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;

import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.demo.domain.QCourse;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Course> qrFindByTitle(String title) {
        QCourse course = QCourse.course;
        JPAQuery<Course> query = new JPAQuery<>(em);
        return query.from(course).where(course.title.eq(title)).fetch();
    }
    
}
