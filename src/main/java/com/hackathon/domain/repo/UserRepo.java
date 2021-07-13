package com.hackathon.domain.repo;

import com.hackathon.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findById(String id);
}
