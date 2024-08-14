package com.example.demo.entity;

import java.time.LocalDate;

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
@Table(name="balance_table")
public class Balance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="balance_id")
	private Integer balanceId;
	
	@NotNull
	@Column(name="user_id")
	private Integer userId;
	
	@NotNull
	@Column(name="balance_name")
	private String balanceName;
	
	@NotNull
	@Column(name="date")
	private LocalDate date;
	
	@NotNull
	@Column(name="price")
	private Integer price;
	
	@NotNull
	@Column(name="category")
	private String category;
	
	public Balance() {
		super();
	}
	
	public Balance(Integer userId, String balanceName, @NotNull LocalDate date, @NotNull Integer price, @NotNull String category) {
		this.userId = userId;
		this.balanceName = balanceName;
		this.date = date;
		this.price = price;
		this.category = category;
	}
	
	@Override
	public String toString() {
	    return String.format("userId: %d, balanceName: %s, date: %s, price: %d, category: %s", 
	                         this.userId, this.balanceName, this.date, this.price, this.category);
	}


}