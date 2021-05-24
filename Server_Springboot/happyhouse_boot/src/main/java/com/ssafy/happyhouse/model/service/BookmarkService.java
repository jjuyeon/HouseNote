package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.BookmarkDto;
import com.ssafy.happyhouse.model.BookmarkResponseDto;

public interface BookmarkService {
	public int deleteBookmark(Map<String, Object> map) throws Exception;
	public List<BookmarkResponseDto> listBookmark(Map<String, Object> map) throws Exception;
	public int addBookmark(BookmarkDto bookmark) throws Exception;
}
