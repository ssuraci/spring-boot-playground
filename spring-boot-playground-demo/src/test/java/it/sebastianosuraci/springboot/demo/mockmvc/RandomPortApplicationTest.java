package it.sebastianosuraci.springboot.demo.mockmvc;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import it.sebastianosuraci.springboot.demo.domain.School;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class RandomPortApplicationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void appTestRandomPort() throws Exception {
		School school =	this.restTemplate.getForObject("http://localhost:" + port + "/api/demo/school/1", School.class);
		Assert.assertEquals(1, school.getId().intValue());
	}

}
