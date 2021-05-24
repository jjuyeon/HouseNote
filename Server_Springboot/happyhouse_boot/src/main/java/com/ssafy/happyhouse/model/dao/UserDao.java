package com.ssafy.happyhouse.model.dao;

import java.util.Map;

import com.ssafy.happyhouse.model.UserDto;

public interface UserDao {
	public UserDto login(Map<String, Object> map);
	public int join(UserDto user);
	public UserDto getUser(Map<String, Object> map);
	public int modifyUser(Map<String, Object> map);
	public int deleteUser(Map<String, Object> map);
}
