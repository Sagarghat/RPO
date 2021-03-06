package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.ContactsAddressMapping.ContactAddressMapDao;
import com.ojas.rpo.security.entity.AddressDetails;
import com.ojas.rpo.security.entity.ContactAddressDetails;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/Contact_address_map")
public class ContactsAddressMappingResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ContactAddressMapDao contactAddressMapDao;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response readAddressDetails(@PathParam("id") Long id) {
		this.logger.info("read(id)");
	 List<ContactAddressDetails> findById = this.contactAddressMapDao.findById(id);
		if (findById == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, findById);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(ContactAddressDetails addressdetails) {
		Response response = null;
		if (addressdetails.getCid() == null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid customer contact id");
		}
		addressdetails = this.contactAddressMapDao.save(addressdetails);
		response = new Response(ExceptionMessage.StatusSuccess, addressdetails);
		response.setRes("Address Details Added Successfully");

		if (addressdetails == null) {

			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid Address Details");

		}
		return response;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id,  ContactAddressDetails addressdetails) {
		System.out.println("==========inside post method====RPO");
		addressdetails.setId(id);
		Response response = null;
		if (addressdetails.getCid() == null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid customer contact id");
		}
		
		else {
			ContactAddressDetails details = this.contactAddressMapDao.save(addressdetails);
			if (details == null) {
				return new Response(ExceptionMessage.DataIsNotSaved);
			} else {
			response = new Response(ExceptionMessage.StatusSuccess, details);
			response.setRes("AddressDetails Details Updated Successfully ");
		}
		}

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readContactAddressDetails/{cpid}")
	public @ResponseBody Response readContactAddressDetails(@PathParam("cpid") Long cpid) {
		this.logger.info("read(cpid)");
		List<ContactAddressDetails> contactAddressDetails = this.contactAddressMapDao.findBycpId(cpid);
		if (contactAddressDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, contactAddressDetails);
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletById(@PathParam("id") Long addressId) {
		boolean isDeleted = contactAddressMapDao.deleteById(addressId);

		if (isDeleted)
			return new Response(ExceptionMessage.StatusSuccess,
					"the AddressDetails with id: " + addressId + " is deleted");

		return new Response(ExceptionMessage.Bad_Request);

	}

}
