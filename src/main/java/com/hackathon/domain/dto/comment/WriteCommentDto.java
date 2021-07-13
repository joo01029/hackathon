package com.hackathon.domain.dto.comment;

import lombok.Data;

@Data
public class WriteCommentDto {
	private Long postId;
	private String comment;
	private Boolean isSecret;
}
