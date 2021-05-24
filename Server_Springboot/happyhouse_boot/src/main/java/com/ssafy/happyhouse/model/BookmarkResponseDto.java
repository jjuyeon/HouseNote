package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "관심지역  출력 DTO", description = "관심 매물의 일련 번호, 유저 번호, 관심지역 아파트 이름, 관심지역 건축년도, 관심지역 주소, 관심지역 생성일을 가진 BookmarkResponse Class")
public class BookmarkResponseDto {
	@ApiModelProperty(value = "매물 일련 번호")
	private String houseIdx;
	@ApiModelProperty(value = "유저 번호")
	private String userIdx;
	@ApiModelProperty(value = "관심지역 아파트 이름")
	private String aptname;
	@ApiModelProperty(value = "관심지역 건축년도")
	private String buildYear;
	@ApiModelProperty(value = "관심지역 주소")
	private String address;
	@ApiModelProperty(value = "관심지역 생성일")
	private String createdAt;
	public String getHouseIdx() {
		return houseIdx;
	}
	public void setHouseIdx(String houseIdx) {
		this.houseIdx = houseIdx;
	}
	public String getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(String userIdx) {
		this.userIdx = userIdx;
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
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
