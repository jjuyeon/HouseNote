package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BookmarkDto;
import com.ssafy.happyhouse.model.BookmarkResponseDto;
import com.ssafy.happyhouse.model.NoteDto;
import com.ssafy.happyhouse.model.dao.BookmarkDao;
import com.ssafy.happyhouse.model.dao.NoteDao;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	@Transactional
	public int deleteBookmark(Map<String, Object> map) throws Exception {
		int result = 0;
		NoteDto note = sqlSession.getMapper(NoteDao.class).getNote(map);
		// bookmark 정보 삭제
		result += sqlSession.getMapper(BookmarkDao.class).deleteBookmark(map);
		System.out.println("bookmark 삭제 "+result);
		return result;
	}

	@Override
	public List<BookmarkResponseDto> listBookmark(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BookmarkDao.class).listBookmark(map);
	}

	@Override
	@Transactional
	public int addBookmark(BookmarkDto book) throws Exception {
		int result = 0;
		result += sqlSession.getMapper(BookmarkDao.class).addBookmark(book); // 관심지역 생성
		System.out.println("들어옴");
		NoteDto note = new NoteDto();
		note.setUserIdx(book.getUserIdx());
		note.setHouseIdx(book.getHouseIdx());
		System.out.println(note);
		result += sqlSession.getMapper(NoteDao.class).addNote(note); // 메모장 생성
		return result;
	}
}
