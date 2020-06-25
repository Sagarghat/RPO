package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.location.SkillDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Skill;
import com.ojas.rpo.security.util.DateParsing;

@Component
@Path("/skill")
public class SkillResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SkillDao skillDao;
	/*
	 * @Autowired private LocationDao locationDao;
	 */

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAll/{pageNum}/{pageSize}/{regId}")
	public @ResponseBody Response getdata(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize, @PathParam("regId") Long regId) throws IOException {

		return this.skillDao.findAllSkills(pageNum, pagesize, regId);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{regId}")
	public @ResponseBody Response read(@PathParam("id") Long id, @PathParam("regId") Long regId) {
		this.logger.info("read(id)");
		List<Skill> skill = this.skillDao.find(id,regId);
		if (skill.isEmpty()) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, skill.get(0));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Skill skill) {
		try {
			String s = skill.getSkillName();
			String fs = DateParsing.textConvertor(s);
			skill.setSkillName(fs);
			if (skill != null && !skill.getSkillName().trim().isEmpty()) {
				skill = this.skillDao.save(skill);
				return new Response(ExceptionMessage.StatusSuccess, skill);
			} else
				return new Response(ExceptionMessage.DataIsEmpty);
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Skill skill) {
		skill.setId(id);
		this.logger.info("update(): " + skill);

		if (skill == null || skill.getSkillName().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		try {
			skill = this.skillDao.save(skill);
			return new Response(ExceptionMessage.StatusSuccess, skill);
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord);
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
