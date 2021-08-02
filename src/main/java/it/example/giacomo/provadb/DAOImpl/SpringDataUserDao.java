package it.example.giacomo.provadb.DAOImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.DAOInterface.DaoImproved;
import it.example.giacomo.provadb.Domain.UserEntityHibernate;
import it.example.giacomo.provadb.repositoryspringdata.UserRepository;

@Repository
public class SpringDataUserDao implements Dao<UserEntityHibernate>{

	@Autowired
	private UserRepository userRepository;	
	
	@Override
	public Optional<UserEntityHibernate> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Optional<UserEntityHibernate> get(Integer id) {
		Optional<UserEntityHibernate> userEntityHibernate = userRepository.findById(id);
		return userEntityHibernate;
	}	
	
	@Override
	public List<UserEntityHibernate> getAll() {
		
		Iterable<UserEntityHibernate> uehIterable = userRepository.findAll();		
		List<UserEntityHibernate> uehList = new ArrayList<>();
		Iterator<UserEntityHibernate> uehIterator = uehIterable.iterator();
		
		while (uehIterator.hasNext()) {
			uehList.add(uehIterator.next());
		}
		return uehList;
	}

	@Override
	public void save(UserEntityHibernate ueh) {
		userRepository.save(ueh);		
	}

	@Override
	public void update(UserEntityHibernate ueh, String[] params) {
		ueh.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
		ueh.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));        		
		userRepository.save(ueh);
	}

	@Override
	public void delete(UserEntityHibernate ueh) {
		userRepository.delete(ueh);		
	}

	
	
}
