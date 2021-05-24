package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseDto;
import com.ssafy.happyhouse.model.HouseResponseDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;

public interface HouseDao {
	List<SidoGugunDongCodeDto> getSido() throws SQLException;
	List<SidoGugunDongCodeDto> getGugunInSido(Map<String, Object> map) throws SQLException;
	List<SidoGugunDongCodeDto> getDongInGugun(Map<String, Object> map) throws SQLException;
	
	public List<HouseResponseDto> searchHouse(Map<String, Object> map) throws SQLException;
	public List<HouseDealDto> searchHouseDeal(Map<String, Object> map) throws SQLException;
	public int addHouseDeal(HouseDealDto houseDeal) throws SQLException;
	public int addHouse(HouseDto house) throws SQLException;
	public int getHouseDealTotalCount(Map<String, Object> map) throws SQLException;
	public int getHouseTotalCount(Map<String, Object> map) throws SQLException;
}
