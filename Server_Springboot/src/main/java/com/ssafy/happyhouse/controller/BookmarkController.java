package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.jwt.JwtService;
import com.ssafy.happyhouse.model.BookmarkDto;
import com.ssafy.happyhouse.model.BookmarkResponseDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.BookmarkService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@CrossOrigin("*")
public class BookmarkController {
	
	@Autowired
	BookmarkService bookmarkService;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/bookmark")
	@ApiOperation(value="BookmarkDto를 받아 관심 지역 등록 서비스")
	public ResponseEntity<Map<String, Object>> createBookmark(@RequestBody BookmarkDto req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			UserDto user = jwtService.getUser(jwt);
			req.setUserIdx(user.getIdx());
			System.out.println(req);
			int insert = bookmarkService.addBookmark(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", insert);
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "관심 지역 등록 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "관심 지역 등록 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/bookmark")
	@ApiOperation(value="관심 지역 조회 서비스", response=BookmarkResponseDto.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> selectBookmark(@RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		Map<String, Object> req = new HashMap<String, Object>();
		try {
			UserDto user = jwtService.getUser(jwt);
			req.put("userIdx",user.getIdx());
			List<BookmarkResponseDto> list = bookmarkService.listBookmark(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", list);
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "관심 지역 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "관심 지역 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resEntity;
	}
	
	@DeleteMapping("/bookmark")
	@ApiOperation(value="userIdx와 매물 일련번호를 받아 관심 지역 삭제 서비스", response=BookmarkDto.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> deleteBookmark(@RequestParam Map<String, Object> req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			UserDto user = jwtService.getUser(jwt);
			req.put("userIdx",user.getIdx());
			int delete = bookmarkService.deleteBookmark(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", delete);
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "관심 지역 삭제 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "관심 지역 삭제 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resEntity;
	}

}
