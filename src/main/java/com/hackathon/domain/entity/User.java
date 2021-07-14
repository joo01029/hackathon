package com.hackathon.domain.entity;

import com.hackathon.domain.dto.auth.SigninDto;
import com.hackathon.enums.Admin;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	@Column(unique = true)
	private String id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Byte grade;

	@Column(nullable = false)
	private Byte classNum;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Admin isAdmin = Admin.USER;

	@ManyToOne
	@JoinColumn(nullable = false)
	private School school;

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Post> posts = new ArrayList<>();

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	public User() {

	}

	public void add(School school) {
		this.school = school;
		school.getUsers().add(this);
	}

	public User(SigninDto signinDto, School school) {
		this.id = signinDto.getId();
		this.password = signinDto.getPassword();
		this.name = signinDto.getName();
		this.grade = signinDto.getGrade();
		this.classNum = signinDto.getClassNum();
		add(school);
	}
}
