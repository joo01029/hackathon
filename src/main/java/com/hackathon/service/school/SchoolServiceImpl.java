package com.hackathon.service.school;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hackathon.domain.entity.School;
import com.hackathon.domain.repo.SchoolRepo;
import com.hackathon.domain.response.school.GetSchoolRO;
import com.hackathon.lib.ApiRequest;
import com.hackathon.lib.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.HttpServerErrorException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SchoolServiceImpl implements SchoolService{
	@Autowired
	private SchoolRepo schoolRepo;

	@Override
	@Transactional
	public School addSchool(String code, String name) {
		try {
			return schoolRepo.findByCode(code).orElseGet(() -> {
				School school = new School(code, name);
				schoolRepo.save(school);
				return school;
			});
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}


	@Override
	public List<GetSchoolRO> searchSchool(String query) {
		try {
			Url apiUrl = new Url("https://open.neis.go.kr/hub/schoolInfo");
			apiUrl.add("KEY", URLEncoder.encode("9fec8d8188e9447f84282212805aa55d", "UTF-8"));
			apiUrl.add("Type", URLEncoder.encode("json", "UTF-8"));
			apiUrl.add("pIndex", URLEncoder.encode("1", "UTF-8"));
			apiUrl.add("pSize", URLEncoder.encode("20", "UTF-8"));
			apiUrl.add("SCHUL_NM", URLEncoder.encode(query, "UTF-8"));

			JsonObject obj = ApiRequest.getRequest(apiUrl);
			JsonArray schoolInfo = (JsonArray) obj.get("schoolInfo");
			List<GetSchoolRO> schoolList = new ArrayList<>();
			if(schoolInfo != null) {
				JsonObject body = (JsonObject) schoolInfo.get(1);
				JsonArray row = (JsonArray) body.get("row");
				for (int i = 0; i < row.size(); i++) {
					JsonObject jsonSchool = (JsonObject) row.get(i);
					GetSchoolRO school = new GetSchoolRO(jsonSchool);
					schoolList.add(school);
				}
			}
			return schoolList;
		}catch (Exception e){
			try {
				throw e;
			} catch (UnsupportedEncodingException unsupportedEncodingException) {
				unsupportedEncodingException.printStackTrace();
				throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "인코딩 에러");
			}
		}
	}
}
