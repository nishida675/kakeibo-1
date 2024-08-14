package com.example.demo.beans;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.dtos.LoginForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@Component
@SessionScope
public class SessionControl implements Serializable {
	@Autowired
	private UserRepository userRepository;
	private User user;
	private transient Logger logger = LoggerFactory.getLogger(SessionControl.class);
	
	public boolean login(LoginForm loginForm) {
		this.user = this.userRepository.findByEmailAndPassword(loginForm.getName(), loginForm.getEmail());
		if (user == null) {
			return false;
		} else {
			this.logger.info("log in(" + this.user + ")");
			return true;
		}
	}
	
	public void logout() {
		this.user = null;
	}
	
	public boolean isLogin() {
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public User getUser() {
		return this.user;
	}
}