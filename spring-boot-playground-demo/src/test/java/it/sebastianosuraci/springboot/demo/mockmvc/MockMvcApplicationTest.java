package it.sebastianosuraci.springboot.demo.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import it.sebastianosuraci.springboot.demo.domain.School.SchoolCategory;
import it.sebastianosuraci.springboot.demo.dto.SchoolDTO;

@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MockMvcApplicationTests {

	@Autowired
    private WebApplicationContext webApplicationContext;
 
    private MockMvc mockMvc;
 
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    ObjectMapper objectMapper;
    ObjectWriter objectWriter;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    }

	@Test
	void readTestMockMvc() throws Exception {
		mockMvc.perform(get("/api/demo/school")).
		andExpect(status().isOk());
	}

    @Test
	void writeTestMockMvc() throws Exception {
        SchoolDTO schoolDTO = new SchoolDTO();

        mockMvc.perform(post("/api/demo/school")
        .contentType(APPLICATION_JSON_UTF8)
        .content(objectWriter.writeValueAsString(schoolDTO)))
		.andExpect(status().isBadRequest());

        schoolDTO.setName("New School");
        schoolDTO.setCategory(SchoolCategory.SC_HIGH);
 
        mockMvc.perform(post("/api/demo/school")
        .contentType(APPLICATION_JSON_UTF8)
        .content(objectWriter.writeValueAsString(schoolDTO)))
		.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.name", is("New School")));

	}

}
