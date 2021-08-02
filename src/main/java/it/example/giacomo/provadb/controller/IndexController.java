package it.example.giacomo.provadb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.example.giacomo.provadb.DAOImpl.HibernateAutoTxBenefitDao;
import it.example.giacomo.provadb.DAOImpl.HibernateAutoTxEmployeeDao;
import it.example.giacomo.provadb.DAOImpl.HibernateAutoTxUserDao;
import it.example.giacomo.provadb.DAOImpl.HibernateTxUserDao;
import it.example.giacomo.provadb.DAOImpl.SpringDataUserDao;
import it.example.giacomo.provadb.Domain.Benefit;
import it.example.giacomo.provadb.Domain.Employees;
import it.example.giacomo.provadb.Domain.UserEntityHibernate;

@RestController
public class IndexController {
	
	@Autowired
	private SpringDataUserDao spudao;	
	@Autowired
	private HibernateTxUserDao hiberTxUDao;
	@Autowired
	private HibernateAutoTxUserDao hiberAutoTxUDao;
	
	@Autowired
	private HibernateAutoTxEmployeeDao hiberAutoTxEmployeeDao;
	@Autowired
	private HibernateAutoTxBenefitDao hiberAutoTxBenefitDao;

	
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping(value={"/"}, method=RequestMethod.GET)
	public String index() {
		
		log.info("INTO IndexController");
		
		//Works all
		testSpringData();
		
		return "index";	//al momento non ci sono le jsp configurate in application.properties

	}

	

	@RequestMapping(value={"/hibertx"}, method=RequestMethod.GET)
	public String hiberTx() {
		
		log.info("INTO hibernate transactions");
		
		//Works all
		testHibernateTransactions();
		
		return "index";	//no jsp configured in application.properties
	}
	
	
	
	@RequestMapping(value={"/hiberautotx"}, method=RequestMethod.GET)
	public String hiberAutoTx() {
		
		log.info("INTO hibernate auto transactions");
		
		//Works all
		testHibernateAutoTransactions();
		
		return "index";	//no jsp configured in application.properties
	}	
	
	
	@RequestMapping(value={"/testrelations"}, method=RequestMethod.GET)
	public String testRelations() {
		
		log.info("INTO test relations");
		
		testHibernateRelations();
		
		return "index";	//no jsp configured in application.properties
	}

	
	//Works all
	private void testSpringData() {
		UserEntityHibernate ueh = new UserEntityHibernate();
		ueh.setName("AUAUAU");
		ueh.setEmail("auauau@domain.com");
		//addUserSpringData(ueh);
		
		//ueh = getUserSpringData(1);
		//log.info("id: " +ueh.getId()+ " name: " +ueh.getName()+ " email: " +ueh.getEmail());
		
		//testare
//		List<UserEntityHibernate> uehList = new ArrayList<>();
//		uehList = getAllUsersSpringData();
//		for(UserEntityHibernate useh : uehList) {
//			log.info("id: " +useh.getId()+ " name: " +useh.getName()+ " email: " +useh.getEmail());
//		}
		
		//ueh = getUserSpringData(15);
		//String[] updateParams = new String[] {"auauau", "auauau@domain.com"};
		//updateUserSpringData(ueh, updateParams);
		//deleteUserSpringData(ueh);

	}
	
	private UserEntityHibernate getUserSpringData(Integer id) {		
		Optional<UserEntityHibernate> userEntityHibernate = spudao.get(id);
		return userEntityHibernate.orElseGet(
				() -> new UserEntityHibernate("non-existing user", "no-email"));		
	} 

	private List<UserEntityHibernate> getAllUsersSpringData() {		
		
		return spudao.getAll();					
	}

	private void addUserSpringData(UserEntityHibernate ueh) {		
		
		spudao.save(ueh);		
	}
	
	private void updateUserSpringData(UserEntityHibernate ueh, String[] params) {
		spudao.update(ueh, params);
	}
	
	private void deleteUserSpringData(UserEntityHibernate ueh) {
		spudao.delete(ueh);
	}

	
	
	private void testHibernateTransactions() {
		
		UserEntityHibernate ueh = new UserEntityHibernate();
		ueh.setName("HiberTX");
		ueh.setEmail("hibertx@domain.com");
		//addUserHibernateTx(ueh);
		
		//ueh = getUserHibernateTx(17);
		//log.info("id: " +ueh.getId()+ " name: " +ueh.getName()+ " email: " +ueh.getEmail());
		

//		List<UserEntityHibernate> uehList = new ArrayList<>();
//		uehList = getAllUsersHibernateTx();		
//		for(UserEntityHibernate useh : uehList) {
//			log.info("id: " +useh.getId()+ " name: " +useh.getName()+ " email: " +useh.getEmail());
//		}

		String[] updateParams = new String[] {"superHiberTx", "hibertx@domain.com"};		
		//updateUserHibernateTx(18, updateParams);
		
		//deleteUserHibernateTx(18);
		
	}
	
	

