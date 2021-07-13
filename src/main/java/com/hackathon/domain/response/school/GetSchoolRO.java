package com.hackathon.domain.response.school;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class GetSchoolRO {
	private String code;
	private String name;
	private String address;

	public GetSchoolRO(JsonObject jsonObject) {
		this.code = jsonObject.get("ATPT_OFCDC_SC_CODE").toString().replace("\"", "") + jsonObject.get("SD_SCHUL_CODE").toString().replace("\"", "");

		this.name = jsonObject.get("SCHUL_NM").toString().replace("\"", "");

		this.address = jsonObject.get("ORG_RDNMA").toString().replace("\"", "");
	}

	public GetSchoolRO(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
