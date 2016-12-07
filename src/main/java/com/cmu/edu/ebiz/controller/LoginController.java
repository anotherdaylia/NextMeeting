package com.cmu.edu.ebiz.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmu.edu.ebiz.pojo.Group;
import com.cmu.edu.ebiz.pojo.Rule;
import com.cmu.edu.ebiz.pojo.User;
import com.cmu.edu.ebiz.service.GroupService;
import com.cmu.edu.ebiz.service.MeetingService;
import com.cmu.edu.ebiz.service.RuleService;
import com.cmu.edu.ebiz.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private MeetingService meetingService;

	@Autowired
	private RuleService ruleService;
	
	public static final String USER_NAME = "NAME";
	public static final String USER = "USER";
	public static final String USER_TYPE = "USERTYPE";

	/**
	 * Show login page actions
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpSession session) {
		String andrewID = (String) session.getAttribute(USER_NAME);
		if (andrewID == null) {
			return "login";
		}
		return "auth";
	}
	
	/**
	 * Actually login method
	 * @param map
	 * @param session
	 * @param andrewID
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Map<String, Object> map, HttpSession session, @RequestParam("andrewID") String andrewID, @RequestParam("password") String password) {
		if (andrewID != null && password != null) {
			andrewID.trim();
			password.trim();
			User loginUser = null;
			loginUser = userService.getUserByAndrewID(andrewID);
			if (loginUser != null && loginUser.getPassword().equals(AuthorizationFilter.MD5(password))) {
				session.setAttribute(LoginController.USER_TYPE, loginUser.getType());
				session.setAttribute(LoginController.USER, loginUser);
				session.setAttribute(LoginController.USER_NAME, loginUser.getFirstname()
						+ " " + loginUser.getLastname());
				meetingService.deleteUnCheckin(ruleService.getValueByRuleId(Rule.CHECKIN));
				if (loginUser.getType() == User.USER_ADMIN) {
					return "redirect:/admin/welcome";
				} else {
					return "redirect:/student/";
				}
			}
		}
		map.put("errorMessage", "Password or User Name is incorrect.");
		map.put("andrewID", andrewID);
		return "login";
	}
	
	/**
	 * Logout method
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index";
	}
	
	/**
	 * URL when error happened
	 * @return
	 */
	@RequestMapping("/error")
	public String error() {
		return "error";
	}
	
	/**
	 * URL when no authority access happened
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/noauth")
	public String noauth(HttpSession session, Map<String, Object> map) {
		User u = (User) session.getAttribute("USER");
		String redirectUrl;
		if (u.getType() == User.USER_ADMIN) {
			redirectUrl = AuthorizationFilter.ADMIN_INDEX_PAGE;
		} else {
			redirectUrl = AuthorizationFilter.STUDENT_INDEX_PAGE;
		}
		map.put("redirectUrl", redirectUrl);
		return "noauth";
	}
}
