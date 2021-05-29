package com.ssafy.happyhouse.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "시군도 DTO", description = "시도 코드, 시도 이름, 구군 코드, 구군 이름, 동 코드, 동 이름을 가진 SidoGugunDongCode Class")
public class SidoGugunDongCodeDto {
	@ApiModelProperty(value="시도 코드")
	private String sido_code;
	@ApiModelProperty(value="시도 이름")
	private String sido_name;
	@ApiModelProperty(value="구군 코드")
	private String gugun_code;
	@ApiModelProperty(value="구군 이름")
	private String gugun_name;
	@ApiModelProperty(value="동 코드")
	private String dongcode;
	@ApiModelProperty(value="동 이름")
	private String dong;

	public String getSido_code() {
		return sido_code;
	}

	public void setSido_code(String sido_code) {
		this.sido_code = sido_code;
	}

	public String getSido_name() {
		return sido_name;
	}

	public void setSido_name(String sido_name) {
		this.sido_name = sido_name;
	}

	public String getGugun_code() {
		return gugun_code;
	}

	public void setGugun_code(String gugun_code) {
		this.gugun_code = gugun_code;
	}

	public String getGugun_name() {
		return gugun_name;
	}

	public void setGugun_name(String gugun_name) {
		this.gugun_name = gugun_name;
	}

	public String getDongcode() {
		return dongcode;
	}

	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	@Override
	public String toString() {
		return "SidoGugunDongCodeDto [sido_code=" + sido_code + ", sido_name=" + sido_name + ", gugun_code="
				+ gugun_code + ", gugun_name=" + gugun_name + ", dongcode=" + dongcode + ", dong=" + dong + "]";
	}

	
}
