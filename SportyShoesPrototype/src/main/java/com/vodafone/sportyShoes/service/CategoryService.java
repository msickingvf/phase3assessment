package com.vodafone.sportyShoes.service;

import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.sportyShoes.model.Category;
import com.vodafone.sportyShoes.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired 
	CategoryRepository categoryRepository;
	public ArrayList<Category> getCategories(){
		return (ArrayList<Category>) categoryRepository.findAll();
	}
}
