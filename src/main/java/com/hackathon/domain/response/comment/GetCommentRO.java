package com.hackathon.domain.response.comment;

import com.hackathon.domain.entity.Comment;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.user.GetUserRO;
import lombok.Data;

import java.util.Date;

@Data
public class GetCommentRO {
	private Long id;
	private String comment;
	private GetUserRO user;
	private Date date;
	private Boolean isUpdated = false;
	private Boolean isMe = false;
	private Boolean isWriter = false;

	public GetCommentRO(Comment comment, User user) {
		this.id = comment.getId();
		this.comment = comment.getComment();

		this.date = comment.getUpdateAt();
		if (comment.getCreateAt().getTime() < comment.getUpdateAt().getTime()) {
			this.isUpdated = true;
		}

		if (comment.getUser().getIdx().equals(user.getIdx())) {
			this.isMe = true;
		}
		if(comment.getPost().getUser().getIdx().equals(comment.getUser().getIdx())){
			isWriter = true;
		}

		if (comment.getIsSecret()) {
			this.user = new GetUserRO(0L, "익명");
		} else {
			this.user = new GetUserRO(comment.getUser().getIdx(), comment.getUser().getName(), comment.getUser().getGrade(), comment.getUser().getClassNum());
		}
	}
}
