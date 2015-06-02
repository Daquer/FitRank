package br.com.fitrank.persistencia;

import java.sql.SQLException;

import javax.persistence.EntityManager;

public abstract class GenericDAO<T> extends JpaDAO {

	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void insert(T type) throws SQLException {
		super.open();
		super.beginTransaction();
		super.persist(type);
		super.flush();
		super.refresh(type);
		super.commitTransaction();
		super.close();
	}

	@SuppressWarnings("unchecked")
	public T update(T type) throws SQLException {
		super.open();
		super.beginTransaction();
		T merge = (T) super.merge(type);
		super.commitTransaction();
		super.close();
		return merge;
	}

	@SuppressWarnings("unchecked")
	public void delete(T type) throws SQLException {
		super.open();
		super.beginTransaction();
		type = (T) super.merge(type);
		super.remove(type);
		super.commitTransaction();
		super.close();
	}

	public T find(int entityID) throws SQLException {
		super.open();
		EntityManager em = super.getEntityManager();
		T find = em.find(entityClass, entityID);
		super.close();
		return find;
	}

}