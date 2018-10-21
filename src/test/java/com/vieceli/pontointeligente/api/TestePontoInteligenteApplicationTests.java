package com.vieceli.pontointeligente.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") //vai usar o application-test-properties
public class TestePontoInteligenteApplicationTests {

	@Test
	public void contextLoads() {
	}

}
