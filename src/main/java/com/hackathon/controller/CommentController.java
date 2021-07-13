package com.hackathon.controller;

import com.hackathon.domain.dto.comment.UpdateCommentDto;
import com.hackathon.domain.dto.comment.WriteCommentDto;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.Response;
import com.hackathon.domain.response.ResponseData;
import com.hackathon.domain.response.comment.GetCommentRO;
import com.hackathon.service.comment.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@GetMapping("/{postId}")
	@ApiOperation("댓글 받아오기")
	public ResponseData<List<GetCommentRO>> getComments(@PathVariable Long postId, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		List<GetCommentRO> comments = commentService.getCommentList(postId, user);

		return new ResponseData<>(HttpStatus.OK, "성공", comments);
	}

	@PostMapping
	@ApiOperation("댓글 작성")
	public Response writeComment(@RequestBody WriteCommentDto writeCommentDto, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		commentService.writeComment(writeCommentDto, user);

		return new Response(HttpStatus.OK, "성공");
	}

	@PatchMapping("/{commentId}")
	@ApiOperation("댓글 수정")
	public Response updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentDto updateCommentDto, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		commentService.updateComment(commentId, updateCommentDto, user);

		return new Response(HttpStatus.OK, "성공");
	}

	@DeleteMapping("/{commentId}")
	@ApiOperation("댓글 삭제")
	public Response removeComment(@PathVariable Long commentId, HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		commentService.removeComment(commentId, user);

		return new Response(HttpStatus.OK, "성공");
	}
}

