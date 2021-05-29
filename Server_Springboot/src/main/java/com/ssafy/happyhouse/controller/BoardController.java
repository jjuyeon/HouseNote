package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import com.ssafy.happyhouse.jwt.JwtService;
import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.BoardService;
import com.ssafy.happyhouse.util.PageNavigation;

@RestController
@RequestMapping(value="/api")
@CrossOrigin("*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;
	
	@Autowired
	JwtService jwtService;
	
	@GetMapping("/board/{boardIdx}")
	@ApiOperation(value="게시판 상세 조회 서비스")
	public ResponseEntity<Map<String, Object>> selectBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("boardIdx", boardIdx);
		try {
			BoardDto board = boardService.selectBoard(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", board);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "게시글 상세 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "게시글 상세 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/board")
	@ApiOperation(value="게시판 조회 서비스")
	public ResponseEntity<Map<String, Object>> listBoard(@RequestParam Map<String, Object> req) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			int totalCount = boardService.getTotalCount(req);
			req.put("start", ((Integer.parseInt(req.get("pg").toString())-1) * 10));
			List<BoardDto> board = boardService.listBoard(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", board);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "게시글 조회 성공");
			if (totalCount != 0) {
				PageNavigation pageNavigation = new PageNavigation(Integer.parseInt(req.get("pg").toString()),totalCount);
				map.put("pageNavigation", pageNavigation);
			}
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
			map.put("message", "게시글 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@PostMapping("/board")
	@ApiOperation(value="게시글 생성 서비스")
	public ResponseEntity<Map<String, Object>> insertBoard(@RequestBody Map<String, Object> req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			UserDto user = jwtService.getUser(jwt);
			req.put("userIdx", user.getIdx());
			int insert = boardService.insertBoard(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "게시글 생성 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
			map.put("message", "게시글 생성 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@PutMapping("/board/{boardIdx}")
	@ApiOperation(value="게시글 수정 서비스")
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable("boardIdx") int boardIdx, @RequestBody Map<String, Object> req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		req.put("boardIdx", boardIdx);
		try {
			UserDto user = jwtService.getUser(jwt);
			req.put("userIdx", user.getIdx());
			int update = boardService.modifyBoard(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "게시글 수정 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
			map.put("message", "게시글 수정 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@DeleteMapping("/board/{boardIdx}")
	@ApiOperation(value="게시글 삭제 서비스")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("boardIdx") int boardIdx, @RequestParam Map<String, Object> req, @RequestHeader(value="Authorization") String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		req.put("boardIdx", boardIdx);
		try {
			UserDto user = jwtService.getUser(jwt);
			req.put("userIdx", user.getIdx());
			int delete = boardService.deleteBoard(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "게시글 삭제 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
			map.put("message", "게시글 삭제 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}

	
}
