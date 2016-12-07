package com.cmu.edu.ebiz.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmu.edu.ebiz.pojo.Group;
import com.cmu.edu.ebiz.pojo.Rule;
import com.cmu.edu.ebiz.pojo.User;
import com.cmu.edu.ebiz.service.MeetingService;
import com.cmu.edu.ebiz.service.RuleService;
import com.cmu.edu.ebiz.service.UserService;
import com.cmu.edu.ebiz.service.GroupService;

@Controller
public class StudentController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private RuleService ruleService;

	@RequestMapping("/student/profile")
	public String showProfile() {
		// read all booking and events here
		return "student-account";
	}

	@RequestMapping("/student/teams")
	public String showTeams(HttpSession session, Map<String, Object> map) {
		// read all booking and events here
		map.put("groupList", groupService.getActiveGroupList());
		User user = (User) session.getAttribute(LoginController.USER);
		User nUser = userService.getUserByAndrewID(user.getAndrewId());
		if (nUser.getGroup() == null)
			map.put("currentGroup", -1);
		else
			map.put("currentGroup", nUser.getGroup().getId());

		return "student-group";
	}

	@RequestMapping("/student/teams/join/{groupId}")
	public String joinTeams(HttpSession session,
			@PathVariable("groupId") int groupId) {
		Group group = groupService.getGroupById(groupId);
		User user = (User) session.getAttribute(LoginController.USER);
		userService.joinGroup(group, user.getId());
		return "redirect:/student/teams";
	}

	@RequestMapping("/student/teams/quit")
	public String joinTeams(HttpSession session) {
		User user = (User) session.getAttribute(LoginController.USER);
		meetingService.deleteAllThisUserFromNow(user.getAndrewId());
		userService.quitGroup(user.getId());
		return "redirect:/student/teams";
	}

	@RequestMapping("/student/rules")
	public String showRules(Map<String, Object> map) {
		List<Rule> ruleList = ruleService.getList();
		if (ruleList.size() != 0) {

			// The JSTL tag can not recognize dash ("-") therefore manually take dash
			// off.
			map.put("CHECKIN", ruleList.get(0).getNumber());

			for (int i = 1; i < ruleList.size(); i++) {
				map.put(ruleList.get(i).getName(), ruleList.get(i).getNumber());
			}
		}
		return "rules";
	}

	@RequestMapping("/student/profile/change/PW")
	public String changePW(HttpSession session, Map<String, Object> map,
			@RequestParam("currentPW") String current,
			@RequestParam("newPW") String newPW,
			@RequestParam("confirmNewPW") String confirm) {

		User user = (User) session.getAttribute(LoginController.USER);
		User nUser = userService.getUserByAndrewID(user.getAndrewId());

		if (nUser.getPassword().equals(AuthorizationFilter.MD5(current)) == true
				&& newPW.equals(confirm) == true) {
			nUser.setPassword(AuthorizationFilter.MD5(newPW));
			userService.updateUser(nUser);
			map.put("successMessage",
					"Your password has been successfuly changed");
			return "student-account";
		} else {
			map.put("errorMessage",
					"Error! Please make sure you are entering right password");
			return "student-account";
		}

	}

	@RequestMapping("/student/register")
	public String showRegister(Map<String, Object> map) {
		// read all booking and events here
		map.put("user", new User());
		return "student-register";
	}

	@RequestMapping("/student/register/submit")
	public String register(
			Map<String, Object> map,
			@RequestParam(value = "andrewId", required = false) String andrewId,
			@RequestParam(value = "firstname", required = false) String firstname,
			@RequestParam(value = "lastname", required = false) String lastname,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "confirmPassword", required = false) String confirmPassword,
			@RequestParam(value = "obey", required = false) String obey) {
		User user = new User();
		String users = userService.getAndrewList();
		if (users.isEmpty()) {
			map.put("errorMessage",
					"The andrew ID your provide is not allowed to register in our system");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		}
		String[] acceptedUsers = users.split(",");
		if (acceptedUsers == null) {
			map.put("errorMessage",
					"The andrew ID your provide is not allowed to register in our system");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		} else {
			boolean allowed = false;
			for (int i = 0; i < acceptedUsers.length; i++) {
				if (acceptedUsers[i].equals(andrewId)) {
					allowed = true;
					break;
				}
			}
			if (allowed == false) {
				map.put("errorMessage",
						"The andrew ID your provide is not allowed to register in our system");
				map.put("user", user);
				map.put("obey", obey);
				return "student-register";
			}
		}
		user.setAndrewId(andrewId);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setPassword(password);
		if (!"check".equals(obey)) {
			map.put("errorMessage",
					"You have to check I understand the rule inorder to register.");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		}
		if (!User.checkUser(user)) {
			map.put("errorMessage",
					"Required information is missing for create new user");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		} else if (!user.getPassword().equals(confirmPassword)) {
			map.put("errorMessage",
					"Password is not as same as confirmed password");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		}
		User u = userService.getUserByAndrewID(user.getAndrewId());
		if (u != null) {
			map.put("errorMessage",
					"User with the Andrew ID is already existed.");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		}
		user.setType(User.USER_STUDENT);
		user.setPassword(AuthorizationFilter.MD5(user.getPassword()));
		try {
			userService.create(user);
		} catch (Exception e) {
			map.put("errorMessage",
					"Not able to create account, by unknown reason.");
			map.put("user", user);
			map.put("obey", obey);
			return "student-register";
		}
		return "student-register-success";
	}

}
