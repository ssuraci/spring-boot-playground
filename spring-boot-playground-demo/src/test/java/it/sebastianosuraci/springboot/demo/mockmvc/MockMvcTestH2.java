package it.sebastianosuraci.springboot.demo.mockmvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sebastianosuraci.springboot.demo.domain.School.SchoolCategory;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
// @ActiveProfiles("test")
class MockMvcTestH2 {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Sql("/test-data/init-data.sql")
	void readTestMockMvc() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/api/demo/school")).
				andExpect(status().isOk());
	}


	@Autowired
	ObjectMapper objectMapper;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @Test
	@Sql("/test-data/init-data.sql")
	void writeTestMockMvc() throws Exception {
        SchoolDTO schoolDTO = new SchoolDTO();

		// we try to insert a school with empty fields, so validation is triggered
		// and causes a BadRequest
        mockMvc.perform(post("/api/demo/school")
			.contentType(APPLICATION_JSON_UTF8)
			// writes JSON body
			.content(objectMapper.writeValueAsString(schoolDTO)))
			.andExpect(status().isBadRequest());

        schoolDTO.setName("New School");
        schoolDTO.setCategory(SchoolCategory.SC_HIGH);

		// now the fields are OK, and we verify the returned data
        mockMvc.perform(post("/api/demo/school")
			.contentType(APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(schoolDTO)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.name", is("New School")));

	}
	@Test
	@Sql({"/test-data/init-data.sql", "/test-data/course-data.sql"})
	void courseReadTest() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/api/demo/course"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title").value("Corso Base Angular"));
	}

}
