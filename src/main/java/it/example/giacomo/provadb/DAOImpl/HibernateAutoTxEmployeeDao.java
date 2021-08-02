package it.example.giacomo.provadb.DAOImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import it.example.giacomo.provadb.DAOInterface.DaoImproved;
import it.example.giacomo.provadb.Domain.Employees;
import it.example.giacomo.provadb.Domain.UserEntityHibernate;

@Repository
public class HibernateAutoTxEmployeeDao implements DaoImproved<Employees>{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<Employees> get(Integer id) {
		Optional<Employees> employeeOpt = null;
		Employees employee = entityManager.find(Employees.class, id);
		employeeOpt = Optional.of(employee);
		return employeeOpt;
	}

	@Override
	public List<Employees> getAll() {
		Query query = entityManager.createQuery("FROM Employees");
		List<Employees> employeesList = (List<Employees>) query.getResultList();
		return employeesList;
	}

	@Transactional
	@Override
	public Integer save(Employees e) {
		entityManager.persist(e);
		return e.getId();
	}

	@Transactional
	@Override
	public void update(Integer id, String[] params) {
		Employees employee = entityManager.find(Employees.class, id);
		employee.setFirst(params[0]);
		employee.setLast(params[1]);
		employee.setAge(Integer.valueOf(params[2])); //here maybe it is better a DTO
		entityManager.merge(employee);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Employees employee = entityManager.find(Employees.class, id);
		entityManager.remove(employee);	
	}

}
