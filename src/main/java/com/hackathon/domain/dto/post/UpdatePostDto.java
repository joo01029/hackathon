package com.hackathon.domain.dto.post;

import lombok.Data;

@Data
public class UpdatePostDto {
	private String title;
	private String content;
	private Boolean isSecret;
}
