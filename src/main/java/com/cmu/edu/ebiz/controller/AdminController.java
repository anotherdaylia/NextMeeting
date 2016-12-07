package com.cmu.edu.ebiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmu.edu.ebiz.pojo.Rule;
import com.cmu.edu.ebiz.pojo.User;
import com.cmu.edu.ebiz.service.GroupService;
import com.cmu.edu.ebiz.service.RoomService;
import com.cmu.edu.ebiz.service.RuleService;
import com.cmu.edu.ebiz.service.UserService;
import com.cmu.edu.ebiz.service.MeetingService;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @file AdminController.java
 * @brief The MVC controller for administrators.
 *        http://nextmeeting.herokuapp.com/admin/* will go to this controller.
 * @author Kewei Wang
 * @author Jason Liang
 * 
 */

@Controller
public class AdminController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private MeetingService meetingService;

	/*
	 * @brief For each mapping, controller takes different actions.
	 * 
	 * @return The return String represents the prefix of the jsp file. For
	 * example, admin-maintenance represents admin-maintenance.jsp
	 */
//	@RequestMapping("/admin/maintenance")
//	public String showMaintenance() {
//		return "admin-maintenance";
//	}

	/*
	 * @return Return admin-tak.jsp page.
	 */
	@RequestMapping("/admin/welcome")
	public String showAdmin() {
		return "admin-task";
	}

	/*
	 * @param file Describes the file number for download
	 * 
	 * @param response HttpServletResponse.
	 * 
	 * @return Return file for download.
	 */
