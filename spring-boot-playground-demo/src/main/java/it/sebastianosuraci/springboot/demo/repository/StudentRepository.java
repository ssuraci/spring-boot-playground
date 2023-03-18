package it.sebastianosuraci.springboot.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.querydsl.core.BooleanBuilder;


import it.sebastianosuraci.springboot.core.domain.QBaseEntity;
import it.sebastianosuraci.springboot.core.dto.PageModel;
import it.sebastianosuraci.springboot.core.repository.BaseRepository;
import it.sebastianosuraci.springboot.demo.domain.QStudent;
import it.sebastianosuraci.springboot.demo.domain.Student;


public interface StudentRepository extends BaseRepository<Student, Integer> {

    // QueryDSL
    default List<Student> qdslFindByLastName(String lastName) {
        QStudent student = QStudent.student;
        List<Student> result = new ArrayList<>();
        findAll(student.lastName.like(lastName)).forEach(result::add);
        return result;
    }
	
    @Override
    default List<String> getAllowedOrderByList() {
		String[] s = { "id", "lastName", "birthDate" };
		return Arrays.asList(s);
	}

    @Override
    default BooleanBuilder addPageModelFilterPredicate(BooleanBuilder builder, PageModel pageModel) {
        QStudent student = QStudent.student;
        if (pageModel != null && pageModel.getF() != null) {
            for (Map.Entry<String, String> entry : pageModel.getF().entrySet()) {
                switch (entry.getKey()) {
                case "q":
                case "lastNameLike":
                    builder.and(student.lastName.likeIgnoreCase(entry.getValue() + '%'));
                    break;
                case "schoolEq":
                    builder.and(student.school().id.eq(Integer.parseInt(entry.getValue())));
                break;
                    default:
                    break;
                }
            }
        }
    return builder;
    }

    default QBaseEntity getQBaseEntity() {
        return QStudent.student._super;    
    }

}
