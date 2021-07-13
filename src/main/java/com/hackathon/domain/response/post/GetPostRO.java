package com.hackathon.domain.response.post;

import com.hackathon.domain.entity.Post;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.user.GetUserRO;
import lombok.Data;

import java.util.Date;

@Data
public class GetPostRO {
	private Long id;
	private String title;
	private String content;
	private GetUserRO user;
	private Long commentsNum;
	private Long date;
	private Boolean isUpdated = false;
	private Boolean isMe;

	public GetPostRO(Post post, User user, Long commentsNum) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.isMe = false;
		this.date = post.getUpdateAt();
		this.commentsNum = commentsNum;

		if (post.getCreateAt() < post.getUpdateAt()) {
			this.isUpdated = true;
		}

		if (post.getUser().getIdx().equals(user.getIdx())) {
			this.isMe = true;
		}

		if (post.getIsSecret()) {
			this.user = new GetUserRO(0L, "익명");
		} else {
			this.user = new GetUserRO(post.getUser().getIdx(), post.getUser().getName(), post.getUser().getGrade(), post.getUser().getClassNum());
		}
	}
}
