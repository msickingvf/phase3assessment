package com.vodafone.sportyShoes.repository;

import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vodafone.sportyShoes.model.User;
@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	@Query(value="select * from User where regexp_like(email,:filterString,'i') ", nativeQuery=true)
	ArrayList<User> findByEmailContainingIgnoreCase(@Param("filterString") String filterString);
	

}
