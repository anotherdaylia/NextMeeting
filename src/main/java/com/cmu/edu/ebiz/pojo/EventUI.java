package com.cmu.edu.ebiz.pojo;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EventUI {
	public Integer id;
	public String text;
	public String start_date;
	public String end_date;
	public String roomName;
	public boolean readonly;
	public boolean custom;
	public boolean isCheckIn;
	public static final int YEAR = 1900;
	public static final int MONTH = 1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public boolean getIsCheckIn() {
		return isCheckIn;
	}

	public void setIsCheckIn(boolean isCheckIn) {
		this.isCheckIn = isCheckIn;
	}

	public static String convert(Date date) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		cal.setTime(date);
		sb.append(cal.get(Calendar.YEAR)).append("-")
				.append(cal.get(Calendar.MONTH) + MONTH).append("-")
				.append(cal.get(Calendar.DAY_OF_MONTH));
		sb.append(" ").append(cal.get(Calendar.HOUR_OF_DAY)).append(":")
				.append((cal.get(Calendar.MINUTE) == 0) ? "00" : cal.get(Calendar.MINUTE));
		return sb.toString();
	}
}
