package com.cmu.edu.ebiz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmu.edu.ebiz.pojo.Room;





@Repository
public class RoomDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void createNewRoom(String roomName, boolean isActive){
		Session session = sessionFactory.getCurrentSession();
		Room room = new Room();
		room.setIsActive(isActive);
		room.setName(roomName);
		if(isActive)
			room.setIsActiveString("Available");
		else
			room.setIsActiveString("On hold");
		session.save(room);
	}
	
	public void changeStatus(int roomID, boolean isActive){
		Session session = sessionFactory.getCurrentSession();
		List<Room> list= null;
		Criteria criteria = session.createCriteria(Room.class)
				.add(Restrictions.eq("id", roomID));
		list = criteria.list();
		Room r = list.get(0);
		r.setIsActive(isActive);
		if(isActive)
			r.setIsActiveString("Available");
		else
			r.setIsActiveString("On Hold");
		session.merge(r);
	}
	
	public void deleteRoom(int roomID){
		Session session = sessionFactory.getCurrentSession();
		List<Room> list= null;
		Criteria criteria = session.createCriteria(Room.class)
				.add(Restrictions.eq("id", roomID));
		list = criteria.list();
		Room r = list.get(0);
		session.delete(r);
	}
	
	public List<Room> getList() {
		Session session = sessionFactory.getCurrentSession();
		List<Room> list = null;
		Criteria criteria = session.createCriteria(Room.class);
		list = criteria.list();
		return list;
	}
	
	public List<Room> getAvailableList() {
		Session session = sessionFactory.getCurrentSession();
		List<Room> list = null;
		Criteria criteria = session.createCriteria(Room.class).add(Restrictions.eq("isActive", true));
		list = criteria.list();
		return list;
	}
	
	public Room getRoomByName(String roomName){
		Session session = sessionFactory.getCurrentSession();
		List<Room> list = null;
		Criteria criteria = session.createCriteria(Room.class).add(Restrictions.eq("name", roomName));
		list = criteria.list();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}

}
