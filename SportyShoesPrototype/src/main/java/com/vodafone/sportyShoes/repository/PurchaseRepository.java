package com.vodafone.sportyShoes.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vodafone.sportyShoes.model.Product;
import com.vodafone.sportyShoes.model.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Integer>{

	@Query(value="select \r\n" + 
			"T1.*\r\n" + 
			"from\r\n" + 
			"   PURCHASE T1\r\n" + 
			"   join PRODUCT T2 on T1.PRODUCT_ID = T2.ID\r\n" + 
			"   join CATEGORY T3 on T2.CATEGORY_ID = T3.ID\r\n" + 
			"where\r\n" + 
			"   formatdatetime(T1.PURCHASE_TIMESTAMP,'yyyy-MM-dd') = :dateString\r\n" + 
			"   and T3.NAME = :categoryName", nativeQuery=true)
	ArrayList<Purchase> getDetails(@Param("categoryName") String category, @Param("dateString") String dateString);

	@Query(value="select\r\n" + 
			"   T3.NAME,\r\n" + 
			"   formatdatetime(PURCHASE_TIMESTAMP,'yyyy-MM-dd'),\r\n" + 
			"   count(*) as count,\r\n" + 
			"   round(sum(T2.PRICE),2)\r\n" + 
			"from\r\n" + 
			"   PURCHASE T1\r\n" + 
			"   join PRODUCT T2 on T1.PRODUCT_ID = T2.ID\r\n" + 
			"   join CATEGORY T3 on T2.CATEGORY_ID = T3.ID\r\n" + 
			"group by \r\n" + 
			"   T3.NAME,\r\n" + 
			"   formatdatetime(PURCHASE_TIMESTAMP,'yyyy-MM-dd')\r\n" + 
			"order by\r\n" + 
			"   formatdatetime(PURCHASE_TIMESTAMP,'yyyy-MM-dd') desc,\r\n" + 
			"   T3.NAME", nativeQuery=true)
	ArrayList<ArrayList<String>> getSummary();

}
