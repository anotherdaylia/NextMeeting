package com.cmu.edu.ebiz.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmu.edu.ebiz.pojo.Rule;


@Repository
public class RuleDao {
	@Autowired
	private SessionFactory sessionFactory;
	// this is just for rule service
	public void createRule(Rule r){
		Session session = sessionFactory.getCurrentSession();
		session.save(r);
	}
	
	public List<Rule> getList(){
		Session session = sessionFactory.getCurrentSession();
		List<Rule> list = null;
		Criteria criteria = session.createCriteria(Rule.class);
		list = criteria.list();
		return list;
	}
	public void modifyRule(int ruleId, int value){
		Session session = sessionFactory.getCurrentSession();
		List<Rule> list = null;
		Criteria criteria = session.createCriteria(Rule.class).add(Restrictions.eq("id", ruleId));
		list = criteria.list();
		Rule r = list.get(0);
		r.setNumber(value);
		session.merge(r);
	}
}
