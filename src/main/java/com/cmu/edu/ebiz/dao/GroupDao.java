package com.cmu.edu.ebiz.dao;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmu.edu.ebiz.pojo.Group;
import com.cmu.edu.ebiz.pojo.User;

@Repository
public class GroupDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void dismiss(int teamNumber) {
		Session session = sessionFactory.getCurrentSession();
		List<Group> list = null;
		String hql = "FROM Group";
		Query query = session.createQuery(hql);
		list = query.list();
		int currentTeams = list.size();
		
		if (currentTeams == teamNumber) {
			for (int i = 0; i < teamNumber; i++) {
				Group g = list.get(i);
				g.setIsExpired(false);
				session.update(g);
			}
		} else if (currentTeams < teamNumber) {
			for (int i = 1; i <= (teamNumber - currentTeams); i++) {
				Group g = new Group();
				g.setIsExpired(false);
				session.save(g);
			}
			for (int i = 0; i < teamNumber; i++) {
				list = query.list();
				Group g = list.get(i);
				g.setIsExpired(false);
				session.merge(g);
			}
		} else if (currentTeams > teamNumber) {
			for (int i = 0; i < teamNumber; i++) {
				Group g = list.get(i);
				g.setIsExpired(false);
				session.merge(g);
			}
			for(int i = teamNumber;i<currentTeams;i++){
				Group g = list.get(i);
				g.setIsExpired(true);
				session.merge(g);
			}
		}
	}

	public List<Group> getList() {
		Session session = sessionFactory.getCurrentSession();
		List<Group> list = null;
		// Criteria criteria = session.createCriteria(Group.class);
		// list = criteria.list();
		String hql = "FROM Group";
		Query query = session.createQuery(hql);
		list = query.list();
		return list;
	}

	public List<Group> getActiveGroupList() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Group G WHERE G.isExpired = false";
		Query query = session.createQuery(hql);
		List list = query.list();

		// List<Group> list = null;
		// Criteria criteria = session.createCriteria(Group.class);
		// criteria.add(Restrictions.eq("isExpired", false));
		// list = criteria.list();
		return list;
	}

	public Group getGroup(int groupId) {
		Session session = sessionFactory.getCurrentSession();
		List<Group> list = null;
		Criteria criteria = session.createCriteria(Group.class);
		criteria.add(Restrictions.like("id", groupId));
		list = criteria.list();
		return list.get(0);
	}

	public void create(Group group) {
		Session session = sessionFactory.getCurrentSession();
		session.save(group);
	}
	
	public void setGroupName(int groupId,String roomName) {
		Session session = sessionFactory.getCurrentSession();
		List<Group> list = null;
		Criteria criteria = session.createCriteria(Group.class);
		criteria.add(Restrictions.like("id", groupId));
		list = criteria.list();
		Group g = list.get(0);
		g.setName(roomName);
		session.merge(g);
	}
	
	public void resetAllGroupName(){
		Session session = sessionFactory.getCurrentSession();
		List<Group> list = null;
		Criteria criteria = session.createCriteria(Group.class);
		list = criteria.list();
		for(int i=0; i < list.size();i++){
			Group g = list.get(i);
			String teamName = "Team " + Integer.toString(i+1); 
			g.setName(teamName);
			session.merge(g);
		}
	}

}
