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
import javax.ws.rs.WebApplicationException;
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
import com.ojas.rpo.security.dao.slab.SlabDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Skill;
import com.ojas.rpo.security.entity.Slab;

@Component
@Path("/slab")
public class SlabResource
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SlabDao slabDao;
 

    @Autowired
    private ObjectMapper mapper;

  
 	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response getdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Slab> add=this.slabDao.findAll();
		if (add == null) {
			//throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		else 
			
			if(add.size()>0){
				return new Response(ExceptionMessage.StatusSuccess,add);
			}
			else{
			
				return new Response(ExceptionMessage.DataIsEmpty,"200","NOEXCEPTION","NULL");
			}	
	}
  
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public  @ResponseBody  Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
	     Slab slab = this.slabDao.find(id);
		if (slab == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,slab);
	}
    
    @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody  Response create(Slab slab){
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + slab);

		if ( this.slabDao.save(slab) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,slab);
	}
	
 
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
	public @ResponseBody  Response  update(@PathParam("id") Long id, Slab slab){
		System.out.println("==========inside post method====RPO");
		slab.setId(id);
		this.logger.info("update(): " + slab);
		

		if ( this.slabDao.save(slab) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		else
		return new Response(ExceptionMessage.StatusSuccess,slab);
	}

    private boolean isAdmin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getAuthorities().contains(Role.ADMIN);
    }
}
