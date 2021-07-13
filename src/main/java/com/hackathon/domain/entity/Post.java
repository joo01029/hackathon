package com.hackathon.domain.entity;

import com.hackathon.domain.dto.post.WritePostDto;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Boolean isSecret = false;

	@Column(nullable = false)
	private Date createAt = new Date();

	@Column(nullable = false)
	private Date updateAt = new Date();

	@ManyToOne
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@ManyToOne
	@JoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	private School school;

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	public Post() {

	}

	public void add(User user) {
		School school = user.getSchool();
		this.setUser(user);
		this.setSchool(school);
		school.getPost().add(this);
		user.getPosts().add(this);
	}

	public Post(WritePostDto writePostDto, User user) {
		this.title = writePostDto.getTitle();
		this.content = writePostDto.getContent();
		this.isSecret = writePostDto.getIsSecret();
		add(user);
	}
}
