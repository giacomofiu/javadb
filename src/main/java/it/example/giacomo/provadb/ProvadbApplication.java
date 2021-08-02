package it.example.giacomo.provadb;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import it.example.giacomo.provadb.DAOImpl.JdbcUserDao;
import it.example.giacomo.provadb.DAOImpl.JpaUserDao;
import it.example.giacomo.provadb.DAOImpl.UserDAO;
import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.Domain.User;
import it.example.giacomo.provadb.Domain.UserEntity;
import it.example.giacomo.provadb.repositoryspringdata.UserRepository;

//Excludes errors if datasource is not configured
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@SpringBootApplication(scanBasePackages={"it.example.giacomo.provadb.DAOImpl"})
//@ComponentScan("it.example.giacomo.provadb.DAOImpl")
@SpringBootApplication
public class ProvadbApplication {

	private static final Logger log = LoggerFactory.getLogger(ProvadbApplication.class);
	private static Dao<User> userDao;
	
/*	@Autowired
	private static UserRepository userRepository;*/
	
	public static void main(String[] args) {
		SpringApplication.run(ProvadbApplication.class, args);
		
		log.info("main method of ProvadbApplication");
		
		userDao = new UserDAO();
		User user1 = getUser(0);
		//log.info(user1.toString());
		userDao.update(user1, new String[] {"Jake", "jake@domain.com"});
		
		User user2 = getUser(1);
		//log.info(user2.toString());
		
		userDao.delete(user2);
		userDao.save(new User("Julie", "julie@domain.com"));
		//log.info(user2.toString());
		
		userDao.getAll().forEach(user -> log.info(user.getName()));
		log.info(String.valueOf(userDao.getAll().size()));
		/*List<User> usersList;
		usersList = userDao.getAll();
		log.info(String.valueOf(usersList.size()));*/
		
		
		//prova con JPA - Funziona tutto
		UserEntity user = new UserEntity("OBrian", "obrian@domain.com");
		//FUNZIONA!!!
		//saveUserEntity(user);
		  //UserEntity userFromDb = getUserJpa(6);
		//log.info(userFromDb.toString());
		
		//l'update funziona sia se prendi prima l'oggetto userEntity dal db e lo passi come parametro al metodo updateUserEntity
		//sia se crei un oggetto vuoto e passi un id come parametro, per es. cosi - ricorda di aggiornare il metodo update in jpaUserDao
		//MA l'update di norma si fa prima con una getById o findById e poi si fa l'update 
		  //String[] paramsArray = new String[] {"6", "Obrian", "obrian@domain.com"};
		  //updateUserEntity(new UserEntity("", ""), paramsArray);
		  //normalmente senza id 
		  //String[] paramsArray = new String[] {"Obrian", "obrian@domain.com"};
		  //updateUserEntity(userFromDb, paramsArray);

		//la cancellazione tramite id non vale per delete, se provo a passare un nuovo oggetto con campi vuoti e a settare l'id uguale
		//all'id della riga da cancellare si ha errore java.lang.reflect.InvocationTargetException, Caused by: java.lang.IllegalArgumentException: Removing a detached instance it.example.giacomo.provadb.Domain.UserEntity#6 (6 e' l'id)
		//deleteUserEntity(userFromDb);
		
		//getAll
		/*
		List<UserEntity> usersList = getAllUsersJpa();		
		for(UserEntity u : usersList) {
			log.info(u.toString());
		}*/
		

		
		//prova con JDBC
		/*List<User> userList = getAllJdbc();
		for(User u : userList) {
			log.info(u.toString());
		}*/
		
		User userJdbc = getUserJdbc(9);
		//log.info(userJdbc.toString());
	
		userJdbc = new User("JimmySprice", "jimmysprice@domain.com");
		//saveUserJdbc(userJdbc);
		String[] params = new String[] {"JimMY", "jimmy@domain.com"};
		//qui lo setto io ma andrebbe preso prima dal db
		//userJdbc.setId(10);
		//updateUserJdbc(userJdbc, params);
		//userJdbc.setId(13);
		//deleteUserJdbc(userJdbc);
		
		
		
		//la prova con hibernate la faccio da indexController perchè non è supportato Autowired con static
		
	}

	
    private static class JpaUserDaoHolder {
        private static final JpaUserDao jpaUserDao = new JpaUserDao(new JpaEntityManagerFactory().getEntityManager());
    }
    
    public static Dao<UserEntity> getJpaUserDao() {
        return JpaUserDaoHolder.jpaUserDao;
    }    
	
	private static User getUser(long id) {   
        Optional<User> user = userDao.get(id);
        return user.orElseGet(
          () -> new User("non-existing user", "no-email"));
    }
	
	private static void saveUserEntity(UserEntity user) {
		getJpaUserDao().save(user);
	}
	
	private static UserEntity getUserJpa(long id) {
		Optional<UserEntity> user = getJpaUserDao().get(id);
		return user.orElseGet(
				() -> new UserEntity("non-existing user", "no-email"));
	}
	
	private static List<UserEntity> getAllUsersJpa(){
		List<UserEntity> usersList = getJpaUserDao().getAll();
		
		return usersList;
	}
	
	private static void updateUserEntity(UserEntity user, String[] params) {
		getJpaUserDao().update(user, params);
	}
	
	private static void deleteUserEntity(UserEntity user) {
		getJpaUserDao().delete(user);
	}

	
	
    private static class JDBCUserDaoHolder {
        private static final JdbcUserDao jdbcUserDao = new JdbcUserDao();
    }

    public static Dao<User> getJdbcUserDao() {
        return JDBCUserDaoHolder.jdbcUserDao;
    }    
    
	private static void saveUserJdbc(User user) {
		getJdbcUserDao().save(user);
	}

	private static List<User> getAllJdbc() {
		return getJdbcUserDao().getAll();
	}
	
	private static User getUserJdbc(long id) {
		Optional<User> user = getJdbcUserDao().get(id);
		
		return user.orElseGet(
				() -> new User("non-existing user", "no-email"));		
	}

	private static void updateUserJdbc(User user, String[] params) {
		getJdbcUserDao().update(user, params);
	}

	private static void deleteUserJdbc(User user) {
		getJdbcUserDao().delete(user);
	}
	
}
