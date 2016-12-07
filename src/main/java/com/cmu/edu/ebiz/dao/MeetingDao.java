package com.cmu.edu.ebiz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmu.edu.ebiz.pojo.Meeting;

@Repository
public class MeetingDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void create(Meeting meeting) {
		Session session = sessionFactory.getCurrentSession();
		session.save(meeting);
	}

	public List<Meeting> getList() {
		Session session = sessionFactory.getCurrentSession();
		List<Meeting> list = null;
		Criteria criteria = session.createCriteria(Meeting.class);
		list = criteria.list();
		return list;
	}

	public List<Meeting> getList(List<Criterion> criterions) {
		List<Meeting> list = null;
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Meeting.class);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		list = criteria.list();
		return list;
	}
	
	public Meeting get(List<Criterion> criterions) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Meeting.class);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return (Meeting) criteria.uniqueResult();
	}

	public void remove(Meeting meeting) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(meeting);
	}

	public void update(Meeting meeting) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(meeting);
	}
}
