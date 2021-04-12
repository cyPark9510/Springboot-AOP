package com.pcy.aop.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// Exception을 낚아채기 
@RestController
@ControllerAdvice  // 모든 Exception 관리 
public class MyExceptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public String argumentException(IllegalArgumentException e) {
		return "Exception : " + e.getMessage();
	}
}
