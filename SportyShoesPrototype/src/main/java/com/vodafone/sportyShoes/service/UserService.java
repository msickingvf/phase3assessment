package com.vodafone.sportyShoes.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.sportyShoes.model.User;
import com.vodafone.sportyShoes.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public ArrayList<User> getAllUsers() {
		return (ArrayList<User>) userRepository.findAll();
	}

	public ArrayList<User> getUsersByEmailfilter(String filterString) {
		System.out.println("Filter: "+filterString);
		return (ArrayList<User>) userRepository.findByEmailContainingIgnoreCase(filterString);
	}

}
