package com.ojas.rpo.security.dao.user;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.entity.UserSearch;

public interface UserDao extends Dao<User, Long>  {
User findByName(String name);

	List<User> findByRole(String role);

	List<User> findByRoleAndId(Long id, String role);

	List<User> findByOnlyBdmleadRole(Long regId);

	List<User> findByOnlyAMRole(Long regId);

	Response updateUserById(Long id, User userUpdate);

	Response findAllUsers(Integer pageNo, Integer pageSize, Long regId,String sortingOrder, String sortingField,

	String searchField, String searchInput,Boolean flag);

	public Response getAllReportingManagers();

	Response getUserNamesByRole(String role,Long regId);

	Response getRecruitersByReportingId(Long id);

	Response findAllUsersByRegId(Integer pageNo, Integer pageSize, Long regId,Boolean flag);
	
	Response updateUserByRegId(Long id, Long regId,User userUpdate);

	//public boolean chekduplicate(String email);

	Response usersListByRegIdRole(Integer pageNo, Integer pageSize, Long regId, String role,boolean flag);
	
	public int userCount(Long id);
	
	public Response findAllUsers(UserSearch userSearch) ;
	
	

	User loadUserByUsername(String username) throws UsernameNotFoundException;

	List<String> findActiveContactByEmail(Long id, String email, Long registrationId);
}





































