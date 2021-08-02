package it.example.giacomo.provadb.DAOImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.DAOInterface.DaoImproved;
import it.example.giacomo.provadb.Domain.UserEntityHibernate;

@Repository
//@Service
public class HibernateAutoTxUserDao implements DaoImproved<UserEntityHibernate>{

//	@Autowired
//	private SessionFactory sessionFactory;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	//Transactions managed by hibernate
	//@Transactional is not required for get() and getAll()
	//@Transactional
	@Override
	public Optional<UserEntityHibernate> get(Integer id) {
		Optional<UserEntityHibernate> optUeh = null;
		//UserEntityHibernate ueh = (UserEntityHibernate) sessionFactory.getCurrentSession().get(UserEntityHibernate.class, id);
		UserEntityHibernate ueh = entityManager.find(UserEntityHibernate.class, id);
		optUeh = Optional.of(ueh);
		return optUeh;
	}
	
	//@Transactional
	@Override
	public List<UserEntityHibernate> getAll() {
		
		Query query = entityManager.createQuery("FROM UserEntityHibernate");
		List<UserEntityHibernate> uehList = (List<UserEntityHibernate>) query.getResultList();
		//List<UserEntityHibernate> uehList = (List<UserEntityHibernate>) sessionFactory.getCurrentSession().createQuery("From UserEntityHibernate").list();
		return uehList;
	}

	@Transactional
	@Override
	public Integer save(UserEntityHibernate ueh) {	
		entityManager.persist(ueh);
		return ueh.getId();
		//return (Integer) sessionFactory.getCurrentSession().save(ueh);
		
	}

	@Transactional
	@Override
	public void update(Integer id, String[] params) {		
		//UserEntityHibernate ueh = (UserEntityHibernate) sessionFactory.getCurrentSession().get(UserEntityHibernate.class, id);
		UserEntityHibernate ueh = entityManager.find(UserEntityHibernate.class, id);
		ueh.setName(params[0]);
		ueh.setEmail(params[1]);
		entityManager.merge(ueh);
		//sessionFactory.getCurrentSession().update(ueh);
	}

	@Transactional
	@Override
	public void delete(Integer id) {

		UserEntityHibernate ueh = entityManager.find(UserEntityHibernate.class, id);
		entityManager.remove(ueh);
		//UserEntityHibernate ueh = (UserEntityHibernate) sessionFactory.getCurrentSession().get(UserEntityHibernate.class, id);
		//sessionFactory.getCurrentSession().delete(ueh);
	}

}
