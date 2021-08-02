package it.example.giacomo.provadb.DAOImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.example.giacomo.provadb.DAOInterface.DaoImproved;
import it.example.giacomo.provadb.Domain.Benefit;
import it.example.giacomo.provadb.Domain.Employees;

@Repository
public class HibernateAutoTxBenefitDao implements DaoImproved<Benefit>{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<Benefit> get(Integer id) {
		Optional<Benefit> benefitOpt = null;
		Benefit benefit = entityManager.find(Benefit.class, id);
		benefitOpt = Optional.of(benefit);
		return benefitOpt;
	}

	@Override
	public List<Benefit> getAll() {
		Query query = entityManager.createQuery("FROM Benefit");
		List<Benefit> benefitList = (List<Benefit>) query.getResultList();
		return benefitList;
	}	
	

	@Transactional
	@Override
	public Integer save(Benefit ben) {
		entityManager.persist(ben);
		return ben.getId();
	}

	@Transactional
	@Override
	public void update(Integer id, String[] params) {
		Benefit benefit = entityManager.find(Benefit.class, id);
		benefit.setBenefitType(params[0]);
		benefit.setInfoBenefit(params[1]);
		entityManager.merge(benefit);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Benefit benefit = entityManager.find(Benefit.class, id);
		entityManager.remove(benefit);
		
	}

}
