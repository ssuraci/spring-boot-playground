package it.sebastianosuraci.springboot.demo.mockmvc.base;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpMethod;

import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;
import org.springframework.test.context.jdbc.Sql;


abstract class MockMvcBaseTestTeacher extends MockMvcBaseTest<TeacherDTO> {

	
	@Override
	protected String getEndpointUrl() {
		return "/api/demo/teacher";
	}

	
	@Test
	@Sql("/test-data/init-data.sql")
	void readTest01() throws Exception {
		String expectedJson = new String(Files.readAllBytes( Paths.get("src/test/resources/json/teacher_01_resp.json"))); 
		readTest("", status().isOk(), expectedJson);
	}

	@Test
	@Sql("/test-data/init-data.sql")
	void writeTest02() throws Exception {
		String reqJson = new String(Files.readAllBytes( Paths.get("src/test/resources/json/teacher_02_req.json"))); 
		writeTest(reqJson, HttpMethod.POST, status().isOk(), null);
	}
}
