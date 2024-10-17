package com.example.demo.openApiTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenAPITest {

	String serviceKey = "cSZuPwiq9JmL9XpsD6tmD7YgCw1Em3c9gfuyENRYAz1nmT6SVPp%2BpOhNWf6O18a2N1qptDoHam%2F9bKrhpmg3pA%3D%3D";
	String dataType = "JSON";
	String code = "11B20201";
	
//	@Test
	public String getWeather() throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst");
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("regId", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8"));
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	@Test
	public void jsonToDto() throws IOException {
		
		// 매퍼 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		
		// 분석할 수 없는 구문을 무시하는 옵션 설정
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// 날씨 데이터 가져오기
		String weather = getWeather();
		
		Root root =null;
		
		// JSON 문자열을 클래스로 변환
		// 원본데이터, 변환할 클래스
		root = mapper.readValue(weather, Root.class);
		
		System.out.println("결과코드: " + root.response.header.resultCode);
		System.out.println("결과메세지: " + root.response.header.resultMsg);
		System.out.println("전체결과수: " + root.response.body.totalCount);
		
	}
}
