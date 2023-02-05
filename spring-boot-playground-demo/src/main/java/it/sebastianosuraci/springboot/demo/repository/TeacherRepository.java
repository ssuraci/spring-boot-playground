package it.sebastianosuraci.springboot.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.QTeacher;
import it.sebastianosuraci.springboot.demo.domain.Teacher;


public interface TeacherRepository extends BaseRepository<Teacher, Integer> {

    @EntityGraph(attributePaths = { "courseList" })
    Optional<Teacher> findOneById(Integer id);

    // Spring data
    List<Teacher> findByLastName(String lastName);
    Optional<Teacher> findOneByEmail(String email);
    List<Teacher> findByLastNameAndEmail(String lastName, String email);

    // JPQL 1
    List<Teacher> qFindByLastName(String lastName);

    // JPQL 2
    @Query("select t from Teacher t where t.lastName=:lastName")
    List<Teacher> qFindByLastName2(@Param("lastName") String lastName);

    List<Teacher> findByEmail(String lastName);

    // Native Query
    List<Teacher> nqFindByLastName(String lastName);

    // QueryDSL
    default List<Teacher> qdslFindByLastName(String lastName) {
        QTeacher teacher = QTeacher.teacher;
        List<Teacher> result = new ArrayList<>();
        findAll(teacher.lastName.like(lastName)).forEach(result::add);
        return result;
    }
	
    @Override
    default List<String> getAllowedOrderByList() {
		String[] s = { "id", "lastName", "birthDate" };
		return Arrays.asList(s);
	}

    @Override
    default BooleanBuilder addPageModelFilterPredicate(BooleanBuilder builder, PageModel pageModel) {
        QTeacher teacher = QTeacher.teacher;
        if (pageModel != null && pageModel.getF() != null) {
            for (Map.Entry<String, String> entry : pageModel.getF().entrySet()) {
                switch (entry.getKey()) {
                case "q":
                case "lastNameLike":
                    builder.and(teacher.lastName.likeIgnoreCase(entry.getValue() + '%'));
                    break;
                case "schoolIdEq":
                    builder.and(teacher.school.id.eq(Integer.parseInt(entry.getValue())));
                    break;
                case "subjectIdEq":
                    builder.and(teacher.subject.id.eq(Integer.parseInt(entry.getValue())));
                break;
                    default:
                    break;
                }
            }
        }
    return builder;
    }

    default QBaseEntity getQBaseEntity() {
        return QTeacher.teacher._super._super;    
    }

}
