package it.sebastianosuraci.springboot.demo.repository;

import java.util.Map;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

import org.springframework.data.jpa.repository.EntityGraph;

import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.core.repository.PageModel;
import it.sebastianosuraci.springboot.demo.domain.QSchool;
import it.sebastianosuraci.springboot.demo.domain.School;

public interface SchoolRepository extends BaseRepository<School, Integer> {

    @EntityGraph(attributePaths = { "teacherList" })
    Optional<School> findOneById(Integer id);

    @Override
    default BooleanBuilder buildCriteria(BooleanBuilder builder, PageModel pageModel) {
            QSchool school = QSchool.school;

            for (Map.Entry<String, String> entry : pageModel.getF().entrySet()) {
                switch (entry.getKey()) {
                case "q":
                case "nameLike":
                        builder.and(school.name.likeIgnoreCase(entry.getValue() + '%'));
                        break;
                default:
                break;    
                }
    }
    return builder;
    }

}
