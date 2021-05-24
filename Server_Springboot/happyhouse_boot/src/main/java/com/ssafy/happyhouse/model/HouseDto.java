package com.ssafy.happyhouse.model;

public class HouseDto {
	private String idx;
	private String aptname;
	private String buildYear;
	private String dong;
	private String code;
	private String address;
	private String lat;
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
