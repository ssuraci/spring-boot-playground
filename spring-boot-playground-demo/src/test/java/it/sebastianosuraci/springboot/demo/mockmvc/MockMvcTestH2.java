package it.sebastianosuraci.springboot.demo.mockmvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.sebastianosuraci.springboot.demo.domain.School.SchoolCategory;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class MockMvcTestH2 {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void readTestMockMvc() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/api/demo/school")).
				andExpect(status().isOk());
	}

    @Test
	void writeTestMockMvc() throws Exception {
        SchoolDTO schoolDTO = new SchoolDTO();

        mockMvc.perform(post("/api/demo/school")
			.contentType(APPLICATION_JSON_UTF8)
			.content(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(schoolDTO)))
			.andExpect(status().isBadRequest());

        schoolDTO.setName("New School");
        schoolDTO.setCategory(SchoolCategory.SC_HIGH);
 
        mockMvc.perform(post("/api/demo/school")
			.contentType(APPLICATION_JSON_UTF8)
			.content(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(schoolDTO)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.name", is("New School")));

	}

}