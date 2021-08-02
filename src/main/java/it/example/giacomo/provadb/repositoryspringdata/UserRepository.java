package it.example.giacomo.provadb.repositoryspringdata;

import org.springframework.data.repository.CrudRepository;
import it.example.giacomo.provadb.Domain.UserEntityHibernate;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

//CrudRepository is part of SpringData
public interface UserRepository extends CrudRepository<UserEntityHibernate, Integer> {

}