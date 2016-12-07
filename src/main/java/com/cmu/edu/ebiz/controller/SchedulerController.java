package com.cmu.edu.ebiz.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmu.edu.ebiz.pojo.Event;
import com.cmu.edu.ebiz.pojo.EventUI;
import com.cmu.edu.ebiz.pojo.Meeting;
import com.cmu.edu.ebiz.pojo.Room;
import com.cmu.edu.ebiz.pojo.Rule;
import com.cmu.edu.ebiz.pojo.User;
import com.cmu.edu.ebiz.service.EventService;
import com.cmu.edu.ebiz.service.MeetingService;
import com.cmu.edu.ebiz.service.RoomService;
import com.cmu.edu.ebiz.service.RuleService;
import com.cmu.edu.ebiz.service.UserService;

@Controller
public class SchedulerController {
	@Autowired
	private MeetingService meetingService;

	@Autowired
	private UserService userService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private EventService eventService;
	
	private static final String TEAM = "\nTeam: ";
	
	private static final String BY = "\nBy: ";

	/**
	 * url of show all the reservation of student to admin
	 * @return
	 */
	@RequestMapping("/admin/reservation")
	public String defaultAdminCalander() {
		// read all booking and events here
		List<Room> rooms = roomService.getAvailableList();
		if (rooms == null || rooms.isEmpty()) {
			return "admin-calendar";
		}

		return "redirect:/admin/reservation/" + rooms.get(0).getId();
	}

	/**
	 * URL of show all the booking record for admin's view, room based. 
	 * @param map
	 * @param roomId
	 * @return
	 */
	@RequestMapping("/admin/reservation/{roomId}")
	public String showAdminCalander(Map<String, Object> map,
			@PathVariable("roomId") int roomId) {
		// read all booking and events here
		List<Room> rooms = roomService.getAvailableList();
		map.put("rooms", rooms);
		map.put("roomId", roomId);
		return "admin-calendar";
	}

	@RequestMapping("/student")
	public String defaultCalander() {
		// read all booking and events here
		List<Room> rooms = roomService.getAvailableList();
		if (rooms == null || rooms.isEmpty()) {
			return "student-calendar";
		}

		return "redirect:/student/" + rooms.get(0).getId();
	}

	@RequestMapping("/student/checkin/{roomId}/{eventId}")
	public String checkIn(HttpSession session,
			@PathVariable("roomId") int roomId,
			@PathVariable("eventId") int eventId) {
		int CHECK_IN_LIMIT = ruleService
				.getValueByRuleId(Rule.CHECKIN);
		String andrewId = ((User) session.getAttribute(LoginController.USER))
				.getAndrewId();
		Meeting meeting = meetingService.getMeetingById(eventId);
		String errorMessage = null;
		if (meeting.getAndrewId().equals(andrewId)) {
			Date now = new Date();
			Calendar start = Calendar.getInstance();
			start.setTime(now);
			Calendar end = Calendar.getInstance();
			end.setTime(now);
			start.add(Calendar.MINUTE, -CHECK_IN_LIMIT);
			end.add(Calendar.MINUTE, CHECK_IN_LIMIT);
			if (meeting.getStart().after(start.getTime())
					&& meeting.getStart().before(end.getTime())) {
				meetingService.checkIn(meeting);
			} else {
				errorMessage = "You can't check in, becasue either it's already "
						+ CHECK_IN_LIMIT
						+ " minutes later than the starting time, OR more than"
						+ CHECK_IN_LIMIT + " before the starting time";
			}
		}
		if (errorMessage == null) {
			return "redirect:/student/" + roomId;
		} else {
			return "redirect:/student/" + roomId + "?errorMessage="
					+ errorMessage;
		}
	}

