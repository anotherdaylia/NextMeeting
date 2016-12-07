package com.cmu.edu.ebiz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmu.edu.ebiz.pojo.Group;
import com.cmu.edu.ebiz.pojo.User;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void create(User greeting) {
		Session session = sessionFactory.getCurrentSession();
		session.save(greeting);
	}

	public void update(User u) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(u);
	}

	public void delete(int userId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("id", userId));
		list = criteria.list();
		User u = list.get(0);
		session.delete(u);
	}
	
	public void deleteByAndrewId(String andrewId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("andrewId", andrewId));
		list = criteria.list();
		User u = list.get(0);
		session.delete(u);
	}

	public void joinGroup(Group group, int userId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("id", userId));
		list = criteria.list();
		User u = list.get(0);

		u.setGroup(group);
		session.merge(u);
	}
	
	public void quitGroup(int userId) {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("id", userId));
		list = criteria.list();
		User u = list.get(0);
		u.setGroup(null);
		session.merge(u);
	}
	
	public void quitAllUser(){
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class);
		list = criteria.list();
		for(User u : list){
			u.setGroup(null);
			session.update(u);
		}
	}
	
	public void deleteAllUser(){
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class);
		list = criteria.list();
		for(User u : list){
			if(u.getType()==User.USER_STUDENT){
				session.delete(u);
			}
		}
	}

	public List<User> getList() {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class);
		list = criteria.list();
		return list;
	}

	public List<User> getStudents() {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("type", User.USER_STUDENT));
		list = criteria.list();
		return list;
	}

	public List<User> getAdmins() {
		Session session = sessionFactory.getCurrentSession();
		List<User> list = null;
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("type", User.USER_ADMIN));
		list = criteria.list();
		return list;
	}

	public User getUser(List<Criterion> criterions) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(User.class);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return (User) criteria.uniqueResult();
	}
}
