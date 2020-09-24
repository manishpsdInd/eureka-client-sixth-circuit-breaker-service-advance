package com.spring.camel.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SpringCamelIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCamelIntegrationApplication.class, args);
	}
	
	@Primary
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@LoadBalanced
	@Bean("restTemplateWithEurekaDiscovery")
	public RestTemplate restTemplateWithEurekaDiscovery(RestTemplateBuilder builder) {
	     return builder.build();
	}
}