//	@RequestMapping("/admin/files/{file}")
//	@ResponseBody
//	public FileSystemResource getFile(@PathVariable("file") int file,
//			HttpServletResponse response) {
//		String fileName = (file == 1) ? "database.log" : "database.script";
//		response.setContentType("text/plain");
//		response.setHeader("Content-Disposition", "attachment; filename="
//				+ fileName);
//		return new FileSystemResource(new File(fileName));
//	}

	@RequestMapping("/admin/account")
	public String showProfile() {
		return "admin-account";
	}

	/*
	 * @brief Change the password for administrator.
	 * 
	 * @param session HttpSession
	 * 
	 * @param map The Map object which will be showed on jsp
	 * 
	 * @param current Current password
	 * 
	 * @param newPW New password
	 * 
	 * @param confirm The confirm (re-enter) password
	 * 
	 * @reurn Return admin-account.jsp
	 */
	@RequestMapping("/admin/account/change")
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
			return "admin-account";
		} else {
			map.put("errorMessage",
					"Error! Please make sure you are entering right password");
			return "admin-account";
		}

	}

	/*
	 * @brief Show all rules.
	 * 
	 * @param map The Map object which will be showed on jsp
	 * 
	 * @param isSuccess A optional parameter. If /admin/rules?isSuccess=1, this
	 * mapping will take isSuccess as a parameter and the value is 1.
	 * 
	 * @return return admin-rules.jsp
	 */
	@RequestMapping("/admin/rules")
	public String listRules(
			Map<String, Object> map,
			@RequestParam(value = "isSuccess", required = false) Integer isSuccess) {
		if (isSuccess != null) {
			if (isSuccess == 1)
				map.put("successMessage", "Success!");
			if (isSuccess == -1)
				map.put("errorMessage", "The value you entered is not valid");
		}
		map.put("ruleList", ruleService.getList());
		return "admin-rules";
	}

	/*
	 * @brief Modify the value of the rule.
	 * 
	 * @param map The Map object which will be showed on jsp.
	 * 
	 * @param ruleId Rule ID that wants to be changed.
	 * 
	 * @param value Rule Value that wants to be assigned.
	 * 
	 * @return redirect to /admin/rules mapping.
	 */

	@RequestMapping("/admin/rules/modify/{ruleId}")
	public String modifyRule(Map<String, Object> map,
			@PathVariable("ruleId") int ruleId,
			@RequestParam("ruleValue") int value) {
		// -- error check starts here -- //
		if (ruleId == Rule.CHECKIN) {
			if (value < 1 || value > 60)
				return "redirect:/admin/rules?isSuccess=-1";
		}
		if (ruleId == Rule.MAXHOUR) {
			if (value < 1 || value > 24)
				return "redirect:/admin/rules?isSuccess=-1";
		}
		if (ruleId == Rule.MAXAFTERNOON) {
			if (value < 1 || value > 7)
				return "redirect:/admin/rules?isSuccess=-1";
		}
		if (ruleId == Rule.MAXWEEK) {
			if (value < 1 || value > 10)
				return "redirect:/admin/rules?isSuccess=-1";
		}

		if (ruleId == Rule.CHECKIN) {
			if (value < 1 || value > 60)
				return "redirect:/admin/rules?isSuccess=-1";
		}
		// -- end of error check -- //
		ruleService.modifyRule(ruleId, value);
		return "redirect:/admin/rules?isSuccess=1";
	}

	/*
	 * @brief Show team list
	 * 
	 * @param map The Map object which will be showed on jsp
	 * 
	 * @param isSuccess A optional parameter. If /admin/rules?isSuccess=1, this
	 * mapping will take isSuccess as a parameter and the value is 1.
	 */

	@RequestMapping("/admin/groups")
	public String listGroups(
			Map<String, Object> map,
			@RequestParam(value = "isSuccess", required = false) Integer isSuccess) {
		System.out.println("enter group");
		if (isSuccess != null) {
			if (isSuccess == 1)
				map.put("successMessage", "Success!");
			if (isSuccess == -1)
				map.put("errorMessage", "The value you entered is not valid.");
		}
		map.put("groupList", groupService.getActiveGroupList());
		return "admin-team";
	}
	
	@RequestMapping("/admin/groups/updateGroupName/{groupId}")
	public String editGroupName(@PathVariable("groupId") int groupId,
			@RequestParam("newGroupName") String newGroupName) {
		if(newGroupName == null || newGroupName.equals("")) {
			return "redirect:/admin/groups?isSuccess=-1";
		}
		groupService.setGroupName(groupId, newGroupName);
		return "redirect:/admin/groups?isSuccess=1";
	}

	/*
	 * @brief Empty all teams and assign a new team number.
	 * 
	 * @param map The Map object which will be showed on jsp.
	 * 
	 * @param stringTeamNumber The new team number.
	 * 
	 * @return redirect to the mapping /admin/groups with parameter isSuccess
	 * and the value is equal to 1.
	 */
	@RequestMapping("/admin/dismiss")
	public String dismissGroup(Map<String, Object> map,
			@RequestParam("teamNumber") String stringTeamNumber) {
		if(stringTeamNumber == null || stringTeamNumber.equals("")) {
			return "redirect:/admin/groups?isSuccess=-1";
		}
		if (isInteger(stringTeamNumber) == false) {
			return "redirect:/admin/groups?isSuccess=-1";
		}
		int teamNumber = Integer.parseInt(stringTeamNumber);
		if (teamNumber <= 0) {
			return "redirect:/admin/groups?isSuccess=-1";
		} else {
			groupService.dismiss(teamNumber);
			groupService.resetAllTeamName();
			userService.quitAllUser();
			meetingService.deleteAllFromNow();
		}
		return "redirect:/admin/groups?isSuccess=1";
	}

	/*
	 * @brief Check whether the input string is an Integer.
	 * 
	 * @return True or False.
	 * 
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/*
	 * @brief Show room list
	 * 
	 * @param map The Map object which will be showed on jsp
	 * 
	 * @param isSuccess A optional parameter. If /admin/rules?isSuccess=1, this
	 * mapping will take isSuccess as a parameter and the value is 1.
	 * 
	 * @return Return admin-room.jsp
	 */
	@RequestMapping("/admin/rooms")
	public String listRooms(
			Map<String, Object> map,
			@RequestParam(value = "isSuccess", required = false) Integer isSuccess) {
		if (isSuccess != null) {
			if (isSuccess == 1)
				map.put("successMessage", "Success!");
			if (isSuccess == -1)
				map.put("errorMessage", "The value you entered is not valid.");
			if (isSuccess == -2)
				map.put("errorMessage", "The room is already existed.");
		}
		map.put("roomList", roomService.getList());
		return "admin-room";
	}
	
	/*
	 * @brief Add room
	 * 
	 * @param map The Map object which will be showed on jsp
	 * 
	 * @param roomName The room's name.
	 * 
	 * @param option The availability of the room. if value equals to 1 then it is available and vice versa.
	 * 
	 * @return redirect to /admin/rooms.
	 */
	@RequestMapping("/admin/rooms/add")
	public String createRoom(Map<String, Object> map,
			@RequestParam("roomName") String roomName,
			@RequestParam("statusOption") int option) {
		if (roomName == null || roomName.trim().equals("")) {
			return "redirect:/admin/rooms?isSuccess=-1";
		}
		if (option < 1 || option > 2) {
			return "redirect:/admin/rooms?isSuccess=-1";
		}
		if (roomService.getRoomByName(roomName) != null) {
			return "redirect:/admin/rooms?isSuccess=-2";
		}
		// if option value == 1 -> available
		// if option value == 2 -> on hold
		boolean isActive = true;
		if (option == 1)
			isActive = true;
		if (option == 2)
			isActive = false;
		roomService.createRoom(roomName, isActive);
		return "redirect:/admin/rooms?isSuccess=1";
	}
	

	
	/*
	 * @brief Delete room
	 * 
	 * @param map The Map object which will be showed on jsp.
	 * 
	 * @param roomId The room's ID.
	 * 
	 * @return redirect to /admin/rooms.
	 * 
	 */
	@RequestMapping("/admin/rooms/delete/{roomId}")
	public String deleteRoom(@PathVariable("roomId") int roomID) {
		roomService.deleteRoom(roomID);
		return "redirect:/admin/rooms?isSuccess=1";
	}
	
	/*
	 * @brief Release room. Change the room status to available.
	 * 
	 * @param roomId The room's ID.
	 * 
	 * @return redirect to /admin/rooms.
	 * 
	 */
	@RequestMapping("/admin/rooms/release/{roomId}")
	public String releaseRoom(@PathVariable("roomId") int roomID) {
		roomService.changeStatus(roomID, true);
		return "redirect:/admin/rooms";
	}


	/*
	 * @brief Hold the room. Change the room status to unavailable.
	 * 
	 * @param roomId The room's ID.
	 * 
	 * @return redirect to /admin/rooms.
	 * 
	 */
	@RequestMapping("/admin/rooms/onhold/{roomId}")
	public String holdRoom(@PathVariable("roomId") int roomID) {
		roomService.changeStatus(roomID, false);
		return "redirect:/admin/rooms";
	}
	
	/*
	 * @brief Show student list.
	 * 
	 * @param map The Map object which will be showed on jsp.
	 * 
	 * @param isSuccess A optional parameter. If /admin/rules?isSuccess=1, this
	 * mapping will take isSuccess as a parameter and the value is 1.
	 * 
	 * @return Return admin-student.jsp
	 * 
	 */
	@RequestMapping("/admin/users")
	public String listStudents(Map<String, Object> map,
			@RequestParam(value = "isSuccess", required = false) Integer isSuccess) {
		if (isSuccess != null) {
			if (isSuccess == 1)
				map.put("successMessage", "Success!");
			if (isSuccess == -1)
				map.put("errorMessage", "The value you entered is not valid.");
			
		}
		map.put("studentList", userService.getStudents());
		map.put("andrewList", userService.getAndrewList());
		return "admin-student";
	}

	/*
	 * @brief Reset studnet's password to default value.
	 * 
	 * @param andrewId The student's andrewId.
	 * 
	 * @return Redirect to /admin/users
	 * 
	 */
	@RequestMapping("/admin/users/resetPW/{andrewId}")
	public String resetPW(@PathVariable("andrewId") String andrewId) {
		User u = userService.getUserByAndrewID(andrewId);
		u.setPassword(AuthorizationFilter.MD5(User.defaultPW));
		userService.updateUser(u);
		return "redirect:/admin/users?isSuccess=1";
	}

	/*
	 * @brief Update the andrew list (proved student list).
	 * 
	 * @param andrewList The file name of the andrew list.
	 * 
	 * @return Redirect to /admin/users
	 * 
	 */
	@RequestMapping("/admin/users/update")
	public String updateAndrewList(@RequestParam("andrewList") String andrewList) {
		userService.setAndrewList(andrewList);
		return "redirect:/admin/users?isSuccess=1";
	}

	/*
	 * @brief Remove all student from database (Does not remove the andrew list).
	 * 
	 * @return Redirect to /admin/users
	 * 
	 */
	@RequestMapping("/admin/users/deleteAll")
	public String deleteUsers() {
		userService.deleteAllUser();
		meetingService.deleteAllFromNow();
		return "redirect:/admin/users?isSuccess=1";
	}
	
	/*
	 * @brief Show administrator list.
	 * 
	 * @param map The Map object which will be showed on jsp.
	 * 
	 * @param isSuccess A optional parameter. If /admin/rules?isSuccess=1, this
	 * mapping will take isSuccess as a parameter and the value is 1.
	 * 
	 * @return Return admin-admin.jsp
	 * 
	 */
	@RequestMapping("/admin/admins")
	public String listAdmins(
			Map<String, Object> map,
			@RequestParam(value = "isSuccess", required = false) Integer isSuccess) {
		if (isSuccess != null) {
			if (isSuccess == 1)
				map.put("successMessage", "Success!");
			if (isSuccess == -1)
				map.put("errorMessage", "The value you entered is not valid.");
			if (isSuccess == -2)
				map.put("errorMessage", "The user is already existed.");
			if (isSuccess == -3)
				map.put("errorMessage", "You can not delete your own account.");
		}
		map.put("adminList", userService.getAdmins());
		return "admin-admin";
	}
	
	/*
	 * @brief Add new administrator.
	 * 
	 * @param firstName The first name of the administrator.
	 * 
	 * @param lastName The last name of the administrator.
	 * 
	 * @param userName The user name of the administrator.
	 * 
	 * @return Redirect to /admin/admins
	 */
	@RequestMapping("/admin/admins/add")
	public String createAdmin(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("userName") String userName) {
		User u = new User();
		if (firstName == null || firstName.equals("")) {
			return "redirect:/admin/admins?isSuccess=-1";
		}
		if (lastName == null || lastName.equals("")) {
			return "redirect:/admin/admins?isSuccess=-1";
		}
		if (userName == null || userName.equals("")) {
			return "redirect:/admin/admins?isSuccess=-1";
		}
		if (userService.getUserByAndrewID(userName.trim()) != null) {
			return "redirect:/admin/admins?isSuccess=-2";
		}
		u.setFirstname(firstName.trim());
		u.setLastname(lastName.trim());
		u.setAndrewId(userName.trim());
		u.setType(User.USER_ADMIN);
		u.setPassword(AuthorizationFilter.MD5(User.defaultPW));
		userService.create(u);
		return "redirect:/admin/admins?isSuccess=1";
	}

	/*
	 * @brief Delete administrator.
	 * 
	 * @param andrewId The adrew ID of the administrator.
	 * 
	 * @param session The HttpSession used to get the current login user.
	 * 
	 * @return Redirect to /admin/admins
	 */
	@RequestMapping("/admin/admins/delete/{andrewId}")
	public String deleteAdmin(@PathVariable("andrewId") String andrewId,
			HttpSession session) {
		User u = (User)session.getAttribute(LoginController.USER);
		if(u.getAndrewId().equals(andrewId)){
			return "redirect:/admin/admins?isSuccess=-3";
		}
		userService.deleteByAndrewId(andrewId);
		return "redirect:/admin/admins?isSuccess=1";
	}

}
