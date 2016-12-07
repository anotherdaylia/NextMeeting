package com.cmu.edu.ebiz.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmu.edu.ebiz.dao.MeetingDao;
import com.cmu.edu.ebiz.pojo.Group;
import com.cmu.edu.ebiz.pojo.Meeting;

@Service
@Transactional
public class MeetingService {

	@Autowired
	private MeetingDao meetingDao;
	
	public void create(Meeting meeting) {
		meetingDao.create(meeting);
	}
	
	public Meeting getMeetingById(int id) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("id", id));
		return meetingDao.get(criterions);
	}
	
	public List<Meeting> getList() {
		return meetingDao.getList();
	}
	
	public void deleteUnCheckin(int mins) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, -mins);
		now = cal.getTime();
		List<Meeting> list = null;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.lt("start", now));
		criterions.add(Restrictions.eq("isCheckIn", false));
		list = meetingDao.getList(criterions);
		if (list != null) {
			for (Meeting meeting : list) {
				meetingDao.remove(meeting);
			}
		}
	}

	public void deleteAllFromNow() {
		Date now = new Date();
		List<Meeting> list = null;
		List<Criterion> criterions = new ArrayList<Criterion>();
//		criterions.add(Restrictions.ge("start", now));
		list = meetingDao.getList(criterions);
		if (list != null) {
			for (Meeting meeting : list) {
				meetingDao.remove(meeting);
			}
		}
	}
	
	public void deleteAllThisUserFromNow(String andrewId) {
		Date now = new Date();
		List<Meeting> list = null;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.ge("start", now));
		criterions.add(Restrictions.eq("andrewId", andrewId));
		list = meetingDao.getList(criterions);
		if (list != null) {
			for (Meeting meeting : list) {
				meetingDao.remove(meeting);
			}
		}
	}
	
	public List<Meeting> getMeetingListInByGroup(int idGroup, Date start, Date end) {
		List<Meeting> list;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("idGroup", idGroup));
		criterions.add(Restrictions.ge("start", start));
		criterions.add(Restrictions.le("end", end));
		return meetingDao.getList(criterions);
	} 
	
	public List<Meeting> getMeetingListIn(int idRoom, Date start, Date end) {
		List<Meeting> list;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("idRoom", idRoom));
		criterions.add(Restrictions.ge("start", start));
		criterions.add(Restrictions.le("end", end));
		return meetingDao.getList(criterions);
	} 
	
	public List<Meeting> getMeetingListContain(int idRoom, Date start, Date end) {
		List<Meeting> list;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("idRoom", idRoom));
		criterions.add(Restrictions.le("start", start));
		criterions.add(Restrictions.ge("end", end));
		return meetingDao.getList(criterions);
	} 
	
	public List<Meeting> getMeetingListMixStart(int idRoom, Date start, Date end) {
		List<Meeting> list;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("idRoom", idRoom));
		criterions.add(Restrictions.ge("start", start));
		criterions.add(Restrictions.lt("start", end));
		return meetingDao.getList(criterions);
	} 
	
	public List<Meeting> getMeetingListMixEnd(int idRoom, Date start, Date end) {
		List<Meeting> list;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("idRoom", idRoom));
		criterions.add(Restrictions.gt("end", start));
		criterions.add(Restrictions.le("end", end));
		return meetingDao.getList(criterions);
	}

	public synchronized int checkConflictAndSave(Date start, Date end, Meeting meeting, boolean isNew) {
		int ret = 0;
		int idRoom = meeting.getIdRoom();
		List<Meeting> list = getMeetingListContain(idRoom, start, end);
		if (list != null && !list.isEmpty()) {
			if (!isNew && list.size() == 1 && list.get(0).getId().equals(meeting.getId())){
				ret = 0;
			} else {
				ret = 1;
			}
		}
		list = getMeetingListMixStart(idRoom, start, end);
		if (list != null && !list.isEmpty()) {
			if (!isNew && list.size() == 1 && list.get(0).getId().equals(meeting.getId())){
				ret = 0;
			} else {
				ret = 1;
			}
		}
		list = getMeetingListMixEnd(idRoom, start, end);
		if (list != null && !list.isEmpty()) {
			if (!isNew && list.size() == 1 && list.get(0).getId().equals(meeting.getId())){
				ret = 0;
			} else {
				ret = 1;
			}
		}
		if (ret == 0) {
			if (isNew) {
				meetingDao.create(meeting);
			} else {
				meetingDao.update(meeting);
			}
		}
		return ret;		
	}

	public void remove(Meeting meeting) {
		meetingDao.remove(meeting);	
	}

	public List<Meeting> getMeetingListByAndrewId(String andrewId) {
		List<Meeting> list;
		Date now = new Date();
		Calendar cal = Calendar.getInstance();  
		cal.setTime(now);  
		int MIN_BEFORE = -15;
		cal.add(Calendar.MINUTE, MIN_BEFORE);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("andrewId", andrewId));
		criterions.add(Restrictions.ge("start", cal.getTime()));
		return meetingDao.getList(criterions);
	}

	public void checkIn(Meeting meeting) {
		meeting.setIsCheckIn(true);
		meetingDao.update(meeting);
	}
}
