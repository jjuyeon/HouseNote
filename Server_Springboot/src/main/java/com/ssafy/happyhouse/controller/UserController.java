package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.happyhouse.jwt.JwtService;
import com.ssafy.happyhouse.model.NoteDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.KakaoAPIService;
import com.ssafy.happyhouse.model.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/api")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	KakaoAPIService kakaoAPIService;
	
	@Autowired
	UserService userService;
	
	@ApiOperation(value="userIdx(유저의 번호)를 받아 user 조회 서비스")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "userIdx", value = "유저 번호", required = true, dataType = "string", paramType = "path")
    })
	@ApiResponses({ 
        @ApiResponse(response = UserDto.class, code = 200, message = "유저 조회 성공")
    })
	@GetMapping("/user/{userIdx}")
	public @ResponseBody ResponseEntity<Map<String, Object>> viewUser(@PathVariable("userIdx") String userIdx) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		UserDto user = null;
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("userIdx", userIdx);
		try {
			user = userService.getUser(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", user);
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "유저 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "유저 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resEntity;
	}
	
	// TODO 카카오 연동
	@PostMapping("/user/kakaologin")
	@ApiOperation("카카오 로그인")
	public ResponseEntity<Map<String, Object>> kakaoLogin(@RequestBody Map<String, Object> req) {
		ResponseEntity<Map<String, Object>> resEntity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> res = new HashMap<String, Object>();
		JsonNode json = kakaoAPIService.getKaKaoUserInfo(req.get("access_token").toString());
		long kakaoId = json.get("id").asLong();
		String name = json.get("kakao_account").get("profile").get("nickname").toString();
		name = name.substring(1, name.length() - 1);
		String picture = null;
		if (json.get("kakao_account").get("profile").has("profile_image_url")) {
			picture = json.get("kakao_account").get("profile").get("profile_image_url").toString();
			picture = picture.substring(1, picture.length() - 1);
			String temp = picture.substring(0, 4);
			String temp2 = picture.substring(4, picture.length());
			picture = temp + "s" + temp2; // https 작업
		}
		String email = null;
		if(json.get("kakao_account").has("email")) {
			email = json.get("kakao_account").get("email").toString();
			email = email.substring(1, email.length() - 1);
		}
		//String 
		map.put("userIdx", String.valueOf(kakaoId));
		try {
			if(userService.getUser(map) == null) { //최초 로그인시 유저를 가입시켜줌
				UserDto user = new UserDto();
				user.setIdx(String.valueOf(kakaoId));
				user.setEmail(email);
				user.setName(name);
				user.setImage(picture);
				userService.join(user);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String result = null;

		try {
			result = kakaoAPIService.redirectToken(json); // 토큰 발행
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		res.put("jwt", result);
		res.put("isSuccess", true);
		res.put("code", 200);
		res.put("message", "로그인 성공");
		resEntity = new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		return resEntity;
	}

	@GetMapping("/user/logintoken")
	@ApiOperation(value="토큰 검증", response=UserDto.class)
	public Object checkToken(@RequestParam String access_token) {

		Object result = null;

		if (jwtService.isUsable(access_token)) {
			result = jwtService.get("user");
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/user")
	@ApiOperation(value="userIdx을 받아 user 삭제 서비스")
	public ResponseEntity<Map<String, Object>> deleteUser(@RequestParam Map<String, Object> req) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		System.out.println(req.get("userIdx"));
		try {
			int delete = userService.deleteUser(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "유저 삭제 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "유저 삭제 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resEntity;
	}
}
