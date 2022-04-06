package com.vodafone.sportyShoes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.sportyShoes.model.AdminUser;
import com.vodafone.sportyShoes.model.Product;
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

}
