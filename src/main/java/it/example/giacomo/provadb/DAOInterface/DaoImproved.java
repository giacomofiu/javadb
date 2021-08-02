package it.example.giacomo.provadb.DAOInterface;

import java.util.List;
import java.util.Optional;

public interface DaoImproved<T> {

	Optional<T> get(Integer id);
	
	List<T> getAll();
	
	Integer save(T t);
	
	void update(Integer id, String[] params);
	
	void delete(Integer id);
	
}
