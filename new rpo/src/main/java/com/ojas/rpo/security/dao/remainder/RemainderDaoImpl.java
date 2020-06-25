package com.ojas.rpo.security.dao.remainder;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Remainder;

public class RemainderDaoImpl extends JpaDao<Remainder, Long> implements RemainderDao {

	public RemainderDaoImpl() {
		super(Remainder.class);
	}

	
}
