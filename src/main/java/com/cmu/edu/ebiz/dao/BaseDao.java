/**
 * 08-600 
 * Kewei Wang
 * 08/23/2012
 */
package com.cmu.edu.ebiz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 * BaseDao
 * 
 * @version 1.0
 * @param <T>
 */
public abstract class BaseDao<T> {
	private Class<T> clazz;

	public BaseDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	private enum OperFlag {
		CREATE, RETRIEVE, UPDATE, DELETE
	}

	protected void create(T t) throws Exception {
		operation(t, OperFlag.CREATE);
	}

	protected List<T> retrieve(T t) throws Exception {
		return operation(t, OperFlag.RETRIEVE);
	}

	@SuppressWarnings("unchecked")
	protected List<T> retrieve() throws Exception {
		List<T> list = null;
		Session session = null;
		Criteria criteria = null;

		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			criteria = session.createCriteria(clazz);
			list = criteria.list();
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	protected void update(T t) throws Exception {
		operation(t, OperFlag.UPDATE);
	}

	protected void delete(T t) throws Exception {
		operation(t, OperFlag.DELETE);
	}

	@SuppressWarnings("unchecked")
	private List<T> operation(T t, OperFlag flag) throws Exception {
		List<T> list = null;
		Session session = null;
		Criteria criteria = null;

		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			list = null;
			switch (flag) {
			case CREATE: {
				session.save(t);
				break;
			}
			case RETRIEVE: {

				criteria = session.createCriteria(clazz);
				list = criteria.add(Example.create(t)).list();
				break;
			}
			case UPDATE: {
				session.merge(t);
				break;
			}
			case DELETE: {
				session.delete(t);
				break;
			}
			default: {
				break;
			}
			}
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getList() throws Exception {
		List<T> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			Criteria criteria = session.createCriteria(clazz);
			session.getTransaction().begin();
			list = criteria.list();
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	protected List<T> getList(List<Criterion> criterions) throws Exception {
		List<T> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();

			Criteria criteria = session.createCriteria(clazz);
			if (criterions != null) {
				for (Criterion criterion : criterions) {
					criteria.add(criterion);
				}
			}
			list = criteria.list();
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	
	/**
	 * 
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getList(int first, int max, String orderBy,
			boolean isAsc, List<Criterion> criterions) throws Exception {
		List<T> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();

			Criteria criteria = session.createCriteria(clazz);
			if (orderBy != null) {
				if (isAsc) {
					criteria.addOrder(Order.asc(orderBy));
				} else {
					criteria.addOrder(Order.desc(orderBy));
				}
			}
			if (criterions != null) {
				for (Criterion criterion : criterions) {
					criteria.add(criterion);
				}
			}
			if (max != 0) {
				criteria.setFirstResult(first);
				criteria.setMaxResults(max);
			}
			list = criteria.list();
			session.flush();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 *
	 * 
	 * @param criterions
	 * @return count 
	 * @throws SQLException
	 */
	protected long getCount(List<Criterion> criterions) throws Exception {
		Session session = null;
		long count = 0;
		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();
			Criteria criteria = session.createCriteria(clazz);
			if (criterions != null) {
				for (Criterion criterion : criterions) {
					criteria.add(criterion);
				}
			}
			count = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
			session.flush();
			session.getTransaction().commit();

		} catch (HibernateException e) {
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected Integer getMaxId(List<Criterion> criterions) throws Exception {
		Integer t = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();

			Criteria criteria = session.createCriteria(clazz);
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
			t = (Integer) criteria.setProjection(Projections.max("id")).uniqueResult();
			session.flush();
			session.getTransaction().commit();

		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return t;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected T get(List<Criterion> criterions) throws Exception {
		T t = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.setFlushMode(FlushMode.AUTO);
			session.getTransaction().begin();

			Criteria criteria = session.createCriteria(clazz);
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
			t = (T) criteria.uniqueResult();
			session.flush();
			session.getTransaction().commit();

		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
		return t;
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	public List<T> getByExample(T t) throws Exception {
		return this.retrieve(t);
	}

}
