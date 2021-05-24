package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.BoardDto;

public interface BoardService {
	public int insertBoard(Map<String, Object> map) throws Exception;
	public int modifyBoard(Map<String, Object> map) throws Exception;
	public int deleteBoard(Map<String, Object> map) throws Exception;
	public BoardDto selectBoard(Map<String, Object> map) throws Exception;
	List<BoardDto> listBoard(Map<String, Object> map) throws Exception;
	public int getTotalCount(Map<String, Object> map) throws Exception;
}
