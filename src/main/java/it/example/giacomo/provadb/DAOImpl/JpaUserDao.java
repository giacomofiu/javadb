package it.example.giacomo.provadb.DAOImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.Domain.UserEntity;

public class JpaUserDao implements Dao<UserEntity>{

//example from Baeldung https://github.com/eugenp/tutorials/tree/master/persistence-modules/hibernate-jpa
//project patterns->design-patterns-architectural->src->main->java... daopattern
	private EntityManager entityManager;
	
	public JpaUserDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<UserEntity> get(long id) {
		
		return Optional.ofNullable(entityManager.find(UserEntity.class, id));	
	}

	@Override
	public List<UserEntity> getAll() {

		Query query = entityManager.createQuery("SELECT e FROM UserEntity e");
		return (List<UserEntity>) query.getResultList();
	}

	@Override
	public void save(UserEntity user) {
		
		executeInsideTransaction(entityManager -> entityManager.persist(user));		
	}

	@Override
	public void update(UserEntity userEntity, String[] params) {
		//userEntity.setId(Objects.requireNonNull(Long.parseLong(params[0]), "Id cannot be null"));
		userEntity.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
		userEntity.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));
        executeInsideTransaction(entityManager -> entityManager.merge(userEntity));	
	}

	@Override
	public void delete(UserEntity userEntity) {
		executeInsideTransaction(entityManager -> entityManager.remove(userEntity));
	}
	
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			action.accept(entityManager);
			tx.commit();
		}
		catch (RuntimeException e) {
			tx.rollback();
			throw e; 
		}
		
	}
	
	
}
