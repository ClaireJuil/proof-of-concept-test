package fr.ans.psc;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class AppTest {
	
		@Bean 
		public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
		}
		
}
