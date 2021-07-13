package com.hackathon.domain.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String comment;

	@Column(nullable = false)
	private Boolean isSecret = false;

	@Column(nullable = false)
	private Long createAt = new Date().getTime();

	@Column(nullable = false)
	private Long updateAt = new Date().getTime();

	@ManyToOne
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	public Comment() {

	}

	public void add(Post post) {
		this.post = post;
		post.getComments().add(this);
	}

	public void add(User user) {
		this.user = user;
		user.getComments().add(this);
	}

	public Comment(String comment, Boolean isSecret, Post post, User user) {
		this.comment = comment;
		this.isSecret = isSecret;
		add(post);
		add(user);
	}

}
