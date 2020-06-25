package com.ojas.rpo.security.dao.bdmreqdtls;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.BdmReqSearch;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.BdmUpdate;

public interface BdmReqDao extends Dao<BdmReq, Long>

{

	BdmReq getRequirementById(BdmReqSearch bdmReqSearch);

	Response getAllRequirementsByRegId(BdmReqSearch bdmReqSearch);

	Response updateReqDetails(BdmUpdate bdmUpdate);

}
