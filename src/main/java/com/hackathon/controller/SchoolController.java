package com.hackathon.controller;

import com.hackathon.domain.response.ResponseData;
import com.hackathon.domain.response.school.GetSchoolRO;
import com.hackathon.service.school.SchoolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {
	@Autowired
	private SchoolService schoolService;

	@GetMapping("/search-school")
	@ApiOperation("학교 검색")
	public ResponseData<List<GetSchoolRO>> getSchoolList(@RequestParam("query") String query){
		List<GetSchoolRO> schools = schoolService.searchSchool(query);

		return new ResponseData<List<GetSchoolRO>>(HttpStatus.OK, "성공", schools);
	}

}
