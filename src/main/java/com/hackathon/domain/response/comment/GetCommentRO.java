package com.hackathon.domain.response.comment;

import com.hackathon.domain.entity.Comment;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.user.GetUserRO;
import com.hackathon.enums.Admin;
import com.hackathon.lib.FormatDate;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.Date;

@Data
public class GetCommentRO {
	private Long id;
	private String comment;
	private GetUserRO user;
	private String date;
	private Boolean isUpdated = false;
	private Boolean isMe = false;
	private Boolean isWriter = false;
	private Boolean isAdmin;


	public GetCommentRO(Comment comment, User user) {
		this.id = comment.getId();
		this.comment = comment.getComment();
		this.isAdmin = false;
		this.date = FormatDate.formatDate(comment.getUpdateAt());

		if(user.getIsAdmin() == Admin.ADMIN){
			isAdmin = true;
		}

		if (comment.getCreateAt() < comment.getUpdateAt()) {
			this.isUpdated = true;
		}

		if (comment.getUser().getIdx().equals(user.getIdx())) {
			this.isMe = true;
		}
		if(comment.getPost().getUser().getIdx().equals(comment.getUser().getIdx())){
			isWriter = true;
		}

		if (comment.getIsSecret()&&user.getIsAdmin() == Admin.USER) {
			this.user = new GetUserRO(0L, "익명");
		} else {
			this.user = new GetUserRO(comment.getUser().getIdx(), comment.getUser().getName(), comment.getUser().getGrade(), comment.getUser().getClassNum());
		}
	}
}
