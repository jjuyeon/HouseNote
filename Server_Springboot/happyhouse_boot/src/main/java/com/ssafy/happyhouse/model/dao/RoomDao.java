package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.RoomDto;

public interface RoomDao {
	public int deleteRoom(int idx) throws SQLException;
	List<RoomDto> listRoom(int noteIdx) throws SQLException;
	public int addRoom(RoomDto room) throws SQLException;
	public int updateRoom(RoomDto room) throws SQLException;
}
