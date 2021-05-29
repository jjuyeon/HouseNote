package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "매물 정보 결과 DTO", description = "매물의 일련 번호, 아파트 이름, 건축년도, 주소, 위도, 경도, 관심등록 여부를 가진 HouseResponse Class")
public class HouseResponseDto {
	@ApiModelProperty(value = "매물 일련 번호")
	private String idx;
	@ApiModelProperty(value = "매물 아파트 이름")
	private String aptname;
	@ApiModelProperty(value = "매물 건축년도")
	private String buildYear;
	@ApiModelProperty(value = "매물 주소")
	private String address;
	@ApiModelProperty(value = "매물 위도")
	private String lat;
	@ApiModelProperty(value = "매물 경도")
	private String lng;
	@ApiModelProperty(value = "매물 관심등록 여부")
	private int isBookMark;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getAptname() {
		return aptname;
	}
	public void setAptname(String aptname) {
		this.aptname = aptname;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public int getIsBookMark() {
		return isBookMark;
	}
	public void setIsBookMark(int isBookMark) {
		this.isBookMark = isBookMark;
	}
	
	
}
