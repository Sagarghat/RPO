package com.ojas.rpo.security.dao.paymentTerms;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;

import com.ojas.rpo.security.entity.PaymentTerms;

public interface PaymentTermsDao extends Dao<PaymentTerms, Long> {

	List<PaymentTerms> findAll(Integer startingRow, Integer pagesize, Integer registationId);

	List<PaymentTerms> findAll(Integer registationId);

	PaymentTerms find(Long id, Integer registationId);
}