package com.hackathon.domain.repo;

import com.hackathon.domain.entity.Comment;
import com.hackathon.domain.entity.Post;
import com.hackathon.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
	List<Comment> findByPostOrderByCreateAtDesc(Post post);

	Optional<Comment> findById(Long id);

	void removeByIdAndUser(Long id, User user);

	Long countByPost(Post post);
}
