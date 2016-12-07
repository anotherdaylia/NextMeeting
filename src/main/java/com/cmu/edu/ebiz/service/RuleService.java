package com.cmu.edu.ebiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmu.edu.ebiz.dao.RuleDao;
import com.cmu.edu.ebiz.pojo.Rule;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class RuleService {
	@Autowired
	private RuleDao ruleDao;

	private HashMap<Integer, Integer> rules = null;

	public void init() {
		if (rules != null) {
			return;
		}
		synchronized (this) {
			if (rules == null) {
				rules = new HashMap<Integer, Integer>();
				List<Rule> ruleList = ruleDao.getList();
				for (Rule r : ruleList) {
					rules.put(r.getId(), r.getNumber());
				}
			}
		}
	}

	public int getValueByRuleId(int ruleId) {
		init();
		return rules.get(ruleId);
	}

	public List<Rule> getList() {
		init();
		return ruleDao.getList();
	}

	public void modifyRule(int ruleId, int value) {
		init();
		rules.put(ruleId, value);
		ruleDao.modifyRule(ruleId, value);
	}

}
