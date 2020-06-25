package com.ojas.rpo.security.rest.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.ContactsAddressMapping.ContactAddressMapDao;
import com.ojas.rpo.security.dao.addClientContact.AddContactDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.AddContactMapper;
import com.ojas.rpo.security.entity.ContactAddressDetails;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.AddContactSearch;

@Component
@Path("/addClientContact")
public class AddContactResources {

	@Autowired
	private AddContactDao addClientContactDao;

	@Autowired
	private ContactAddressMapDao contactAddressMapDao;

	/* AddContact Save */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(AddContactMapper addContactMapper) {
		Response response = null;
		List<ContactAddressDetails> list = new ArrayList<>();
		AddContact contact = addContactMapper.getContact();
		if ((addContactMapper.getContact() == null) || (addContactMapper.getContact().getClient() == null)) {
			response = new Response(HttpStatus.SC_CONFLICT, ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid Request ");
			return response;
		}
		if (contact.getFirstName() == null || contact.getLastName() == null || contact.getMobile() == null
				|| contact.getEmail() == null || contact.getDomain() == null || contact.getJobTitle() == null) {
			return new Response(HttpStatus.SC_CONFLICT, ExceptionMessage.Bad_Request);
		}
		if (contact.getFirstName().trim().length() == 0 || contact.getLastName().trim().length() == 0
				|| contact.getMobile().trim().length() == 0 || contact.getEmail().trim().length() == 0
				|| contact.getDomain().trim().length() == 0 || contact.getJobTitle().trim().length() == 0
				|| contact.getContactOwner() == null || contact.getClient().getId() == null
				|| contact.getRegistrationId() == null) {

			return new Response(HttpStatus.SC_CONFLICT, ExceptionMessage.DataIsEmpty);
		}

		List<String> data = addClientContactDao.findActiveContactByEmail(contact.getClient().getId(),
				contact.getEmail(), contact.getRegistrationId());
		if (contact.getId() != null) {
			AddContact addContact = addClientContactDao.find(contact.getId());
			Iterator<String> iterator = data.iterator();

			while (iterator.hasNext()) {
				if (iterator.next().equalsIgnoreCase(addContact.getEmail())) {
					iterator.remove();
				}
			}
		}

		if (contact.getId() == null && null != data && !data.isEmpty()) {
			return new Response(ExceptionMessage.EmailExist);
		} else if (contact.getId() != null && null != data && !data.isEmpty() && data.contains(contact.getEmail())) {
			return new Response(ExceptionMessage.EmailExist);
		} else {
			addContactMapper.getContact().setLastModifiedTime(new Date());
			AddContact addcontact = addClientContactDao.save(addContactMapper.getContact());
			if (addcontact != null && addcontact.getId() != null && addContactMapper.getAddress() != null
					&& !addContactMapper.getAddress().isEmpty()) {
				for (ContactAddressDetails address : addContactMapper.getAddress()) {
					address.setCid(addcontact.getId());
					ContactAddressDetails add = contactAddressMapDao.save(address);
					list.add(add);
				}
			}
			addContactMapper.setContact(addcontact);
			addContactMapper.setAddress(list);

		}
		response = new Response(ExceptionMessage.StatusSuccess, addContactMapper);
		return response;
	}

	/*
	 * public Response getDuplicateResponse() { return new
	 * Response(ExceptionMessage.DuplicateRecord); }
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		if (id == null || id == 0) {
			return new Response(ExceptionMessage.Bad_Request);
		}
		List<ContactAddressDetails> address = null;
		AddContact contact = addClientContactDao.find(id);
		Date date = contact.getLastModifiedTime();
		Date lastModifiedTime = new Date();
		long diff = lastModifiedTime.getTime() - date.getTime();

		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

		contact.setDiffOfDays(diffDays + "  Days ago");

		if (contact == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			AddContactMapper addContactMapper = new AddContactMapper();
			if (contact.getId() != null) {
				address = contactAddressMapDao.findBycpId(contact.getId());
			}
			addContactMapper.setContact(contact);
			addContactMapper.setAddress(address);
			return new Response(ExceptionMessage.StatusSuccess, addContactMapper);
		}
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response findDataBasedOnRegId(AddContactSearch addContactSearch) {

		return addClientContactDao.findDataBasedOnRegId(addContactSearch);
	}

}
