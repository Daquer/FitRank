package br.com.fitrank.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public abstract class JpaDAO {
	private final String PERSISTENCE_UNIT = "livrariaPU";

	@PersistenceContext
	private EntityManager em;

	@PersistenceUnit
	private EntityManagerFactory emf;

	private EntityTransaction transaction;

	protected void open() {
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		}

		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}

	}

	protected void beginTransaction() {
		transaction = em.getTransaction();
		transaction.begin();
	}

	protected void commitTransaction() {
		transaction.commit();
	}

	protected void persist(Object o) {
		em.persist(o);
	}

	protected void remove(Object o) {
		em.remove(o);
	}

	protected Object merge(Object o) {
		return em.merge(o);
	}

	protected void refresh(Object o) {
		em.refresh(o);
	}

	protected void flush() {
		em.flush();
	}

	protected void close() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (em != null && emf.isOpen()) {
			emf.close();
		}
	}

	public EntityManager getEntityManager() {
		return em;
	}
}