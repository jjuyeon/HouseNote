package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.jwt.JwtService;
import com.ssafy.happyhouse.model.NoteDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.NoteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@CrossOrigin("*")
public class NoteController {
	
	@Autowired
	NoteService noteService;
	
	@Autowired
	JwtService jwtService;
	
	// 조회
	@GetMapping("/note")
	@ApiOperation(value="userIdx(사용자번호), houseIdx(일련 번호)를 받아 메모 내용 조회 서비스", response=NoteDto.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> selectNote(@RequestParam Map<String, Object> req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			UserDto user = jwtService.getUser(jwt);
			req.put("userIdx", user.getIdx());
			NoteDto note = noteService.getNote(req);			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", note);
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "메모 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "메모 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resEntity;
	}
	
	// 업데이트
	@PatchMapping("/note/{noteIdx}")
	@ApiOperation(value="NoteDto(+RoomDto)를 받아 메모 업데이트 서비스", response=NoteDto.class)
	public @ResponseBody ResponseEntity<Map<String, Object>> updateNote(@PathVariable("noteIdx") int noteIdx, @RequestBody NoteDto req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			UserDto user = jwtService.getUser(jwt);
			req.setUserIdx(user.getIdx());
			req.setIdx(noteIdx);
			int note = noteService.updateNote(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", note);
			map.put("isSuccess", true);
			map.put("code", 200);
			map.put("message", "메모 업데이트 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch (RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
			map.put("code", 500);
			map.put("message", "메모 업데이트 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resEntity;
	}
}
