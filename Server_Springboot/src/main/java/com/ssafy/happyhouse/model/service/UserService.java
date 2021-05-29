package com.ssafy.happyhouse.model.service;

import java.util.Map;

import com.ssafy.happyhouse.model.UserDto;

public interface UserService {
	public UserDto login(Map<String, Object> map) throws Exception;
	public int join(UserDto user) throws Exception;

	public UserDto getUser(Map<String, Object> map) throws Exception;
	public int modifyUser(Map<String, Object> map) throws Exception;
	public int deleteUser(Map<String, Object> map) throws Exception;
}
