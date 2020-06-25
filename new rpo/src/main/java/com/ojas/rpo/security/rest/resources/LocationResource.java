package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ojas.rpo.security.dao.location.LocationDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/location")
public class LocationResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LocationDao locationDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAll/{pageNo}/{pageSize}/{regId}")
	public Response list(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize, @PathParam("regId") Long regId)
			throws IOException {
		
		return this.locationDao.findAllLocations(pageNo, pageSize , regId);
		 
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{regId}")
	public Response read(@PathParam("id") Long id , @PathParam("regId") Long regId) {
		this.logger.info("read(id)");

		List<Location> location = this.locationDao.findById(id, regId);
		if (location.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		return new Response(ExceptionMessage.StatusSuccess,location.get(0));
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Location location) {
		this.logger.info("create(): " + location);
		if (location != null && !location.getLocationName().trim().isEmpty()) {
			try {
				location = this.locationDao.save(location);
				return new Response(ExceptionMessage.StatusSuccess, location);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		} else
			return new Response(ExceptionMessage.DataIsEmpty);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") Long id, Location location) {
		this.logger.info("update(): " + location);
		if (location == null || location.getLocationName().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				location = this.locationDao.save(location);
				return new Response(ExceptionMessage.StatusSuccess, location);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}
}
