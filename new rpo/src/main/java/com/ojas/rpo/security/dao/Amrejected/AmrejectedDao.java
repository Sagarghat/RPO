package com.ojas.rpo.security.dao.Amrejected;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Amrejected;

public interface AmrejectedDao extends Dao<Amrejected, Long> {
	void updateCandiate(Long candiateId, String status);
}
