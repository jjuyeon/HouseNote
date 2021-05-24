package com.ssafy.happyhouse.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.happyhouse.jwt.JwtService;
import com.ssafy.happyhouse.model.UserDto;

@Service
public class KakaoAPIService {

	private final String userRequestURL = "https://kapi.kakao.com/v2/user/me";
	private final String addressRequestURL = "https://dapi.kakao.com/v2/local/search/address.json";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	public JsonNode getKaKaoUserInfo(String access_Token) {
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(userRequestURL);

		post.addHeader("Authorization", "Bearer " + access_Token);
		JsonNode returnNode = null;

		HttpResponse response;
		try {
			response = client.execute(post);
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnNode;
	}
	
	@Transactional
	public String redirectToken(JsonNode json) {

		long kakaoId = json.get("id").asLong();
		//String nickname = json.get("kakao_account").get("profile").get("nickname").toString();
		//nickname = nickname.substring(1, nickname.length() - 1);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIdx", String.valueOf(kakaoId));
		UserDto user;
		String jwt = "";
		try {
			user = userService.getUser(map);
			jwt = jwtService.create("user", user, "user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jwt;
	}

	public JsonNode getKakaoAddress(String address) {
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet get = new HttpGet(addressRequestURL+"?query="+address);
		get.addHeader("Authorization", "KakaoAK #{your_kakao_rest_api_key}");

		JsonNode returnNode = null;

		HttpResponse response;
		try {
			response = client.execute(get);
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnNode;
		
	}

}