	private UserEntityHibernate getUserHibernateTx(Integer id) {		
		Optional<UserEntityHibernate> userEntityHibernate = hiberTxUDao.get(id);
		return userEntityHibernate.orElseGet(
				() -> new UserEntityHibernate("non-existing user", "no-email"));		
	}
	
	private List<UserEntityHibernate> getAllUsersHibernateTx() {		
		
		return hiberTxUDao.getAll();				
	}
	
	private void addUserHibernateTx(UserEntityHibernate ueh) {		
		
		hiberTxUDao.save(ueh);		
	}	

	private void updateUserHibernateTx(Integer id, String[] params) {
		hiberTxUDao.update(id, params);
	}
	
	private void deleteUserHibernateTx(Integer id) {
		
		hiberTxUDao.delete(id);
	}

	
	
	private void testHibernateAutoTransactions() {
		
		UserEntityHibernate ueh = new UserEntityHibernate();
		ueh.setName("HiberAUTOTX");
		ueh.setEmail("hiberautotx@domain.com");
		//addUserHibernateAutoTx(ueh);
		
		ueh = getUserHibernateAutoTx(22);
		log.info("id: " +ueh.getId()+ " name: " +ueh.getName()+ " email: " +ueh.getEmail());
		

//		List<UserEntityHibernate> uehList = new ArrayList<>();
//		uehList = getAllUsersHibernateAutoTx();
//		for(UserEntityHibernate useh : uehList) {
//			log.info("id: " +useh.getId()+ " name: " +useh.getName()+ " email: " +useh.getEmail());
//		}

		//String[] updateParams = new String[] {"superHiperAUTOTx", "superhiperauto@domain.com"};		
		//updateUserHibernateAutoTx(22, updateParams);
		
		//deleteUserHibernateAutoTx(23);
		
		
		//test relationships
		
	}

	
	private UserEntityHibernate getUserHibernateAutoTx(Integer id) {		
		Optional<UserEntityHibernate> userEntityHibernate = hiberAutoTxUDao.get(id);
		return userEntityHibernate.orElseGet(
				() -> new UserEntityHibernate("non-existing user", "no-email"));		
	}
	
	private List<UserEntityHibernate> getAllUsersHibernateAutoTx() {		
		
		return hiberAutoTxUDao.getAll();				
	}
	
	private void addUserHibernateAutoTx(UserEntityHibernate ueh) {		
		
		Integer added = hiberAutoTxUDao.save(ueh);
		log.info("id added: " + added);
	}	

	private void updateUserHibernateAutoTx(Integer id, String[] params) {
		hiberAutoTxUDao.update(id, params);
	}
	
	private void deleteUserHibernateAutoTx(Integer id) {
		
		hiberAutoTxUDao.delete(id);
	}
	
	
	private void testHibernateRelations() {
		Optional<Benefit> benefitOpt = hiberAutoTxBenefitDao.get(1);
		Benefit benefit = benefitOpt.orElseGet(() -> new Benefit("non-existing benefit", "non-existing benefit type"));
		System.out.println("Benefit info:");
		System.out.println("id: " + benefit.getId());
		System.out.println("benefitType: " + benefit.getBenefitType());
		System.out.println("infoBenefit: " + benefit.getInfoBenefit());
		System.out.println("employee Foreign key: " + benefit.getEmployeeId());
		System.out.println("employee related id: " + benefit.getEmployee().getId());
		System.out.println("employee related name: " + benefit.getEmployee().getFirst());
		
		
		Optional<Employees> employeeOpt = hiberAutoTxEmployeeDao.get(102);
		Employees employee =  employeeOpt.orElseGet(() -> new Employees("non-existing employee", "non-existing employee"));
		Set<Benefit> benefitSet = employee.getBenefitSet();
		System.out.println("Employee:");
		System.out.println("ID: " + employee.getId() + " name: " + employee.getFirst());
		System.out.println("Benefit total: " + benefitSet.size());
		benefitSet.stream().forEach(ben -> System.out.println("benefit ID: " + ben.getId() + " - tipo benefit: " + ben.getBenefitType() + " - info benefit: " + ben.getInfoBenefit() + "\n"));
		
		
		benefit = new Benefit();
		benefit.setBenefitType("Tickets");
		benefit.setInfoBenefit("cinema tickets");
		benefit.setEmployeeId(102);
		//hiberAutoTxBenefitDao.save(benefit);
		
	}
	
}
