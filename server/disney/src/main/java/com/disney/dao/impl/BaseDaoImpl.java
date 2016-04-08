package com.disney.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Value;

import com.disney.dao.BaseDao;
import com.disney.util.PageUtil;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private SessionFactory sessionFactory;

	@Value("#{commonSessionFactory}")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	public void flush() {
		getCurrentSession().flush();
	}

	public void clear() {
		getCurrentSession().clear();
	}

	protected Class<T> clazz;

	/**
	 * 通过反射泛型获取Class类型,getGenericSuperclass()方法获取对象的泛型的父类类型信息，
	 * getActualTypeArguments()[0]方法得到T的真实类型
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * 公共的增，删，改，查方法
	 */
	@SuppressWarnings("unchecked")
	public T find(Long id) {
		return (T) getCurrentSession().get(this.clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getCurrentSession().createCriteria(this.clazz).list();
	}

	public Serializable save(T entity) {
		return getCurrentSession().save(entity);
	}

	public void update(T entity) {
		getCurrentSession().update(entity);
	}

	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public T getUniqueObjectWithAttrMap(Map<String, Object> attrMap) {
		Criteria c = this.getCurrentSession().createCriteria(this.clazz);
		for (String attr : attrMap.keySet()) {
			c.add(Restrictions.eq(attr, attrMap.get(attr)));
		}
		return (T) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getListWithAttrMap(Map<String, Object> attrMap) {
		Criteria c = this.getCurrentSession().createCriteria(this.clazz);
		for (String attr : attrMap.keySet()) {
			c.add(Restrictions.eq(attr, attrMap.get(attr)));
		}
		return c.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getListWithAttrMap(Map<String, Object> attrMap,PageUtil page) {
		Criteria c = this.getCurrentSession().createCriteria(this.clazz);
		for (String attr : attrMap.keySet()) {
			c.add(Restrictions.eq(attr, attrMap.get(attr)));
		}
		
		c.addOrder(Order.desc("id"));
		
		return c.setFirstResult((page.getCurrPage()-1)*page.getPageSize())
				.setMaxResults(page.getPageSize())
				.list();
	}
	
	
	@Override
	public void delete(Long id) {
		List<Long> ids = new ArrayList<Long>(1);
		ids.add(id);
		this.delete(ids);
	}

	@Override
	public Integer delete(List<Long> idList) {
		String hql = "delete from " + clazz.getName() + " where id in (:idList)";
		return getCurrentSession().createQuery(hql).setParameterList("idList", idList).executeUpdate();
	}

	@Override
	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByHql(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public void updateBatch(final String sql, final List<Object[]> paramsList) throws Exception {
		sessionFactory.getCurrentSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(sql);
				for (Object[] params : paramsList) {
					for (int i = 0; i < params.length; i++) {
						statement.setObject(i + 1, params[i]);
					}
					statement.addBatch();
				}
				statement.executeBatch();
			}
		});
	}

	@Override
	public void updateProperties(final String sql, final Object... params) throws Exception {
		sessionFactory.getCurrentSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(sql);
				for (int i = 0; i < params.length; i++) {
					statement.setObject(i + 1, params[i]);
				}
				statement.executeUpdate();
			}
		});
	}

}
