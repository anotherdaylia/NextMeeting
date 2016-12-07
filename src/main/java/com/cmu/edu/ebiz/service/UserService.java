package com.cmu.edu.ebiz.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmu.edu.ebiz.dao.UserDao;
import com.cmu.edu.ebiz.pojo.Group;
import com.cmu.edu.ebiz.pojo.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;

	public void create(User user) {
		userDao.create(user);
	}

	public void updateUser(User user) {
		userDao.update(user);
	}

	public void delete(int userId) {
		userDao.delete(userId);
	}
	
	public void deleteByAndrewId(String andrewId) {
		userDao.deleteByAndrewId(andrewId);
	}

	public void joinGroup(Group group, int userId) {
		userDao.joinGroup(group, userId);
	}

	public void quitGroup(int userId) {
		userDao.quitGroup(userId);
	}

	public void quitAllUser() {
		userDao.quitAllUser();
	}

	public List<User> getList() {
		return userDao.getList();
	}

	public List<User> getStudents() {
		return userDao.getStudents();
	}

	public void setAndrewList(String s) {
		File f = new File("andrewList.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f, false);
			fos.write(s.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAndrewList() {
		File f = new File("andrewList.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StringBuffer sb = new StringBuffer();
		;
		try {
			FileInputStream fStream = new FileInputStream(f);
			DataInputStream in = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				sb.append(strLine);
			}
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public User getUserByAndrewID(String andrewId) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		Criterion criterion = Restrictions.eq("andrewId", andrewId);
		criterions.add(criterion);
		return userDao.getUser(criterions);
	}

	public List<User> getAdmins() {
		return userDao.getAdmins();
	}

	public void deleteAllUser() {
		userDao.deleteAllUser();
	}
}
