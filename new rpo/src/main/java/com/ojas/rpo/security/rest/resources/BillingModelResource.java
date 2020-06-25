package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.hibernate.jpa.criteria.expression.function.TrimFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.billingModel.BillingModelDao;
import com.ojas.rpo.security.entity.BillingModel;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Path("/BillingModel")
public class BillingModelResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BillingModelDao billingModelDao;
	/*
	 * @Autowired private LocationDao locationDao;
	 */

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getdata/{pageNo}/{pageSize}")
	public @ResponseBody Response getdata(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize)
			throws IOException {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		List<BillingModel> add = this.billingModelDao.findAllData(startingRow, pageSize);
		if (add == null || add.isEmpty()) {
			response = new Response(ExceptionMessage.DataIsEmpty);
		}
		totalRecords = billingModelDao.findAll().size();
		Integer totalPages = 1;
		if (totalRecords == 0) {
			response = new Response();
			response.setTotalPages(0);
		} else {
			totalPages = totalRecords / pageSize;
			totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;
		}
		response = new Response(ExceptionMessage.StatusSuccess);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setResult(add);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		BillingModel billingModel = this.billingModelDao.find(id);
		if (billingModel == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, billingModel);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(BillingModel billingModel) {
		System.out.println("==========inside post method====RPO");

		if (billingModel != null && !billingModel.getBillingModel().trim().isEmpty()) {
			try {
				billingModel = this.billingModelDao.save(billingModel);
				return new Response(ExceptionMessage.StatusSuccess, billingModel);
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
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, BillingModel billingModel) {
		billingModel.setId(id);
		this.logger.info("update(): " + billingModel);
		if (billingModel == null || billingModel.getBillingModel().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				billingModel = this.billingModelDao.save(billingModel);
				return new Response(ExceptionMessage.StatusSuccess, billingModel);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public @ResponseBody Response delete(@PathParam("id") Long id) {
		BillingModel billingModel = this.billingModelDao.find(id);
		if (billingModel != null) {
			int i = this.billingModelDao.deleteById(id);
			if (i == 0)
				return new Response(ExceptionMessage.Exception);
			else
				return new Response(ExceptionMessage.StatusSuccess, billingModel);
		} else
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);

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
