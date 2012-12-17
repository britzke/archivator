package de.archivator.beans;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Eine EntityManagerBean stellt der Applikation
 * einen EntityManager zur Verfügung.
 * @author bubi
 */
@ApplicationScoped
public class EntityManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces @PersistenceContext
	private EntityManager entityManager;

	/**
	 * Erzeugt eine neue EntityManagerBean. Initialisiert den EntityManager.
	 */
	public EntityManagerBean() {
		System.out.println("entityManagerBean (Application) initializing...");
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("mitglieder");
		entityManager = emf.createEntityManager();
		System.out.println("entityManagerBean (Application) initialized");
	}

	/**
	 * Liefert die Eigenschaft entityManager.
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Setzt die Eigenschaft entityManager.
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
