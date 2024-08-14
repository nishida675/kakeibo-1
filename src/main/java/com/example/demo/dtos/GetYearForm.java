package com.example.demo.dtos;



import lombok.Data;

@Data
public class GetYearForm {
	private String userId;
	private String dataYear;
	
	public GetYearForm() {
		super();
	}
	
	public GetYearForm(String userId, String dataYear) {
		this.userId = userId;
		this.dataYear = dataYear;
	}
	
	

}