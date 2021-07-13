package com.hackathon.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String code;

	@Column
	private String name;

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "school", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<User> users = new ArrayList<>();

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "school", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Post> post = new ArrayList<>();

	public School(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public School() {

	}
}
