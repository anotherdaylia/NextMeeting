package com.cmu.edu.ebiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmu.edu.ebiz.dao.RoomDao;
import com.cmu.edu.ebiz.pojo.Room;

import java.util.List;

@Service
@Transactional
public class RoomService {
	@Autowired
	private RoomDao roomDao;

	public List<Room> getList() {
		return roomDao.getList();
	}
	
	public List<Room> getAvailableList() {
		return roomDao.getAvailableList();
	}

	public void createRoom(String roomName, boolean isActive) {
		roomDao.createNewRoom(roomName, isActive);
	}
	
	public Room getRoomByName(String roomName) {
		return roomDao.getRoomByName(roomName);
	}
	
	public void deleteRoom(int roomID){
		roomDao.deleteRoom(roomID);
	}

	public void changeStatus(int roomID, boolean isActive) {
		roomDao.changeStatus(roomID, isActive);
	}

}