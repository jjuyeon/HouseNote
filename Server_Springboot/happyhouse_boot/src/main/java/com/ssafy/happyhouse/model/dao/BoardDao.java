package com.ssafy.happyhouse.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.BoardDto;

public interface BoardDao {
	public int insertBoard(Map<String, Object> map);
	public int modifyBoard(Map<String, Object> map);
	public int deleteBoard(Map<String, Object> map);
	public BoardDto selectBoard(Map<String, Object> map);
	List<BoardDto> listBoard(Map<String, Object> map);
	public int getTotalCount(Map<String, Object> map);
}
