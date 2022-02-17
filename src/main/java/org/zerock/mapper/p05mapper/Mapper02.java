package org.zerock.mapper.p05mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface Mapper02 {

	String getLastNameById(Integer id);
	
	String getCustomerNameById(Integer id);
	
	List<String> getProductNameByCategory(String category);

	List<String> getProductNamesByCategoryAndPrice(@Param("category") String category, @Param("price") Double price);

	List<String> getSupplierNamesByCityAndCountry(@Param("city") String city, @Param("country") String country);
	
}
