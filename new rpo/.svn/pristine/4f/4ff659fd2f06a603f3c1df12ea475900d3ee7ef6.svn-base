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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.paymentTerms.PaymentTermsDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.PaymentTerms;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/PaymentTerms")
public class PaymentTermsResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentTermsDao paymentTermsDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNum}/{pageSize}/{registationId}")
	public @ResponseBody Response getdata(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize, @PathParam("registationId") Integer registationId) {
		this.logger.info("list()");
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}

		List<PaymentTerms> add = this.paymentTermsDao.findAll(startingRow, pagesize, registationId);
		if (add == null) {
			return new Response(ExceptionMessage.DataIsEmpty, add);
		}
		totalRecords = paymentTermsDao.findAll(registationId).size();
		Integer totalPages = 1;
		if (totalRecords == 0) {
			response = new Response();
			response.setTotalPages(0);
		} else {
			totalPages = totalRecords / pagesize;

			totalPages = (totalRecords % pagesize > 0) ? totalPages + 1 : totalPages;

		}

		response = new Response(ExceptionMessage.StatusSuccess, add);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{registationId}")
	public @ResponseBody Response read(@PathParam("id") Long id, @PathParam("registationId") Integer registationId) {
		this.logger.info("read(id)");
		if (id == null || registationId == null) {
			return new Response(ExceptionMessage.Bad_Request, "RegistationId or Id missing");

		}
		PaymentTerms paymentTerms = this.paymentTermsDao.find(id, registationId);
		if (paymentTerms == null || paymentTerms.getPaymentType() == null || paymentTerms.getPaymentType().isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, paymentTerms);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(PaymentTerms paymentTerms) {

		if (paymentTerms != null && !paymentTerms.getPaymentType().trim().isEmpty() && paymentTerms.getId() == null
				&& paymentTerms.getRegistationId() != null) {
			try {
				paymentTerms = this.paymentTermsDao.save(paymentTerms);
				return new Response(ExceptionMessage.StatusSuccess, paymentTerms);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}

		} else {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/{registationId}")
	public @ResponseBody Response update(@PathParam("id") Long id, @PathParam("registationId") Integer registationId,
			PaymentTerms paymentTerms) {
		if (id == null || registationId == null)
			return new Response(ExceptionMessage.Bad_Request, "id or registationId missing ");
		paymentTerms.setId(id);
		paymentTerms.setRegistationId(registationId);
		this.logger.info("update(): " + paymentTerms);
		if (paymentTerms == null || paymentTerms.getPaymentType().trim().isEmpty()
				|| paymentTerms.getRegistationId() == null || paymentTerms.getId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				this.paymentTermsDao.save(paymentTerms);

				return new Response(ExceptionMessage.StatusSuccess, paymentTerms);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id, PaymentTerms paymentTerms) {
		paymentTerms.setId(id);
		this.logger.info("read(id)");

		this.paymentTermsDao.delete(id);
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
