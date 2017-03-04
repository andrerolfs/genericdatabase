package com.wartbar.genericdb.util;

import com.wartbar.genericdb.access.IO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

	static Logger logger = Logger.getLogger(IO.class.getName());

	private static void logInfo(String message) {
		logger.log(Level.INFO,message);
	}

	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager entityManager = null;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory==null) {
			try {
				entityManagerFactory = Persistence.createEntityManagerFactory("cisystem");
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		if (entityManager==null) {
			entityManager = getEntityManagerFactory().createEntityManager();
		}

		return entityManager;
	}

	public static void beginTransaction() {
		logInfo("DB.beginTransaction");
		getEntityManager().getTransaction().begin();
	}

	public static void commitTransaction() {
		logInfo("DB.commitTransaction");
		getEntityManager().getTransaction().commit();
	}

	public static void persist(Object o) {
		logInfo("DB.persist");
		DB.getEntityManager().persist(o);
	}

	public static void rollbackTransaction() {
		logInfo("DB.rollbackTransaction");
		getEntityManager().getTransaction().rollback();
	}
}