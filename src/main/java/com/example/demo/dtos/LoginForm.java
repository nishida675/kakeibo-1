package com.example.demo.dtos;

import java.io.IOException;

import com.example.demo.entity.User;

import lombok.Data;

@Data
public class LoginForm {
	private String name;
	private String email;
	
	
	public LoginForm() {
		super();
	}
	
	public LoginForm(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public User toUser() throws IOException {
		return new User(this.name, this.email);
	}
}