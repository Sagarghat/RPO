package com.ojas.rpo.security.dao.billingModel;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BillingModel;

public interface BillingModelDao extends Dao<BillingModel, Long> {

	public List<BillingModel> findAllData(Integer pageNo, Integer pageSize);

	public int deleteById(Long id);
	
	
}