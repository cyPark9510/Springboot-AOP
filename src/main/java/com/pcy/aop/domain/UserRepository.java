package com.pcy.aop.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	public List<User> findAll(){
		List<User> users = new ArrayList<>();
		users.add(new User(1, "hello", "1234", "010-1234-5678"));
		users.add(new User(2, "bye", "1234", "010-1234-5678"));
		users.add(new User(3, "love", "1234", "010-1234-5678"));
		
		return users;
	}
	
	public User findById(int id){
		
		return new User(1, "hello", "1234", "010-1234-5678");
	}
	
	public void save(JoinReqDto dto) {
		System.out.println("DB에 insert하기!!");
	}
	
	public void update(int id, UpdateReqDto dto) {
		System.out.println("DB에 update하기!!");
	}
	
	public void delete(int id) {
		System.out.println("DB에 delete하기!!");
	}
}
