package com.readingTom.bookService.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateProviderSingleton {
	/*
	 * this is a singleton class that provides the factory object 
	 */
	private static RestTemplate restTemplate;
	
	private RestTemplateProviderSingleton() {
		log.info("singleton rest template to call api has been created!");
	}
	
	public RestTemplate getRestTemplate() {
		log.info("sending restTemplate...");
		
		if(restTemplate == null) {
			log.info("no restTemplate existing, so creating a new object...");
			synchronized(RestTemplateProviderSingleton.class) { //using synchronized in case multiple threads try to access this for creation
				restTemplate = new RestTemplate();
			}
			
		}else {
			log.info("restTemplate object existing in the system so using the present one...");
		}
		
		return restTemplate;
	}
}
