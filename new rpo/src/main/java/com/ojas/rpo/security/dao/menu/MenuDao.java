package com.ojas.rpo.security.dao.menu;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Menu;

public interface MenuDao extends Dao<Menu, Long>{
	
	public boolean chekduplicate(String menuName);
	
	List<Menu> findAllMenu(Integer startingRow, Integer pagesize);

}
