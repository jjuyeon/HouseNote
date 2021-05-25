package com.ssafy.happyhouse.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.happyhouse.jwt.JwtService;
import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseDealResponseDto;
import com.ssafy.happyhouse.model.HouseDealApiResponseDto;
import com.ssafy.happyhouse.model.HouseDto;
import com.ssafy.happyhouse.model.HouseResponseDto;
import com.ssafy.happyhouse.model.SidoGugunDongCodeDto;
import com.ssafy.happyhouse.model.UserDto;
import com.ssafy.happyhouse.model.service.HouseService;
import com.ssafy.happyhouse.model.service.KakaoAPIService;
import com.ssafy.happyhouse.util.HouseSAXHandler;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@CrossOrigin("*")
public class HouseController {
	
	@Autowired
	HouseService houseService;
	
	@Autowired
	KakaoAPIService kakaoAPIService;
	
	@Autowired
	JwtService jwtService;

	@GetMapping("/house")
	@ApiOperation(value="집 정보 조회 서비스")
	public ResponseEntity<Map<String, Object>> searchHouse(@RequestParam Map<String, Object> req, @RequestHeader(value="Authorization", required = false) String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!jwt.equals("null")) {
				UserDto user = jwtService.getUser(jwt);
				req.put("userIdx", user.getIdx());
			} else {
				req.put("userIdx", null);
			}
			int totalCount = houseService.getHouseTotalCount(req);
			req.put("start", ((Integer.parseInt(req.get("pg").toString())-1) * 5));
			if (totalCount != 0) {
				PageNavigation pageNavigation = new PageNavigation(Integer.parseInt(req.get("pg").toString()),totalCount);
				map.put("pageNavigation", pageNavigation);
			}
			List<HouseResponseDto> list = houseService.searchHouse(req);
			map.put("result", list);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "아파트 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "아파트 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/house/{houseIdx}")
	@ApiOperation(value="집 정보 상세 조회 서비스")
	public ResponseEntity<Map<String, Object>> searchHouseDeal(@RequestParam Map<String, Object> req, @PathVariable("houseIdx") String houseIdx, @RequestHeader(value="Authorization", required = false) String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		req.put("houseIdx", houseIdx);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!jwt.equals("null")) {
				UserDto user = jwtService.getUser(jwt);
				req.put("userIdx", user.getIdx());
			} else {
				req.put("userIdx", null);
			}
			HouseResponseDto houseDto = houseService.searchHouseDetail(req);
			map.put("result", houseDto);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "집 정보 상세 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "집 정보 상세 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/housedeal")
	@ApiOperation(value="집 거래 정보 조회 서비스")
	public ResponseEntity<Map<String, Object>> searchHouseDeal(@RequestParam Map<String, Object> req, @RequestHeader(value="Authorization", required = false) String jwt) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!jwt.equals("null")) {
				UserDto user = jwtService.getUser(jwt);
				req.put("userIdx", user.getIdx());
			} else {
				req.put("userIdx", null);
			}
			int totalCount = houseService.getHouseDealTotalCount(req);
			req.put("start", ((Integer.parseInt(req.get("pg").toString())-1) * 5));
			if (totalCount != 0) {
				PageNavigation pageNavigation = new PageNavigation(Integer.parseInt(req.get("pg").toString()),totalCount);
				map.put("pageNavigation", pageNavigation);
			}
			List<HouseDealResponseDto> list = houseService.searchHouseDeal(req);
			map.put("result", list);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "집 거래 정보 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "집 거래 정보 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/housedeal/openapi")
	@ApiOperation(value="거래 정보 저장 서비스")
	public ResponseEntity<Map<String, Object>> addHouseInfo(@RequestParam Map<String, Object> req) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<SidoGugunDongCodeDto> sido = houseService.getSido();
			
			for (int i = 0; i < sido.size(); i++) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("sido", "50");
				List<SidoGugunDongCodeDto> gugun = houseService.getGugunInSido(tmp);
				for (int j = 0; j < gugun.size(); j++) {
					String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=#{your_API_key}&numOfRows=50&LAWD_CD="+gugun.get(j).getGugun_code()+"&DEAL_YMD=201512";
			        Map<Integer,HouseDealApiResponseDto> houseDealResponse;
			        SAXParserFactory factory = SAXParserFactory.newInstance();
			        SAXParser parser = factory.newSAXParser();
			        HouseSAXHandler houseHandler = new HouseSAXHandler();
					parser.parse(new URL(url).openConnection().getInputStream(), houseHandler);
					houseDealResponse = houseHandler.getHouseDealResponse();
			        
			        for (Integer key : houseDealResponse.keySet()) {
			        	HouseDto house = new HouseDto();
			        	HouseDealDto houseDeal = new HouseDealDto();
			        	HouseDealApiResponseDto response = houseDealResponse.get(key);
			        	house.setAptname(response.getAptname());
			        	house.setIdx(response.getHouseIdx());
			        	house.setBuildYear(response.getBuildYear());
			        	house.setCode(response.getCode());
			        	house.setDong(response.getDong());
			        	JsonNode json = kakaoAPIService.getKakaoAddress(response.getDong() + response.getJibun());
			    		String address = json.get("documents").get(0).get("address").get("address_name").toString();
			    		String lat = json.get("documents").get(0).get("address").get("y").toString();
			    		String lng = json.get("documents").get(0).get("address").get("x").toString();
			        	house.setAddress(address.substring(1, address.length() - 1));
			        	house.setLat(lat.substring(1, lat.length() - 1));
			        	house.setLng(lng.substring(1, lng.length() - 1));
			        	houseDeal.setHouseIdx(response.getHouseIdx());
			        	houseDeal.setDealAmount(response.getDealAmount());
			        	houseDeal.setDealYear(response.getDealYear());
			        	houseDeal.setDealMonth(response.getDealMonth());
			        	houseDeal.setDealDay(response.getDealDay());
			        	houseDeal.setArea(response.getArea());
			        	houseService.addHouse(house);
			        	houseService.addHouseDeal(houseDeal);

			        }
				}
			}

	         map.put("isSuccess", true);
	         map.put("code", 200);
	         map.put("message", "아파트 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//			}
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "아파트 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/sido")
	@ApiOperation(value="시도 조회 서비스")
	public ResponseEntity<Map<String, Object>> selectSido() throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			List<SidoGugunDongCodeDto> list = houseService.getSido();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", list);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "시도 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "시도 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/gugun")
	@ApiOperation(value="구군 조회 서비스")
	public ResponseEntity<Map<String, Object>> selectGugun(@RequestParam Map<String, Object> req) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			List<SidoGugunDongCodeDto> list = houseService.getGugunInSido(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", list);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "구군 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "시도 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}
	
	@GetMapping("/dong")
	@ApiOperation(value="동 조회 서비스")
	public ResponseEntity<Map<String, Object>> selectDong(@RequestParam Map<String, Object> req) throws Exception {
		ResponseEntity<Map<String, Object>> resEntity = null;
		try {
			List<SidoGugunDongCodeDto> list = houseService.getDongInGugun(req);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", list);
			map.put("isSuccess", true);
	        map.put("code", 200);
	        map.put("message", "동 조회 성공");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} catch(RuntimeException e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", false);
	        map.put("code", 500);
	        map.put("message", "동 조회 실패");
			resEntity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resEntity;
	}

}
