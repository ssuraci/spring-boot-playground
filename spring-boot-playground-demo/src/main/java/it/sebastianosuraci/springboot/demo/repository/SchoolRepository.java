package it.sebastianosuraci.springboot.demo.repository;

import java.util.Map;
import java.util.Optional;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.querydsl.core.BooleanBuilder;

import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.QSchool;
import it.sebastianosuraci.springboot.demo.domain.School;

public interface SchoolRepository extends BaseRepository<School, Integer> {

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = { "teacherList" })
    Optional<School> findOneById(Integer id);

    @Override
    default BooleanBuilder addPageModelFilterPredicate(BooleanBuilder builder, PageModel pageModel) {
        QSchool school = QSchool.school;
        if (pageModel != null && pageModel.getF() != null) {

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
        }
        return builder;
    }

    default QBaseEntity getQBaseEntity() {
        return QSchool.school._super._super;    
    }

    @Override
    default Optional<EntityGraph> getEntityGraph(String fetchProfile) {
        if (fetchProfile == null) {
            return Optional.empty();
        }
		switch (fetchProfile) {
            case "detail":
                return Optional.of(EntityGraphs.named("school.teacherList"));
            case "detailWithCourse":
                return Optional.of(EntityGraphs.named("school.teacherListAndCourse"));
            default:
                return Optional.empty();    
        }
	}
}
