package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "유저 DTO", description = "유저 번호, 유저 이름, 유저 이메일, 유저 이미지, 유저 생성일, 유저 수정일을 가진 User Class")
public class UserDto {
	@ApiModelProperty(value="유저 번호")
	private String idx;
	@ApiModelProperty(value="유저 이름")
	private String name;
	@ApiModelProperty(value="유저 이메일")
	private String email;
	@ApiModelProperty(value="유저 이미지")
	private String image;
	@ApiModelProperty(value="유저 생성일")
	private String createdAt;
	@ApiModelProperty(value="유저 수정일")
	private String updatedAt;
	
	public String getIdx() {
		return idx;
	}
	
	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
		return "UserDto [idx=" + idx + ", name=" + name + ", email=" + email + ", image=" + image + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}

