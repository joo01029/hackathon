package com.hackathon.service.school;

import com.hackathon.domain.entity.School;
import com.hackathon.domain.response.school.GetSchoolRO;

import java.util.List;

public interface SchoolService {
	School addSchool(String code, String name);
	List<GetSchoolRO> searchSchool(String query);
}
