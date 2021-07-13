package com.hackathon.service.post;

import com.hackathon.domain.dto.post.UpdatePostDto;
import com.hackathon.domain.dto.post.WritePostDto;
import com.hackathon.domain.entity.Post;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.post.GetPostRO;

import java.util.List;

public interface PostService {
	void writePost(WritePostDto writePostDto, User user);

	List<GetPostRO> getPosts(User user);

	List<GetPostRO> getPosts(User user, String postValue);

	GetPostRO getPost(Long postId, User user);

	void updatePost(Long postId, UpdatePostDto updatePostDto, User user);

	void removePost(Long postId, User user);
}
