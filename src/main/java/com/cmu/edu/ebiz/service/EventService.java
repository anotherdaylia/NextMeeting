package com.cmu.edu.ebiz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmu.edu.ebiz.dao.EventDao;
import com.cmu.edu.ebiz.pojo.Event;
import com.cmu.edu.ebiz.pojo.Meeting;

@Service
@Transactional
public class EventService {
	
	@Autowired
	private EventDao eventDao;

	public List<Event> getEventsListIn(Date start, Date end) {
		List<Event> list;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.ge("startDate", start));
		criterions.add(Restrictions.le("endDate", end));
		return eventDao.getList(criterions);
	}

	public void save(Event e) {
		eventDao.create(e);
	}
	
	public void update(Event e) {
		eventDao.update(e);
	}

	public Event getEventById(int eventId) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("id", eventId));
		return eventDao.get(criterions);
	}

	public void remove(Event e) {
		eventDao.delete(e);
	}
}
