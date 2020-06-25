package com.ojas.rpo.security.dao.typeOfAddres;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.TypeOfAddress;

public interface TypeOfAddressDao extends Dao<TypeOfAddress,Long> {

	List<TypeOfAddress> findByBillingAddress();

	List<TypeOfAddress> findByOfficeAddress();
	
	List<TypeOfAddress> findAllTypeOfAddress(Integer startingRow, Integer pagesize);

}
