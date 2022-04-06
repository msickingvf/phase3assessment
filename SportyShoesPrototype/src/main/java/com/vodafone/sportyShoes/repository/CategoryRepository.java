package com.vodafone.sportyShoes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.sportyShoes.model.AdminUser;
import com.vodafone.sportyShoes.model.Category;
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{

}
