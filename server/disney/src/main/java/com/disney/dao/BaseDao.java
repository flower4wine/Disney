package com.disney.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.disney.util.PageUtil;


public interface BaseDao<T> {

	void setSessionFactory(SessionFactory sessionFactory);

	Session getCurrentSession();

	List<T> findByHql(String hql);

	List<T> findAll();

	T find(Long id);

	void delete(Long id);

	void delete(T entity);

	Serializable save(T entity);

	void update(T entity);

	void saveOrUpdate(T entity);

	Integer delete(List<Long> idList);
	T getUniqueObjectWithAttrMap(Map<String, Object> attrMap);

	List<T> getListWithAttrMap(Map<String, Object> attrMap);
	
	List<T> getListWithAttrMap(Map<String, Object> attrMap,PageUtil page);

	void updateBatch(final String sql, final List<Object[]> paramsList) throws Exception;

	void updateProperties(final String sql, final Object... params) throws Exception;

}