	@RequestMapping("/student/{roomId}")
	public String showCalander(
			Map<String, Object> map,
			HttpSession session,
			@PathVariable("roomId") int roomId,
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		// read all booking and events here
		List<Room> rooms = roomService.getAvailableList();
		List<EventUI> events = new ArrayList<EventUI>();
		if (rooms != null) {
			String andrewId = ((User) session
					.getAttribute(LoginController.USER)).getAndrewId();
			List<Meeting> meetings = meetingService
					.getMeetingListByAndrewId(andrewId);

			if (meetings != null) {
				for (Meeting meeting : meetings) {
					EventUI event = new EventUI();
					event.id = meeting.getId();
					event.text = meeting.getTitle();
					event.start_date = EventUI.convert(meeting.getStart());
					event.end_date = EventUI.convert(meeting.getEnd());
					event.isCheckIn = meeting.getIsCheckIn();
					for (Room room : rooms) {
						if (room.getId() == meeting.getIdRoom()) {
							event.roomName = room.getName();
						}
					}
					events.add(event);
				}
			}
		}
		if (errorMessage != null && !errorMessage.equals("")) {
			map.put("errorMessage", errorMessage);
		} else {
			map.put("errorMessage", "");
		}
		map.put("meetings", events);
		map.put("rooms", rooms);
		map.put("roomId", roomId);
		return "student-calendar";
	}

	@RequestMapping("/student/event/delete/{eventId}")
	public @ResponseBody
	EventUI deleteEvent(HttpSession session,
			@PathVariable("eventId") int eventId) {
		EventUI event = new EventUI();
		event.id = 0;
		String andrewId = ((User) session.getAttribute(LoginController.USER))
				.getAndrewId();
		User user = userService.getUserByAndrewID(andrewId);

		Meeting meeting = meetingService.getMeetingById(eventId);
		Date now = new Date();
		if (!meeting.getAndrewId().equals(user.getAndrewId())) {
			event.text = "You can not remove which is not booked by you";
		} else if (now.after(meeting.getStart())) {
			event.text = "You can not cancel the meeting which is already passed the start time.";
		} else {
			event.id = meeting.getId();
			meetingService.remove(meeting);
		}
		return event;
	}

	@RequestMapping("/student/event/update/{eventId}")
	public @ResponseBody
	EventUI updateEvent(HttpSession session,
			@PathVariable("eventId") int eventId,
			@RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("day") int day, @RequestParam("sh") int sh,
			@RequestParam("sm") int sm, @RequestParam("eh") int eh,
			@RequestParam("em") int em,
			@RequestParam(value = "title") String title) {
		// read all booking and events here
		EventUI event = new EventUI();
		Meeting meeting = meetingService.getMeetingById(eventId);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(year, month - EventUI.MONTH, day, sh, sm, 0);
		Date start = cal.getTime();
		cal.set(year, month - EventUI.MONTH, day, eh, em, 0);
		Date end = cal.getTime();
		String andrewId = ((User) session.getAttribute(LoginController.USER))
				.getAndrewId();
		User user = userService.getUserByAndrewID(andrewId);
		Date now = new Date();
		if (!meeting.getAndrewId().equals(andrewId)) {
			event.id = 0;
			event.text = "Can't update other's booking record.";
		} else if (user.getGroup() == null) {
			event.id = 0;
			event.text = "To book a room, you must join a team first.";
		} else if (start.compareTo(meeting.getStart()) == 0
				&& end.compareTo(meeting.getEnd()) == 0
				&& title == meeting.getTitle()) {
			event.id = meeting.getId();
		} else if (now.after(start)) {
			event.id = 0;
			event.text = "The start time is already passed.";
		} else if (eh - sh > ruleService.getValueByRuleId(Rule.MAXHOUR)
				|| (eh - sh == ruleService
						.getValueByRuleId(Rule.MAXHOUR) && em - sm > 0)) {
			event.id = 0;
			event.text = "You can not book a room for more than "
					+ ruleService.getValueByRuleId(Rule.MAXHOUR)
					+ " hours.";
		} else {
			// Potential thread unsafe for checkRule.
			int ret = checkRule(start, end, meeting.getIdGroup(), meeting.getId());
			if (ret != 0) {
				event.id = 0;
				switch (ret) {
				case 1:
					event.text = "You can not book room for "
							+ ruleService.getValueByRuleId(Rule.MAXWEEK)
							+ "weeks later.";
					break;
				case 2:
					event.text = "Your team already booked a room in this day.";
					break;
				case 3:
					event.text = "You can not book a room more than "
							+ ruleService
									.getValueByRuleId(Rule.MAXAFTERNOON)
							+ " times a week after 12:00 pm.";
					break;
				}
				return event;
			} else {
				meeting.setStart(start);
				meeting.setEnd(end);
				meeting.setTitle(title);
				if (meetingService.checkConflictAndSave(start, end, meeting,
						false) == 0) {
					event.id = meeting.getId();
				} else {
					event.id = 0;
					event.text = "Time is conflict.";
				}
			}

		}
		return event;
	}

