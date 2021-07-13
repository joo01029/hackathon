package com.hackathon.domain.response.user;

import lombok.Data;

@Data
public class GetUserRO {
	private Long id;
	private String name;
	private Byte grade;
	private Byte classNum;

	public GetUserRO(Long id, String name, Byte grade, Byte classNum){
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.classNum = classNum;
	}

	public GetUserRO(Long id, String name){
		this.id = id;
		this.name = name;
		this.grade = 0;
		this.classNum = 0;
	}

}
