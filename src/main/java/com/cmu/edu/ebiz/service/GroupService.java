package com.cmu.edu.ebiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.cmu.edu.ebiz.dao.GroupDao;
import com.cmu.edu.ebiz.pojo.Group;

@Service
@Transactional
public class GroupService {
	@Autowired
	private GroupDao groupDao;

	public void create(Group group) {
		groupDao.create(group);
	}

	public Group getGroupById(int groupId) {
		return groupDao.getGroup(groupId);
	}

	public List<Group> getList() {
		return groupDao.getList();
	}

	public List<Group> getActiveGroupList() {
		return groupDao.getActiveGroupList();
	}

	public void dismiss(int newTeamNum) {
		groupDao.dismiss(newTeamNum);
	}
	
	public void setGroupName(int groupId, String roomName) {
		groupDao.setGroupName(groupId, roomName);
	}

	public void resetAllTeamName() {
		groupDao.resetAllGroupName();
	}

}
