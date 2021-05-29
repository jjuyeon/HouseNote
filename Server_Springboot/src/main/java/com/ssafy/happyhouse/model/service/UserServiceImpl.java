package com.ssafy.happyhouse.model.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public UserDto login(Map<String, Object> map) throws Exception {
		if(map.get("userid") == null || map.get("userpwd") == null)
			return null;
		return sqlSession.getMapper(UserDao.class).login(map);
	}
	
	@Override
	@Transactional
	public int join(UserDto user) throws Exception {
		return sqlSession.getMapper(UserDao.class).join(user);
	}

	@Override
	@Transactional
	public int deleteUser(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(UserDao.class).deleteUser(map);
		
	}

	@Override
	public UserDto getUser(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(UserDao.class).getUser(map);
	}

	@Override
	@Transactional
	public int modifyUser(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(UserDao.class).modifyUser(map);
	}
}
