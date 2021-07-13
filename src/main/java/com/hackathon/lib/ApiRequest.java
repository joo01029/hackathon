package com.hackathon.lib;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiRequest{
    public static JsonObject getRequest(Url apiUrl){
    	try {
		    URL url = new URL(apiUrl.getBase());
		    HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    con.setRequestMethod("GET");
		    StringBuffer stringBuffer = new StringBuffer();

		    if( con.getResponseCode() == HttpURLConnection.HTTP_OK){
		    	BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

		    	String line;
		    	while((line = br.readLine()) != null){
				    stringBuffer.append(line).append("\n");
			    }
		    	br.close();
		    }
		    JsonParser jsonParser = new JsonParser();
		    return (JsonObject) jsonParser.parse(stringBuffer.toString());
	    }catch (Exception e){
    		e.printStackTrace();
    		throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "잘못된 요청");
	    }
	}
}
