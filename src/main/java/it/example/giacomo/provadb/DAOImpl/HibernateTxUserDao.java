package it.example.giacomo.provadb.DAOImpl;

import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.DAOInterface.DaoImproved;
import it.example.giacomo.provadb.Domain.UserEntityHibernate;

@Repository
public class HibernateTxUserDao implements DaoImproved<UserEntityHibernate> {
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger log = LoggerFactory.getLogger(HibernateTxUserDao.class);	
	
	@Override
	public Optional<UserEntityHibernate> get(Integer id) {
		//Transazione manuale con openSession()
		Session session = sessionFactory.openSession();
		Optional<UserEntityHibernate> optUeh = null;
		Transaction tx = null;
	      try {
	          tx = session.beginTransaction();
	          UserEntityHibernate ueh = (UserEntityHibernate) session.get(UserEntityHibernate.class, id);
	          optUeh = Optional.of(ueh);
	          tx.commit();
	       } catch (HibernateException e) {
	          if (tx != null) {
	        	  tx.rollback();
	          }
	          log.error(e.getMessage());
	       } finally {
	          session.close(); 
	       }
		return optUeh;
		
		//Transazione gestita da hibernate context con getCurrentSession()
//		UserEntityHibernate ueh = (UserEntityHibernate) sessionFactory
//                .getCurrentSession().get(UserEntityHibernate.class, id);
//        optUeh = Optional.of(ueh);
//        return optUeh;
	}

	@Override
	public List<UserEntityHibernate> getAll() {
		Session session = sessionFactory.openSession();
		List<UserEntityHibernate> usersehList = null;
		
		Transaction tx = null;
	      try {
	          tx = session.beginTransaction();
	          //in method createQuery it is necessary entity name and not table name
	          usersehList = (List<UserEntityHibernate>) session.createQuery("FROM UserEntityHibernate").list();
	          tx.commit();
	       } catch (HibernateException e) {
	          if (tx != null) {
	        	  tx.rollback();
	          }
	          log.error(e.getMessage());
	       } finally {
	          session.close(); 
	       }
		return usersehList;
	}

	@Override
	public Integer save(UserEntityHibernate ueh) {
		Integer userId = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			userId = (Integer) session.save(ueh);
			tx.commit();
		} catch(HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		return userId;
	}

	@Override
	public void update(Integer id, String[] params) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			UserEntityHibernate ueh = (UserEntityHibernate) session.get(UserEntityHibernate.class, id);
			ueh.setName(params[0]);
			ueh.setEmail(params[1]);
			session.update(ueh);
			tx.commit();
		} catch(HibernateException e) {
			if (tx != null) { 
				tx.rollback();
			}
			log.error(e.getMessage());
		} finally {
			session.close();
		}		
	}

	@Override
	public void delete(Integer id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			UserEntityHibernate ueh = (UserEntityHibernate) session.get(UserEntityHibernate.class, id);
			session.delete(ueh);
			tx.commit();
		} catch(HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error(e.getMessage());
		} finally {
			session.close();
		}		
	}

}
