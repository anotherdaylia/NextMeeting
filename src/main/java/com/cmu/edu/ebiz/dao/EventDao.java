package com.cmu.edu.ebiz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmu.edu.ebiz.pojo.Event;
import com.cmu.edu.ebiz.pojo.Meeting;


@Repository
public class EventDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void create(Event e) {
		Session session = sessionFactory.getCurrentSession();
		session.save(e);
	}

	public List<Event> getList() {
		Session session = sessionFactory.getCurrentSession();
		List<Event> list = null;
		Criteria criteria = session.createCriteria(Event.class);
		list = criteria.list();
		return list;
	}

	public List<Event> getList(List<Criterion> criterions) {
		List<Event> list = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		list = criteria.list();
		return list;
	}

	public void update(Event e) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(e);
	}
	
	public void delete(Event e) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(e);
	}

	public Event get(List<Criterion> criterions) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Event.class);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return (Event) criteria.uniqueResult();
	}

}
