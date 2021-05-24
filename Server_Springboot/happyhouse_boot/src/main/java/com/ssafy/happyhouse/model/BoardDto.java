package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "게시판 DTO", description = "작성자 번호, 게시물 제목, 내용, 생성일을 가진 Board Class")
public class BoardDto {
	@ApiModelProperty(value="게시물 번호")
	private int idx;
	@ApiModelProperty(value="작성자 번호")
	private String userIdx;
	@ApiModelProperty(value="게시물 제목")
	private String title;
	@ApiModelProperty(value="게시물 내용")
	private String content;
	@ApiModelProperty(value="게시물 생성일")
	private String createdAt;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(String userIdx) {
		this.userIdx = userIdx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
}
