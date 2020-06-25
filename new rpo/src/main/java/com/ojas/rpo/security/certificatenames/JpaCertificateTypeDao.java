package com.ojas.rpo.security.certificatenames;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Certificate;
import com.ojas.rpo.security.entity.Roles;

public class JpaCertificateTypeDao extends JpaDao<Certificate, Long> implements CertificateTypeDao {

	public JpaCertificateTypeDao() {
		super(Certificate.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional
	public Certificate save(Certificate entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional
	public void updateCandidateCertificateDetailsbyId(Long id, List<Certificate> certificates) {
		if (null != certificates) {
			Query certificateDeleteQuery = this.getEntityManager()
					.createNativeQuery(" DELETE FROM candidatecertificatesmap WHERE candidate_ID = ? ");
			certificateDeleteQuery.setParameter(1, id);
			certificateDeleteQuery.executeUpdate();
			for (Certificate certificateData : certificates) {
				Query addCertQuery = this.getEntityManager().createNativeQuery(
						"INSERT INTO candidatecertificatesmap(candidate_ID,certificate_ID) values(?,?)");
				addCertQuery.setParameter(1, id);
				addCertQuery.setParameter(2, certificateData.getId());
				addCertQuery.executeUpdate();
			}
		}
	}

	@Override
	public List<Certificate> findAll(Integer pageNo, Integer pageSize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Certificate> criteriaQuery = builder.createQuery(Certificate.class);

		Root<Certificate> root = criteriaQuery.from(Certificate.class);

		TypedQuery<Certificate> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageNo).setMaxResults(pageSize).getResultList().toString();
		return typedQuery.getResultList();

	}
}
