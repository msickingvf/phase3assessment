package com.vodafone.sportyShoes.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.sportyShoes.model.Product;
import com.vodafone.sportyShoes.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public ArrayList<Product> getProducts(){
		ArrayList<Product> products = (ArrayList<Product>) productRepository.findAll();
		System.out.println("Number of Products: "+products.size());
		return products; 
	}

	public Product getProduct(Integer id) {
		return productRepository.findById(id).orElse(null);
	}

	public boolean setProduct(Product product) {
		productRepository.save(product);
		return true;
	}


}
