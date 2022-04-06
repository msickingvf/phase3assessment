package com.vodafone.sportyShoes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.sportyShoes.model.AdminUser;

@Repository
public interface AdminUserRepository extends CrudRepository<AdminUser, Integer>{

	AdminUser findByEmailAndPassword(String email, String password);

	AdminUser findByEmail(String email);


}
