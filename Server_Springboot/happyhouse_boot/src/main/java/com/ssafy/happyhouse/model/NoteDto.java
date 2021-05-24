package com.ssafy.happyhouse.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "메모 DTO", description = "매물 일련 번호, 유저 번호, 메모 내용, 메모 생성일, 수정일을 가진 Note Class")
public class NoteDto {
	@ApiModelProperty(value="메모 번호")
	private int idx;
	@ApiModelProperty(value="매물 일련 번호")
	private String houseIdx;
	@ApiModelProperty(value="유저 번호")
	private String userIdx;
	@ApiModelProperty(value="메모 내용")
	private String content;
	@ApiModelProperty(value="방상태 메모 내용")
	private List<RoomDto> roomList;
	@ApiModelProperty(value="메모 생성일")
	private String createdAt;
	@ApiModelProperty(value="메모 최신 수정일")
	private String updatedAt;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<RoomDto> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RoomDto> roomList) {
		this.roomList = roomList;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "NoteDto [idx=" + idx + ", houseIdx=" + houseIdx + ", userIdx=" + userIdx + ", content=" + content
				+ ", roomList=" + roomList + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
