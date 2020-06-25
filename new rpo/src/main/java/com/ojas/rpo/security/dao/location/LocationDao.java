package com.ojas.rpo.security.dao.location;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Response;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link location}s.
 *
 * @author jyothi g. Sorst <jyothi@ojas-it.com>
 */
public interface LocationDao extends Dao<Location, Long>
{

	Response findAllLocations(Integer startingRow, Integer pageSize, Long regId);

	List<Location> findById(Long id, Long regId);

}
