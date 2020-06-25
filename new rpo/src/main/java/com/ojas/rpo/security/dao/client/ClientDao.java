package com.ojas.rpo.security.dao.client;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ClientSearch;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.ClientChange;

public interface ClientDao extends Dao<Client, Long> {

	List<String> chekduplicate(String clientName, String email, String phone, Long regId);

	Client findByIdAndRegId(Long id, Long regId);

	Response searchAllClientsByRegId(ClientSearch clientSearch);

	Response changeClientOwner(ClientChange clientChange);

}
