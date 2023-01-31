package it.sebastianosuraci.springboot.demo.mockmvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import it.sebastianosuraci.springboot.demo.dto.TeacherDTO;

class TestMockMvcTeacher extends MockMvcBaseTest<TeacherDTO> {

	
	@Override
	protected String getEndpointUrl() {
		return "/api/demo/teacher";
	}

	
	@Test
	void readTest01() throws Exception {
		String expectedJson = new String(Files.readAllBytes( Paths.get("src/test/resources/json/teacher_01_resp.json"))); 
		readTest("", status().isOk(), expectedJson);
	}

	@Test
	void writeTest02() throws Exception {
		String reqJson = new String(Files.readAllBytes( Paths.get("src/test/resources/json/teacher_02_req.json"))); 
		writeTest(reqJson, HttpMethod.POST, status().isOk(), null);
	}
}
