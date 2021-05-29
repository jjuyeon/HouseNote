package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseDealResponseDto;
import com.ssafy.happyhouse.model.HouseDto;
import com.ssafy.happyhouse.model.HouseResponseDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;

public interface HouseService {
	List<SidoGugunDongCodeDto> getSido() throws Exception;
	List<SidoGugunDongCodeDto> getGugunInSido(Map<String, Object> map) throws Exception;
	List<SidoGugunDongCodeDto> getDongInGugun(Map<String, Object> map) throws Exception;

	List<HouseResponseDto> searchHouse(Map<String, Object> map) throws Exception;
	HouseResponseDto searchHouseDetail(Map<String, Object> map) throws Exception;
	List<HouseDealResponseDto> searchHouseDeal(Map<String, Object> map) throws Exception;
	public int addHouseDeal(HouseDealDto houseDeal) throws Exception;
	public int addHouse(HouseDto house) throws Exception;
	public int getHouseDealTotalCount(Map<String, Object> map) throws Exception;
	public int getHouseTotalCount(Map<String, Object> map) throws Exception;
}
