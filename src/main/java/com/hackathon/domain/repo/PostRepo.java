package com.hackathon.domain.repo;

import com.hackathon.domain.entity.Post;
import com.hackathon.domain.entity.School;
import com.hackathon.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
	Optional<Post> findById(Long id);

	List<Post> findBySchoolOrderByUpdateAtDesc(School school);


	@Query(value = "select * from post where school_id = :school and (content like :value or title like :value) order by update_at desc",
			nativeQuery = true)
	List<Post> findBySchoolAndTitleLikeOrContentLike(@Param("school") Long school, @Param("value") String value);

	void removeByIdAndUser(Long id, User user);
}
