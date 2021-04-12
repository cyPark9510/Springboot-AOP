package com.pcy.aop.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pcy.aop.domain.CommonDto;

// @Controller, @RestController, @Component, @Configuration

// @Configuration => Controller 진입 하기 전 설정이 필요 할 때 사용!!
@Component
@Aspect
public class BindingAdvice {
	
	
	private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

	
	// 어떤 함수가 언제 몇번 실행 됐는지 횟수같은거 로그 남기기
	@Before("execution(* com.pcy.aop.web..*Controller.*(..))")
	public void testCheck() {
		// request 값 처리
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		System.out.println("주소 : " + request.getRequestURI());
		
		System.out.println("로그를 남겼습니다.");
	}
	
	// 함수 : 앞 (username이 안들어왔으면 내가 강제로 넣어주고 실행)
	// 함수 : 뒤 (응답만 관리)
	// 함수 : 앞 뒤
	
//	@Before
//	@After
	@Around("execution(* com.pcy.aop.web..*Controller.*(..))")
	public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();
		
		System.out.println("type : " + type);
		System.out.println("method : " + method);
		
		Object[] args = proceedingJoinPoint.getArgs();
		
		for(Object arg : args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult)arg;
				
				// 서비스 : 정상적인 화면 => 사용자 요청 
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						// 로그 레벨 error, warn, info, debug
						log.warn(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
						log.debug(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
						// DB연결 -> DB 남기기
						// File file = new File();
					}
					
					return new CommonDto<>(HttpStatus.BAD_REQUEST.value(), errorMap); 
				}
			}
		}
		
		return proceedingJoinPoint.proceed();  // 함수의 스택을 실행해라
	}
}
