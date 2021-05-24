package com.ssafy.happyhouse.model;

public class UserDto {

	private String idx;
	private String name;
	private String email;
	private String createdAt;
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
		return "UserDto [idx=" + idx + ", name=" + name + ", email=" + email + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
}

