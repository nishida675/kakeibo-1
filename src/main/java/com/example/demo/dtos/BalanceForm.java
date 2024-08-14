package com.example.demo.dtos;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.demo.entity.Balance;

import lombok.Data;

@Data
public class BalanceForm {
	private String userId;
	private String balanceName;
	private String date;
	private String price;
	private String category;

	
	public BalanceForm() {
		super();
	}
	
	public BalanceForm(String userId, String balanceName, String date, String price, String category) {
	        this.userId = userId;
	        this.balanceName = balanceName;
	        this.date = date;
	        this.price = price;
	        this.category = category;
	    }
	
	  public Balance toBalance() throws IOException {
		    Integer UserId = Integer.parseInt(this.userId);
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // 日付フォーマットに応じて調整
	        LocalDate dateAsLocalDate = LocalDate.parse(this.date, formatter);;
		    Integer priceAsInteger = Integer.parseInt(this.price);
	        return new Balance(UserId, this.balanceName, dateAsLocalDate, priceAsInteger, this.category);
	    }
}