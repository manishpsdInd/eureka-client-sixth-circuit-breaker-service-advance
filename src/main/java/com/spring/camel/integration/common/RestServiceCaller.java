package com.spring.camel.integration.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.camel.integration.constant.SpringCamelConstant;
import com.spring.camel.integration.dto.RequestDto;
import com.spring.camel.integration.dto.ServiceResponse;

@Service
@DefaultProperties
public class RestServiceCaller extends SpringCamelFallbackService {

	private static final Logger log = LoggerFactory.getLogger(RestServiceCaller.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("restTemplateWithEurekaDiscovery")
	RestTemplate restTemplateWithEurekaDiscovery;

	@HystrixCommand(fallbackMethod = "callSCIntIFallback", commandKey = "callSCIntIKey"
		/*  ,
			commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
				@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
				@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
				@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
			}
			groupKey = "RestServiceCaller",
		*/
		)
	public ServiceResponse callSCIntI(RequestDto request) throws Exception {

		log.info("RestServiceCaller callSCIntI() - entrying");
		
		ServiceResponse response = null;
		String REQUEST_URI = "http://eureka-client-first-service/";
		
		ResponseEntity<ServiceResponse> respEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		respEntity = restTemplateWithEurekaDiscovery.postForEntity(REQUEST_URI, request, ServiceResponse.class);
			
		log.info("RestServiceCaller callSCIntI() - Status: " + respEntity.getStatusCode());
		response = respEntity.getBody();
			
		log.info("RestServiceCaller callSCIntI() - exiting");
		return response;
			
	}

	@HystrixCommand(fallbackMethod = "callSCIntIIFallback", commandKey = "callSCIntIIKey")
	public ServiceResponse callSCIntII(RequestDto request) throws Exception {

		log.info("RestServiceCaller callSCIntII() - entrying");
		
		ServiceResponse response = null;
		String REQUEST_URI = "http://eureka-client-second-service/";
		
		ResponseEntity<ServiceResponse> respEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		respEntity = restTemplateWithEurekaDiscovery.postForEntity(REQUEST_URI, request, ServiceResponse.class);
			
		log.info("RestServiceCaller callSCIntII() - Status: " + respEntity.getStatusCode());
		response = respEntity.getBody();
			
		log.info("RestServiceCaller callSCIntII() - exiting");
		return response;
			
	}
	
	@HystrixCommand(fallbackMethod = "callSCIntIIIFallback", commandKey = "callSCIntIIIKey")
	public ServiceResponse callSCIntIII(RequestDto request) throws Exception {

		log.info("RestServiceCaller callSCIntIII() - entrying");
		
		ServiceResponse response = null;
		String REQUEST_URI = "http://eureka-client-third-service/";
		
		ResponseEntity<ServiceResponse> respEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		respEntity = restTemplateWithEurekaDiscovery.postForEntity(REQUEST_URI, request, ServiceResponse.class);
			
		log.info("RestServiceCaller callSCIntIII() - Status: " + respEntity.getStatusCode());
		response = respEntity.getBody();
			
		log.info("RestServiceCaller callSCIntIII() - exiting");
		if(respEntity.getStatusCode() == HttpStatus.OK) SpringCamelConstant.cachedSCIntResp = response;
		return response;
			
	}
	
	@HystrixCommand(fallbackMethod = "callNonEurekaSCIntIVFallback", commandKey = "callNonEurekaSCIntIVKey")
	public ServiceResponse callNonEurekaSCIntIV(Long sleepTime) throws Exception {

		log.info("RestServiceCaller callNonEurekaSCIntIV() - entrying");
		
		ServiceResponse response = null;
		String REQUEST_URI = "http://localhost:8084/sleep";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		
		HttpEntity<String> entity = new HttpEntity<>(sleepTime.toString(), headers);
		ResponseEntity<ServiceResponse> respEntity = new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		respEntity = restTemplate.exchange(REQUEST_URI, HttpMethod.POST, entity, ServiceResponse.class);

		log.info("RestServiceCaller callSCIntIII() - Status: " + respEntity.getStatusCode());
		response = respEntity.getBody();
			
		log.info("RestServiceCaller callNonEurekaSCIntIV() - exiting");
		return response;
			
	}
}