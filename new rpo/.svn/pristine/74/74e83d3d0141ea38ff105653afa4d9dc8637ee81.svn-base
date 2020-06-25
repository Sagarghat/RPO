package com.ojas.rpo.security.dao.addClientContact;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.AddContactSearch;

public interface AddContactDao extends Dao<AddContact, Long>  {

	List<AddContact> findContactByClientId(Long id, Long regId);

	
	Response findContactByBdmId(Long id, String role, String sortField,String sortOrder,String searchField,String searchText, Integer pageNo, Integer pageSize);

	public int updatingStatus(Long id, String status);

	List<AddContact> findActiveContactByClientId(Long id,String role, Long userId);

	List<String> findActiveContactByEmail(Long clientId, String email,Long regId);

	List<Object[]> findActiveContactByEmailList(Long clientId, String email);

	HashMap findAll(Integer pageNo, Integer pageSize, Long regId);

	Response searchAll(String result, Integer pageNo, Integer pageSize);

	boolean findEmialDuplicates(String email, Long id);

	List<AddContact> getByContactId(Long id, Long regId);

	Long getClientIdByRegId(Long regId);
	/* public boolean checkDuplicate(Long clientId,Long regId, String email); */

	
	
	public Response findDataBasedOnRegId(AddContactSearch addContactSearch);

}
