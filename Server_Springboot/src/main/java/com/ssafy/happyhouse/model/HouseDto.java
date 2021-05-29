package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "매물 정보 DTO", description = "매물의 일련 번호, 아파트 이름, 건축년도, 동, 동코드, 주소, 위도, 경도를 가진 House Class")
public class HouseDto {
	@ApiModelProperty(value = "매물 일련 번호")
	private String idx;
	@ApiModelProperty(value = "매물 아파트 이름")
	private String aptname;
	@ApiModelProperty(value = "매물 건축년도")
	private String buildYear;
	@ApiModelProperty(value = "매물 동")
	private String dong;
	@ApiModelProperty(value = "매물 동 코드")
	private String code;
	@ApiModelProperty(value = "매물 주소")
	private String address;
	@ApiModelProperty(value = "매물 위도")
	private String lat;
	@ApiModelProperty(value = "매물 경도")
	private String lng;
	
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
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	@Override
	public String toString() {
		return "HouseDto [idx=" + idx + ", aptname=" + aptname + ", buildYear=" + buildYear + ", dong=" + dong
				+ ", code=" + code + ", address=" + address + ", lat=" + lat + ", lng=" + lng + "]";
	}
	
	
}
