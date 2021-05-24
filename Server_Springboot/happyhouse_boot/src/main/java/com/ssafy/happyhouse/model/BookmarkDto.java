package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "관심지역 DTO", description = "관심 매물의 일련 번호, 유저 번호, 관심지역 생성일을 가진 Bookmark Class")
public class BookmarkDto {

	@ApiModelProperty(value = "매물 일련 번호")
	private String houseIdx;
	@ApiModelProperty(value = "유저 번호")
	private String userIdx;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "BookmarkDto [houseIdx=" + houseIdx + ", userIdx=" + userIdx + ", createdAt=" + createdAt + "]";
	}
	
	
}
