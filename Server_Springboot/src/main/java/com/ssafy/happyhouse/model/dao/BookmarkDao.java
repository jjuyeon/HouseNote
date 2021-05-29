package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.BookmarkDto;
import com.ssafy.happyhouse.model.BookmarkResponseDto;

public interface BookmarkDao {
	public int deleteBookmark(Map<String, Object> map) throws SQLException;
	List<BookmarkResponseDto> listBookmark(Map<String, Object> map) throws SQLException;
	public int addBookmark(BookmarkDto bookmark) throws SQLException;
}