	@RequestMapping("/student/event/add/{roomId}")
	public @ResponseBody
	EventUI addEvent(HttpSession session, @PathVariable("roomId") int roomId,
			@RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("day") int day, @RequestParam("sh") int sh,
			@RequestParam("sm") int sm, @RequestParam("eh") int eh,
			@RequestParam("em") int em,
			@RequestParam(value = "title") String title) {
		// read all booking and events here
		EventUI event = new EventUI();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(year, month - EventUI.MONTH, day, sh, sm, 0);
		Date start = cal.getTime();
		cal.set(year, month - EventUI.MONTH, day, eh, em, 0);
		Date end = cal.getTime();
		String andrewId = ((User) session.getAttribute(LoginController.USER))
				.getAndrewId();
		User user = userService.getUserByAndrewID(andrewId);
		Date now = new Date();
		if (user.getGroup() == null) {
			event.id = 0;
			event.text = "To book a room, you must join a team first.";
		} else if (eh - sh > ruleService.getValueByRuleId(Rule.MAXHOUR)
				|| (eh - sh == ruleService
						.getValueByRuleId(Rule.MAXHOUR) && em - sm > 0)) {
			event.id = 0;
			event.text = "You can not book a room for more than "
					+ ruleService.getValueByRuleId(Rule.MAXHOUR)
					+ " hours.";
		} else if (now.after(start)) {
			event.id = 0;
			event.text = "The start time is already passed.";
		} else {
			// Potential thread unsafe for checkRule.
			int ret = checkRule(start, end, user.getGroup()
					.getId(), -1);
			if (ret != 0) {
				event.id = 0;
				switch (ret) {
				case 1:
					event.text = "You can not book room for "
							+ ruleService.getValueByRuleId(Rule.MAXWEEK)
							+ "weeks later.";
					break;
				case 2:
					event.text = "Your team already booked a room in this day.";
					break;
				case 3:
					event.text = "You can not book a room more than "
							+ ruleService
									.getValueByRuleId(Rule.MAXAFTERNOON)
							+ " times a week after 12:00 pm.";
					break;
				}
				return event;
			} else {
				Meeting meeting = new Meeting();
				meeting.setAndrewId(user.getAndrewId());
				meeting.setIdGroup(user.getGroup().getId());
				meeting.setStart(start);
				meeting.setEnd(end);
				meeting.setTitle(title);
				meeting.setIsCheckIn(false);
				meeting.setIdRoom(roomId);
				if (meetingService.checkConflictAndSave(start, end, meeting,
						true) == 0) {
					event.id = meeting.getId();
				} else {
					event.id = 0;
					event.text = "Time is conflict.";
				}

			}

		}

		return event;
	}

	@RequestMapping("/student/task")
	public String showTask() {
		// read all booking and events here
		return "student-task";
	}

	@RequestMapping("/admin/event/existed/{roomId}/{sy}/{sm}/{sd}/{ey}/{em}/{ed}")
	public @ResponseBody
	List<EventUI> getExistedMeetingHistoryForAdmin(HttpSession session,
			@PathVariable("roomId") int roomId, @PathVariable("sy") int sy,
			@PathVariable("sm") int sm, @PathVariable("sd") int sd,
			@PathVariable("ey") int ey, @PathVariable("em") int em,
			@PathVariable("ed") int ed) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(sy, sm - EventUI.MONTH, sd, 0, 0);
		Date start = cal.getTime();
		cal.set(ey, em - EventUI.MONTH, ed, 23, 59);
		Date end = cal.getTime();
		
