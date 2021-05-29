package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseDealResponseDto;
import com.ssafy.happyhouse.model.HouseDto;
import com.ssafy.happyhouse.model.HouseResponseDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;
import com.ssafy.happyhouse.model.dao.HouseDao;

@Service
public class HouseServiceImpl implements HouseService {

private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<SidoGugunDongCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseDao.class).getSido();
	}

	@Override
	public List<SidoGugunDongCodeDto> getGugunInSido(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).getGugunInSido(map);
	}

	@Override
	public List<SidoGugunDongCodeDto> getDongInGugun(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).getDongInGugun(map);
	}
	
	@Override
	public List<HouseResponseDto> searchHouse(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).searchHouse(map);
	}
	
	@Override
	public HouseResponseDto searchHouseDetail(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).searchHouseDetail(map);
	}

	@Override
	public List<HouseDealResponseDto> searchHouseDeal(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).searchHouseDeal(map);
	}

	@Override
	@Transactional
	public int addHouseDeal(HouseDealDto houseDeal) throws Exception {
		int result = 0;
		result += sqlSession.getMapper(HouseDao.class).addHouseDeal(houseDeal);
		return result;
	}
	
	@Override
	@Transactional
	public int addHouse(HouseDto house) throws Exception {
		int result = 0;
		result += sqlSession.getMapper(HouseDao.class).addHouse(house);
		return result;
	}

	@Override
	public int getHouseDealTotalCount(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).getHouseDealTotalCount(map);
	}

	@Override
	public int getHouseTotalCount(Map<String, Object> map) throws Exception {
		return sqlSession.getMapper(HouseDao.class).getHouseTotalCount(map);
	}
}
