package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "방 상태 DTO", description = "방 종류, 이름, 항목, 항목별 점수, 메모 생성일을 가진 Room Class")
public class RoomDto {
	@ApiModelProperty(value="방 번호")
	private int idx;
	@ApiModelProperty(value="메모 번호")
	private int noteIdx;
	@ApiModelProperty(value="방 종류(1=방, 2=욕실, 3=주방, 4=시설")
	private int status;
	@ApiModelProperty(value="방 이름")
	private String name;
	@ApiModelProperty(value="방 항목")
	private String item;
	@ApiModelProperty(value="방 항목 점수")
	private String score;
	@ApiModelProperty(value="방 정보 생성일")
	private String createdAt;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getNoteIdx() {
		return noteIdx;
	}
	public void setNoteIdx(int noteIdx) {
		this.noteIdx = noteIdx;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	@Override
	public String toString() {
		return "RoomDto [idx=" + idx + ", noteIdx=" + noteIdx + ", status=" + status + ", name=" + name + ", item="
				+ item + ", score=" + score + ", createdAt=" + createdAt + "]";
	}
}
