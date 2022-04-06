package com.vodafone.sportyShoes.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vodafone.sportyShoes.model.Purchase;
import com.vodafone.sportyShoes.repository.PurchaseRepository;

@Service
public class PurchaseService {
	@Autowired
	PurchaseRepository purchaseRepository;
	public ArrayList<Purchase> getPurchasesByDayAndCategory(String category, String dateString) {
		ArrayList<Purchase> purchases = purchaseRepository.getDetails(category,dateString);
		System.out.println("found "+purchases.size()+" for date: "+dateString+" and category: "+category);
		return purchases;
	}
	public ArrayList<ArrayList<String>> getSummaryTable() {
		return purchaseRepository.getSummary();
	}

}
