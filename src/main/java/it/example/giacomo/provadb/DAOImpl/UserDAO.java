package it.example.giacomo.provadb.DAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.Domain.User;

public class UserDAO implements Dao<User> {

	//users List acts like an in-memory database
	//just an example without any db involved 
	private List<User> usersList = new ArrayList<>();
	
	public UserDAO() {
		usersList.add(new User("John", "john@domain.com"));
		usersList.add(new User("Susan", "susan@domain.com"));
	}
	
	
	@Override
	public Optional<User> get(long id) {
	
		return Optional.ofNullable(usersList.get((int)id));
	}

	@Override
	public List<User> getAll() {
		return usersList;
	}

	@Override
	public void save(User user) {
		
		usersList.add(user);		
	}

	@Override
	public void update(User user, String[] params) {
		//so it updates the user already exists in list
		user.setName(Objects.requireNonNull(params[0], "Name non puo' essere null"));
		user.setEmail(Objects.requireNonNull(params[1], ""));
		//if you do add it adds another user with passed parameters 
		//usersList.add(user);
	}

	@Override
	public void delete(User user) {
		usersList.remove(user);
		
	}



}
