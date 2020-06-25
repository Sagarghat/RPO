package com.ojas.rpo.security.dao.customerType;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CustomerType;

public interface CustomerTypeDao extends Dao<CustomerType, Long>{
	
	public List<CustomerType> findAll(Integer pageNo, Integer pageSize); 
	
}
