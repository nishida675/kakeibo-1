package com.example.demo.entity;

import java.io.Serializable;

import com.example.demo.dtos.LoginForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="user_table")
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer id;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="email")
	private String email;
	
	
	public User() {
		super();
	}
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("name:%s, mail:%s", this.name,this.email);
	}
	
	public LoginForm toLoginForm() {
		return new LoginForm(this.name, this.email);
	}
}