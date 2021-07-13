package com.hackathon.lib;

import lombok.Data;

@Data
public class Url {
	private String base;

	private boolean isQueryParamExist = false;

	public void  add(String key, String value) {
		if (!isQueryParamExist) {
			base += "?" + key + "=" + value;
			isQueryParamExist = true;

		} else {
			base += "&"+ key+"="+value;

		}
	}
	public Url(String base){
		this.base = base;
	}

}
