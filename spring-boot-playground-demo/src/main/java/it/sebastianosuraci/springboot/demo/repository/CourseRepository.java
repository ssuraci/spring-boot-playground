package it.sebastianosuraci.springboot.demo.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.querydsl.core.BooleanBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.Course;
import it.sebastianosuraci.springboot.demo.domain.QCourse;


public interface CourseRepository extends BaseRepository<Course, Integer>, CourseRepositoryCustom {
    @Override
    default QBaseEntity getQBaseEntity() {
        return QCourse.course._super;    
    }
    public Logger logger = LoggerFactory.getLogger("it.sebastianosuraci.springboot.demo.repository.CourseRepository");

    @Override
    default BooleanBuilder addPageModelFilterPredicate(BooleanBuilder builder, PageModel pageModel) {
        QCourse course = QCourse.course;
        if (pageModel != null && pageModel.getF() != null) {

            for (Map.Entry<String, String> entry : pageModel.getF().entrySet()) {
                switch (entry.getKey()) {
                case "q":
                case "titleLike":
                    builder.and(course.title.likeIgnoreCase(entry.getValue() + '%'));
                    break;
                case "schoolEq":
                    builder.and(course.teacher().school().id.eq(Integer.valueOf(entry.getValue())));
                break;
                case "teacherEq":
                    builder.and(course.teacher().id.eq(Integer.valueOf(entry.getValue())));
                break;
                case "startDateAfter":
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            builder.and(course.startDate.after(sdf.parse(entry.getValue().substring(0, 10))));
                        } catch (ParseException e) {
                            logger.warn("Bad input filter");
                        }
                break;
                default:
                break;    
                }
            }
        }
        return builder;
    }

}
