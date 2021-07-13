package com.hackathon.domain.repo;

import com.hackathon.domain.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepo extends JpaRepository<School, Long> {
	Optional<School> findByCode(String code);
}
