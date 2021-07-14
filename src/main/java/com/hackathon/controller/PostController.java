package com.hackathon.controller;

import com.hackathon.domain.dto.post.UpdatePostDto;
import com.hackathon.domain.dto.post.WritePostDto;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.Response;
import com.hackathon.domain.response.ResponseData;
import com.hackathon.domain.response.post.GetPostRO;
import com.hackathon.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostService postService;

	@GetMapping
	@ApiOperation("게시글 받아오기")
	public ResponseData<List<GetPostRO>> getPost(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		List<GetPostRO> posts = postService.getPosts(user);

		return new ResponseData<>(HttpStatus.OK, "성공", posts);
	}


	@PostMapping
	@ApiOperation("게시글 작성")
	public Response writePost(@RequestBody WritePostDto writePostDto, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		postService.writePost(writePostDto, user);

		return new Response(HttpStatus.OK, "성공");
	}

	@PatchMapping("/{postId}")
	@ApiOperation("게시글 수정")
	public Response updatePost(@PathVariable Long postId, @RequestBody UpdatePostDto updatePostDto, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		postService.updatePost(postId, updatePostDto, user);

		return new Response(HttpStatus.OK, "성공");
	}

	@GetMapping("search/{searchValue}")
	@ApiOperation("게시글 검색")
	public ResponseData<List<GetPostRO>> searchPost(@PathVariable String searchValue, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		List<GetPostRO> posts = postService.getPosts(user, searchValue);

		return new ResponseData<>(HttpStatus.OK, "성공", posts);
	}

	@GetMapping("detail/{postId}")
	@ApiOperation("게시글 상세")
	public ResponseData<GetPostRO> getPost(@PathVariable Long postId, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		GetPostRO posts = postService.getPost(postId, user);

		return new ResponseData<>(HttpStatus.OK, "성공", posts);
	}

	@DeleteMapping("/{postId}")
	@ApiOperation("게시글 삭제")
	public Response updatePost(@PathVariable Long postId, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		postService.removePost(postId, user);

		return new Response(HttpStatus.OK, "성공");
	}
}
