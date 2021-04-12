package com.pcy.aop.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pcy.aop.domain.CommonDto;
import com.pcy.aop.domain.JoinReqDto;
import com.pcy.aop.domain.UpdateReqDto;
import com.pcy.aop.domain.User;
import com.pcy.aop.domain.UserRepository;

@RestController
public class UserController {

	private UserRepository userRepository;
	
	// DI = 의존성 주입 
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// http://localhost:8080/user
	@GetMapping("/user")
	public CommonDto<List<User>> findAll() {
		System.out.println("findAll()");
		
		return new CommonDto<>(HttpStatus.OK.value(), userRepository.findAll());  // MessageConverter (JavaObject -> Json String)
	}

	// http://localhost:8080/user/1
	@GetMapping("/user/{id}")
	public CommonDto<User> findById(@PathVariable int id) {
		System.out.println("findById() : id : " + id);
		
		return new CommonDto<>(HttpStatus.OK.value(), userRepository.findById(id));
	}

	// http://localhost:8080/user
	@PostMapping("/user")
	// x-www-form-urlencoded => request.getParameter()
	// text/plain => @RequestBody 어노테이션 
	// appication/json => @ResponseBody 어노테이션 + 오브젝트로 받기 
	public CommonDto<?> save(@Valid @RequestBody JoinReqDto dto, BindingResult bindingResult) {
		System.out.println("save()");
		
//		System.out.println("username : " + username);
//		System.out.println("password : " + password);
//		System.out.println("phone : " + phone);
		
//		System.out.println("data : " + data);
		
		System.out.println("dto : " + dto);
		
		userRepository.save(dto);
		
		return new CommonDto<>(HttpStatus.CREATED.value(), "ok");
	}

	// http://localhost:8080/user/1
	@PutMapping("/user/{id}")
	public CommonDto update(@PathVariable int id, @Valid @RequestBody UpdateReqDto dto, BindingResult bindingResult) {
		System.out.println("update()");
		
		userRepository.update(id, dto);
		
		return new CommonDto<>(HttpStatus.OK.value());
	}

	// http://localhost:8080/user/1
	@DeleteMapping("/user/{id}")
	public CommonDto delete(@PathVariable int id) {
		System.out.println("delete()");
		userRepository.delete(id);
		
		return new CommonDto<>(HttpStatus.OK.value());
	}
}
