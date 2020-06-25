package com.ojas.rpo.security.dao.remainder;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Remainder;

public class JpaRemainderDao extends JpaDao<Remainder, Long> implements RemainderDao {
	
	public JpaRemainderDao() {
		super(Remainder.class);
	}

	@Override
    @Transactional
    public Remainder save(Remainder entity)
    {
        return this.getEntityManager().merge(entity);
    }
}