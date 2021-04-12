# Springboot-AOP

# AOP란?

 - Aspect Oriented Programming
 - 관점 지향 프로그래밍

 <img width="546" alt="Class" src="https://user-images.githubusercontent.com/50781066/114400093-95025300-9bdc-11eb-8405-0cbf38bbe602.png">

 - 흩어진 Aspect를 모듈화 할 수 있는 프로그래밍 기법

<img width="629" alt="AOP" src="https://user-images.githubusercontent.com/50781066/114400106-9764ad00-9bdc-11eb-9656-c19675ed1e75.png">

## 주요 개념

### 키워드

 - Aspect : 여러 곳에서 쓰이는 코드(공통 부분)를 모듈화 한 것
 - Target : Aspect가 적용되는 곳
 - Advice : Aspect에서 실질적인 기능에 대한 구현체
 - Joint Point : Advice가 Target에 적용되는 시점
    (ex : 필드, 메소드 / 스프링에서는 메소드만 해당)
 - Point Cut : Joint Point의 부분으로 실제로 Advice가 적용된 부분
 - Weaving : Advice를 핵심기능에 적용하는 행위

### Advice

 - @Before
    Advice의 Target 메서드가 호출되기 전에 실행되는 Advice
 - @After
    Advice의 Target 메서드가 호출된 후, 그 결과에 상관없이(성공 or 실패) 실행되는 Advice
 - @AfterReturning
    Target 메서드가 정상적으로 실행된 후에 실행되는 Advice
 - @AfterThrowingAdvice
    Target 메서드가 수행도중 예외가 발생했을 때 예외를 던지게 되면 실행되는 Advice
 - @Around
    Advice가 Target 메서드를 감싸서 Target 메서드 호출 전과 후에 실행되는 Advice
    @Aroundsms ProceedingJoinPoint의 proceed()를 수행하는데, proceed()의 리턴 값이 sub 메서드의 리턴 값이다.
    즉, @Advice 내부에서 결과값을 제어할 수 있다.

![ProxyAOP](https://user-images.githubusercontent.com/50781066/114400113-992e7080-9bdc-11eb-823f-7544ac35d8c0.jpeg)

## PointCut 표현식

 - execution : Advice를 적용할 메서드를 명시할 때 사용
 - within : 특정 타입에 속하는 메서드를 JoinPoint로 설정되도록 명시할 때 사용
 - bean : 스프링 버전 2.5 버전부터 지원하기 시작했으며, 스프링 빈을 이용하여 JoinPoint를 설정

## execution 명시자

 execution([수식어]리턴 타입[클래스 이름].이름(파라미터))

    수식어 : public, private 등 수식어를 명시(생략가능)
    리턴 타입 : 리턴 타입을 명시
    클래스 이름 및 이름 : 클래스 이름과 메서드 이름을 명시
        (클래스 이름은 풀 패키지 명으로 명시해야 한다. 클래스 이름은 생략할 수 있다.)
    파라미터 : 메서드의 파라미터를 명시
    "*" : 모든 값을 표현
    ".." : 0개 이상을 의미

### LogBack 설정 파일(logback-spring.xml)

```java
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <property name="LOGS_ABSOLUTE_PATH" value="./logs" />

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>[%d{yyyy-MM-dd HH:mm:ss}:%-3relative][%thread] %-5level %logger{36} - %msg%n</Pattern>
        </layout>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOGS_ABSOLUTE_PATH}/logback.log</file>
        <encoder>
            <pattern>[%d{yyyy-MM-dd HH:mm:ss}:%-3relative][%thread] %-5level %logger{35} - %msg%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOGS_ABSOLUTE_PATH}/logback.%d{yyyy-MM-dd}.%i.log.gz</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>5MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>

	<root level="INFO">
    	<appender-ref ref="STDOUT" />
    </root>
    
    <logger name="com.pcy.aop.config" level="WARN">
        <appender-ref ref="FILE" />
    </logger>

</configuration>
```
