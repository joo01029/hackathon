package com.hackathon.service.comment;

import com.hackathon.domain.dto.comment.UpdateCommentDto;
import com.hackathon.domain.dto.comment.WriteCommentDto;
import com.hackathon.domain.entity.Comment;
import com.hackathon.domain.entity.Post;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.repo.CommentRepo;
import com.hackathon.domain.repo.PostRepo;
import com.hackathon.domain.response.comment.GetCommentRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;

	@Override
	@Transactional
	public void writeComment(WriteCommentDto writeCommentDto, User user) {
		try {
			Post post = postRepo.findById(writeCommentDto.getPostId()).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "존재하지 않는 게시글입니다.");
			});

			Comment comment = new Comment(writeCommentDto.getComment(), writeCommentDto.getIsSecret(), post, user);
			commentRepo.save(comment);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<GetCommentRO> getCommentList(Long postId, User user) {
		try {
			Post post = postRepo.findById(postId).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "존재하지 않는 게시글입니다.");
			});

			List<Comment> comments = commentRepo.findByPostOrderByCreateAt(post);

			List<GetCommentRO> responseComments = new ArrayList<>();
			for(Comment comment:comments){
				GetCommentRO responseComment = new GetCommentRO(comment,user);
				responseComments.add(responseComment);
			}
			return responseComments;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	@Transactional
	public void updateComment(Long commentId, UpdateCommentDto updateCommentDto, User user) {
		try {
			Comment comment = commentRepo.findById(commentId).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "존재하지 않는 댓글입니다.");
			});
			if (!comment.getUser().getIdx().equals(user.getIdx())) {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "유저의 게시글이 아닙니다.");
			}

			comment.setComment(updateCommentDto.getComment());
			comment.setIsSecret(updateCommentDto.getIsSecret());
			comment.setUpdateAt(new Date().getTime());
			commentRepo.save(comment);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void removeComment(Long commentId, User user) {
		try {
			Comment comment = commentRepo.findById(commentId).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "존재하지 않는 댓글입니다.");
			});
			if (!comment.getUser().getIdx().equals(user.getIdx())) {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "유저의 게시글이 아닙니다.");
			}

			commentRepo.removeByIdAndUser(commentId, user);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
