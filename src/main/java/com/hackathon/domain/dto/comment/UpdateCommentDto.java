package com.hackathon.domain.dto.comment;

import lombok.Data;

@Data
public class UpdateCommentDto {
	private String comment;
	private Boolean isSecret;
}
