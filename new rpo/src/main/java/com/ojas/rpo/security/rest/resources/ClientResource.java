package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Array;
import com.ojas.rpo.security.dao.addressdetails.AddressDetailsDao;
import com.ojas.rpo.security.dao.client.ClientDao;
import com.ojas.rpo.security.entity.AddressDetails;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ClientMapper;
import com.ojas.rpo.security.entity.ClientSearch;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.util.ClientChange;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ValidateClient;

@Component
@Path("/client")
public class ClientResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClientDao clientDao;

	@Autowired
	private AddressDetailsDao addressDetailsDao;

	@Autowired
	OutlookMailSender mailSender;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createClent(ClientMapper clientMapper) throws IOException, ParseException {
		logger.info("Create client");
		Response response = null;
		ValidateClient validate = new ValidateClient();
		Client client = clientMapper.getClient();
		List<AddressDetails> addressList = new ArrayList<>();
		response = validate.validateClient(client);
		ClientMapper mapper = new ClientMapper();
		if (response == null) {

			if (client.getId() == null) {
				client.setCreatedBy(clientMapper.getClient().getCreatedBy());
			}
			try {
				List<String> data = this.clientDao.chekduplicate(client.getClientName(), client.getEmail(),
						client.getPhone(), client.getRegistrationId());
				if (client.getId() == null && null != data && !data.isEmpty()) {
					response = new Response(ExceptionMessage.DuplicateRecord, client);
				} else if (client.getId() != null && null != data && !data.isEmpty()
						&& !data.get(0).equalsIgnoreCase(client.getClientName())) {
					response = new Response(ExceptionMessage.DuplicateRecord, client);

				} else {
					client = clientDao.save(client);
					mapper.setClient(client);
					if (client != null) {

						if (clientMapper.getAddressDetails() != null || !clientMapper.getAddressDetails().isEmpty()) {

							for (AddressDetails address : clientMapper.getAddressDetails()) {
								address.setCid(client.getId());
								addressList.add(addressDetailsDao.save(address));

							}
							mapper.setAddressDetails(addressList);

						}
						response = new Response(ExceptionMessage.StatusSuccess, mapper, "client Created");
					}
				}

			} catch (Exception e) {
				response = new Response(ExceptionMessage.Bad_Request, "Unable to create a client");
			}

		}
		return response;

	}

	@GET
	@Path("/findByIdandRegId/{id}/{regId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response findByIdAndRegId(@PathParam("id") Long id, @PathParam("regId") Long regId)
			throws IOException, ParseException {
		Client client = clientDao.findByIdAndRegId(id, regId);
		Date date = client.getLastUpdatedDate();
		Date lastModifiedTime = new Date();
		long diff = 0;
		if (date != null)
			diff = lastModifiedTime.getTime() - date.getTime();

		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		client.setTimeDiff(diffDays + " days ago");
		List<AddressDetails> address = null;
		if (client != null) {
			address = addressDetailsDao.findBycpId(client.getId());
		} else {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
		ClientMapper maper = new ClientMapper();
		maper.setClient(client);

		maper.setAddressDetails(address);
		Response res = new Response(ExceptionMessage.StatusSuccess, maper);

		return res;
	}
	/*
	 * @GET
	 * 
	 * @Path("/findAll/{pageSize}/{pageNo}/{regId}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public @ResponseBody Response
	 * findAllClientsByRegId(@PathParam("pageSize") Integer pageSize,
	 * 
	 * @PathParam("pageNo") Integer pageNo, @PathParam("regId") Long regId) {
	 * 
	 * return clientDao.getAllClientsByRegId(regId, pageNo, pageSize); }
	 */

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;
		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response searchClients(ClientSearch clientSearch) {
		return clientDao.searchAllClientsByRegId(clientSearch);
	}

	@POST
	@Path("/changeOwner")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response changeOwner(ClientChange clientChange) {

		return this.clientDao.changeClientOwner(clientChange);
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}