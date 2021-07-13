package com.hackathon.service.comment;

import com.hackathon.domain.dto.comment.UpdateCommentDto;
import com.hackathon.domain.dto.comment.WriteCommentDto;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.response.comment.GetCommentRO;

import java.util.List;

public interface CommentService {
	void writeComment(WriteCommentDto writeCommentDto, User user);

	List<GetCommentRO> getCommentList(Long postId, User user);

	void updateComment(Long commentId, UpdateCommentDto updateCommentDto, User user);

	void removeComment(Long commentId, User user);
}