		List<EventUI> events = new ArrayList<EventUI>();
		List<Meeting> meetings = meetingService.getMeetingListIn(roomId, start,
				end);
		if (meetings != null) {
			for (Meeting meeting : meetings) {
				EventUI event = new EventUI();
				event.id = meeting.getId();
				event.text = meeting.getTitle() + TEAM + meeting.getIdGroup() + BY + meeting.getAndrewId();
				event.start_date = EventUI.convert(meeting.getStart());
				event.end_date = EventUI.convert(meeting.getEnd());
				event.readonly = true;
				event.custom = true;
				events.add(event);
			}
		}

		return events;
	}

	@RequestMapping("/student/event/existed/{roomId}/{sy}/{sm}/{sd}/{ey}/{em}/{ed}")
	public @ResponseBody
	List<EventUI> getExistedMeetingHistory(HttpSession session,
			@PathVariable("roomId") int roomId, @PathVariable("sy") int sy,
			@PathVariable("sm") int sm, @PathVariable("sd") int sd,
			@PathVariable("ey") int ey, @PathVariable("em") int em,
			@PathVariable("ed") int ed) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(sy, sm - EventUI.MONTH, sd, 0, 0);
		Date start = cal.getTime();
		cal.set(ey, em - EventUI.MONTH, ed, 23, 59);
		Date end = cal.getTime();
		String andrewId = ((User) session.getAttribute(LoginController.USER))
				.getAndrewId();
		List<EventUI> events = new ArrayList<EventUI>();
		List<Meeting> meetings = meetingService.getMeetingListIn(roomId, start,
				end);
		if (meetings != null) {
			for (Meeting meeting : meetings) {
				EventUI event = new EventUI();
				event.id = meeting.getId();
				
				event.start_date = EventUI.convert(meeting.getStart());
				event.end_date = EventUI.convert(meeting.getEnd());
				Date now = new Date();
				if (now.after(meeting.getStart())
						|| !meeting.getAndrewId().equals(andrewId) || meeting.getIsCheckIn() == true) {
					event.text = meeting.getTitle() + TEAM + meeting.getIdGroup() + BY + meeting.getAndrewId();
					event.readonly = true;
					event.custom = true;
				} else {
					event.text = meeting.getTitle();
					event.readonly = false;
					event.custom = false;
				}
				events.add(event);
			}
		}

		return events;
	}

	@RequestMapping("/student/event/program/{sy}/{sm}")
	public @ResponseBody
	List<EventUI> getExistedEventHistory(@PathVariable("sy") int y,
			@PathVariable("sm") int m) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(y, m - EventUI.MONTH, 1, 0, 0);
		Date start = cal.getTime();
		Date end;
		cal.setTime(start);
		cal.add(Calendar.MONTH, 1);
		end = cal.getTime();
		List<EventUI> events = new ArrayList<EventUI>();
		List<Event> es = eventService.getEventsListIn(start, end);
		if (events != null) {
			for (Event e : es) {
				EventUI event = new EventUI();
				event.id = e.getId();
				event.text = e.getTitle();
				event.start_date = EventUI.convert(e.getStartDate());
				event.end_date = EventUI.convert(e.getEndDate());
				event.readonly = true;
				event.custom = true;
				events.add(event);
			}
		}

		return events;
	}

	@RequestMapping("/admin/event/program/{sy}/{sm}")
	public @ResponseBody
	List<EventUI> getAdminExistedEventHistory(@PathVariable("sy") int y,
			@PathVariable("sm") int m) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(y, m - EventUI.MONTH, 1);
		Date start = cal.getTime();
		Date end;
		cal.setTime(start);
		cal.add(Calendar.MONTH, 1);
		end = cal.getTime();
		List<EventUI> events = new ArrayList<EventUI>();
		List<Event> es = eventService.getEventsListIn(start, end);
		if (events != null) {
			for (Event e : es) {
				EventUI event = new EventUI();
				event.id = e.getId();
				event.text = e.getTitle();
				event.start_date = EventUI.convert(e.getStartDate());
				event.end_date = EventUI.convert(e.getEndDate());
				Date now = new Date();
				if (now.after(e.getStartDate())) {
					event.readonly = true;
					event.custom = true;
				} else {
					event.readonly = false;
					event.custom = false;
				}
				events.add(event);
			}
		}

		return events;
	}

	@RequestMapping("/admin/event/program/add")
	public @ResponseBody
	EventUI addProgramEvent(@RequestParam("year") int year,
			@RequestParam("month") int month, @RequestParam("day") int day,
			@RequestParam("sh") int sh, @RequestParam("sm") int sm,
			@RequestParam("eh") int eh, @RequestParam("em") int em,
			@RequestParam(value = "title") String title) {
		// read all booking and events here
		EventUI event = new EventUI();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(year, month - EventUI.MONTH, day, sh, sm);
		Date start = cal.getTime();
		cal.set(year, month - EventUI.MONTH, day, eh, em);
		Date end = cal.getTime();
		Date now = new Date();
		Event e = new Event();
		if (now.after(start)) {
			event.id = 0;
			event.text = "The start time is already passed.";
			return event;
		}
		
		e.setStartDate(start);
		e.setEndDate(end);
		e.setTitle(title);
		try {
			eventService.save(e);
			event.id = e.getId();
		} catch (Exception e1) {
			event.id = 0;
			event.text = "Unable to save the event.";
		}

		return event;
	}

	@RequestMapping("/admin/event/program/update/{eventId}")
	public @ResponseBody
	EventUI updateProgramEvent(@PathVariable("eventId") int eventId,
			@RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("day") int day, @RequestParam("sh") int sh,
			@RequestParam("sm") int sm, @RequestParam("eh") int eh,
			@RequestParam("em") int em,
			@RequestParam(value = "title") String title) {
		// read all booking and events here
		EventUI event = new EventUI();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.set(year, month - EventUI.MONTH, day, sh, sm);
		Date start = cal.getTime();
		cal.set(year, month - EventUI.MONTH, day, eh, em);
		Date end = cal.getTime();
		Event e = eventService.getEventById(eventId);
		Date now = new Date();
		if (now.after(start)) {
			event.id = 0;
			event.text = "The start time is already passed.";
			return event;
		}
		e.setStartDate(start);
		e.setEndDate(end);
		e.setTitle(title);
		try {
			eventService.update(e);
			event.id = e.getId();
		} catch (Exception e1) {
			event.id = 0;
			event.text = "Unable to save the event.";
		}
		return event;
	}

	@RequestMapping("/admin/event/program/delete/{eventId}")
	public @ResponseBody
	EventUI deleteProgramEvent(@PathVariable("eventId") int eventId) {
		EventUI event = new EventUI();
		event.id = 0;

		Event e = eventService.getEventById(eventId);
		Date now = new Date();
		if (now.after(e.getStartDate())) {
			event.text = "You can not cancel the meeting which is already passed the start time.";
		} else {
			event.id = e.getId();
			eventService.remove(e);
		}
		return event;
	}
	
	public int checkRule(Date start, Date end, Integer idGroup, int id) {
		// TODO Auto-generated method stub
		Date now = new Date();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.setTime(now);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		int maxWeek = ruleService.getValueByRuleId(Rule.MAXWEEK);
		cal.add(Calendar.WEEK_OF_YEAR, maxWeek);
		if (end.after(cal.getTime())) {
			return 1;
		}
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		Date dateStart = cal.getTime();
		cal.setTime(end);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		Date dateEnd = cal.getTime();
		List<Meeting> meetings = meetingService.getMeetingListInByGroup(idGroup, dateStart, dateEnd);
		if (meetings != null && !meetings.isEmpty()) {
			if (meetings.size() != 1 || meetings.get(0).getId() != id) {
				return 2;
			}
		}
		cal.setTime(start);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		Date weekStart = cal.getTime();
		cal.setTime(start);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		Date weekEnd = cal.getTime();
		cal.setTime(start);
		meetings = meetingService.getMeetingListInByGroup(idGroup, weekStart, weekEnd);
		if (meetings != null && !meetings.isEmpty() && cal.get(Calendar.HOUR_OF_DAY) >= 12) {
			int count = 0;
			for (Meeting meeting : meetings) {
				cal.setTime(meeting.getStart());
				if (cal.get(Calendar.HOUR_OF_DAY) >= 12 && meeting.getId() != id) {
					count++;
				}
			}
			if (count >= ruleService.getValueByRuleId(Rule.MAXAFTERNOON)) {
				return 3;
			}
		}
		return 0;
	}
}
