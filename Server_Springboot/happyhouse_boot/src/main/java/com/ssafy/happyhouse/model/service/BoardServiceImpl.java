package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.dao.BoardDao;

@Service
public class BoardServiceImpl implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	@Transactional
	public int insertBoard(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BoardDao.class).insertBoard(map);
	}

	@Override
	@Transactional
	public int modifyBoard(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BoardDao.class).modifyBoard(map);
	}

	@Override
	@Transactional
	public int deleteBoard(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BoardDao.class).deleteBoard(map);
	}

	@Override
	public BoardDto selectBoard(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BoardDao.class).selectBoard(map);
	}

	@Override
	public List<BoardDto> listBoard(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BoardDao.class).listBoard(map);
	}

	@Override
	public int getTotalCount(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(BoardDao.class).getTotalCount(map);
	}

}
