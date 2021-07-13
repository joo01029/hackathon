package com.hackathon.domain.dto.post;

import lombok.Data;

@Data
public class WritePostDto {
	private String title;
	private String content;
	private Boolean isSecret;
}
