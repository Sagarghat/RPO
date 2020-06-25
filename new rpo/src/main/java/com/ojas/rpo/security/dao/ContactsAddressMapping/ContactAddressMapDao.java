package com.ojas.rpo.security.dao.ContactsAddressMapping;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddressDetails;
import com.ojas.rpo.security.entity.ContactAddressDetails;

public interface ContactAddressMapDao extends Dao<ContactAddressDetails, Long> {
	

	public List<ContactAddressDetails> findBycpId(Long cpid);
	
	public Boolean deleteById(Long addressId);

	public List<ContactAddressDetails> findById(Long id);
}
