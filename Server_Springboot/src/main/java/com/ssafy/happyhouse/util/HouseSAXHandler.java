package com.ssafy.happyhouse.util;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.ssafy.happyhouse.model.HouseDealApiResponseDto;

/**
 *  AptDealHistory.xml 파일에서 아파트 거래 정보를 읽어 파싱하는 핸들러 클래스 
 */
public class HouseSAXHandler extends DefaultHandler {
	/**
	 * 아파트 거래 정보를 식별하기 위한 번호로 차후 DB에서는 primary key로 대체하지만 
	 * 현재 버전에서는 0번부터 순차 부여한다. 
	 */
	private int no;
	/**아파트 거래 정보를 담는다 */
	private Map<Integer,HouseDealApiResponseDto> houses;
	/**파상힌 아파트 거래 정보*/
	private HouseDealApiResponseDto house;
	/**태그 바디 정보를 임시로 저장*/
	private String temp;
	public HouseSAXHandler(){
		houses = new HashMap<Integer,HouseDealApiResponseDto>();
	}
	public void startElement(String uri, String localName
			                           , String qName, Attributes att ){
		if(qName.equals("item")){
			house = new HouseDealApiResponseDto();
		}
	}
	public void endElement(String uri, String localName, String qName){
		if(qName.equals("거래금액")) { 
			house.setDealAmount(temp.trim());
		}else if(qName.equals("건축년도")) { 
			house.setBuildYear(temp);
		}else if(qName.equals("년")) { 
			house.setDealYear(temp);
		}else if(qName.equals("법정동")) { 
			house.setDong(temp.substring(1,temp.length()));
		}else if(qName.equals("아파트")) { 
			house.setAptname(temp);
		}else if(qName.equals("월")) { 
			house.setDealMonth(temp);
		}else if(qName.equals("일")) { 
			house.setDealDay(temp);
		}else if(qName.equals("일련번호")) { 
			house.setHouseIdx(temp);
		}else if(qName.equals("전용면적")) { 
			house.setArea(temp);
		}else if(qName.equals("지번")) { 
			house.setJibun(temp);
		}else if(qName.equals("지역코드")) { 
			house.setCode(temp);
		}else if(qName.equals("item")) {
			houses.put(no++, house);
		}
	}
	public void characters(char[]ch, int start, int length){
		temp = new String(ch, start, length);
	}
	public Map<Integer,HouseDealApiResponseDto> getHouseDealResponse() {
		return houses;
	}
}





