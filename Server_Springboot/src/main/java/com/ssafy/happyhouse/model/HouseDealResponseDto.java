package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "매물 거래 정보 결과 DTO", description = "거래된 매물의 일련 번호, 가격, 년도, 월, 일, 전용면적을 가진 HouseDealResponse Class")
public class HouseDealResponseDto {
	@ApiModelProperty(value = "매물 거래 일련 번호")
	private int idx;
	@ApiModelProperty(value = "매물 가격")
	private String dealAmount;
	@ApiModelProperty(value = "매물 판매 년도")
	private String dealYear;
	@ApiModelProperty(value = "매물 판매 월")
	private String dealMonth;
	@ApiModelProperty(value = "매물 판매 일")
	private String dealDay;
	@ApiModelProperty(value = "매물 전용면적")
	private String area;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getDealYear() {
		return dealYear;
	}
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
	public String getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}
	public String getDealDay() {
		return dealDay;
	}
	public void setDealDay(String dealDay) {
		this.dealDay = dealDay;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
