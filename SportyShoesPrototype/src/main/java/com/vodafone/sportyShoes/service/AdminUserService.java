package com.vodafone.sportyShoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.sportyShoes.model.AdminUser;
import com.vodafone.sportyShoes.repository.AdminUserRepository;

@Service
public class AdminUserService {

	@Autowired
	private AdminUserRepository adminUserRepository;
	
	public AdminUser getAdminUserByEmailAndPassword(AdminUser thisAdminUser) {
		AdminUser adminUser = adminUserRepository.findByEmailAndPassword(thisAdminUser.getEmail(),thisAdminUser.getPassword());
		return adminUser;
	}
	
	public AdminUser getAdminUserByEmail(String email) {
		AdminUser adminUser = adminUserRepository.findByEmail(email);
		return adminUser;
	}
	
	public boolean setAdminUser(AdminUser user) {
		boolean returnvalue = false;
		AdminUser adminUser = this.getAdminUserByEmail(user.getEmail());
		adminUser.setPassword(user.getPassword());
		adminUserRepository.save(adminUser);
		returnvalue = true;
		return returnvalue;
	}
}
