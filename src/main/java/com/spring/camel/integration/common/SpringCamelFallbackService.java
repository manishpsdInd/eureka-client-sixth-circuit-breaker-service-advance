package com.spring.camel.integration.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.spring.camel.integration.constant.SpringCamelConstant;
import com.spring.camel.integration.dto.RequestDto;
import com.spring.camel.integration.dto.ServiceResponse;

@Service
public class SpringCamelFallbackService	{

	private static final Logger log = LoggerFactory.getLogger(RestServiceCaller.class);

	public ServiceResponse callSCIntIFallback(RequestDto request, Throwable throwable) throws Exception {

		ServiceResponse response = new ServiceResponse();
		response.setErrorCode("0");
		response.setErrorMsg("DEFAULT");
		response.setRespCode("0");
		response.setRespMsg("Returning default success response");

		log.info("RestServiceCaller callSCIntIFallback() - returning default response");
		return response;
	}

	public ServiceResponse callSCIntIIFallback(RequestDto request, Throwable throwable) throws Exception {

		ServiceResponse response = new ServiceResponse();
		response.setErrorCode("0");
		response.setErrorMsg("TRENDS");
		response.setRespCode("0");
		response.setRespMsg("Watch new trends, this will make your life happy !!");

		log.info("RestServiceCaller callSCIntIFallback() - returning default trending response");
		return response;
	}

	public ServiceResponse callSCIntIIIFallback(RequestDto request, Throwable throwable) throws Exception {

		log.info("RestServiceCaller callSCIntIFallback() - returning default cache response");
		return null != SpringCamelConstant.cachedSCIntResp ? SpringCamelConstant.cachedSCIntResp
				: callSCIntIFallback(null,null);
	}

	public ServiceResponse callNonEurekaSCIntIVFallback(Long sleepTime, Throwable throwable) throws Exception {

		ServiceResponse response = new ServiceResponse();
		response.setErrorCode("0");
		response.setErrorMsg("SLEEP");
		response.setRespCode("0");
		response.setRespMsg("Timed Out in Sleep");

		log.info("RestServiceCaller callNonEurekaSCIntIVFallback() - returning Timed Out in Sleep");
		return response;
	}
}
