package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/user/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value = "/user/myinfo", method = RequestMethod.GET)
	public String myinfo() {
		return "user/userinfo";
	}
	
	@RequestMapping(value = "/user/modinfo", method = RequestMethod.GET)
	public String modifyInfo() {
		return "user/modifyinfo";
	}
	
	@RequestMapping(value = "/user/joinsuccess", method = RequestMethod.GET)
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/user/modifysuccess", method = RequestMethod.GET)
	public String modifySuccess() {
		return "user/modifysuccess";
	}
	
	@RequestMapping(value = "/user/deletesuccess", method = RequestMethod.GET)
	public String deleteSuccess() {
		return "user/deletesuccess";
	}
	
	@RequestMapping(value = "/user/interest", method = RequestMethod.GET)
	public String interestInfo() {
		return "user/interestinfo";
	}
	
	@RequestMapping(value = "/user/area/addsuccess", method = RequestMethod.GET)
	public String addSuccess() {
		return "user/area_addsuccess";
	}
	
	@RequestMapping(value = "/user/area/deletesuccess", method = RequestMethod.GET)
	public String deleteAreaSuccess() {
		return "user/area_deletesuccess";
	}
	
	@RequestMapping(value = "/admin/notice", method = RequestMethod.GET)
	public String notice() {
		return "admin/notice";
	}
	
	@RequestMapping(value = "/error/500", method = RequestMethod.GET)
	public String error500() {
		return "error/error500";
	}
	
	@RequestMapping(value = "/error/400", method = RequestMethod.GET)
	public String error400() {
		return "error/error400";
	}
}