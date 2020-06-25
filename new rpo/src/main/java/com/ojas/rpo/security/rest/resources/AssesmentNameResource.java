package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.assesmentname.AssesmentNameDao;
import com.ojas.rpo.security.entity.AssesmentName;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/assesmentNameResource")
public class AssesmentNameResource {

	
	@Autowired
    private AssesmentNameDao assesmentNameDao;
    
    
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public @ResponseBody  Response create(AssesmentName list){
        	list.setAssesmentName(list.getAssesmentName().trim());
        	if(list.getRegistrationId()==null|| list.getAssesmentName().isEmpty()) {
        		return new Response(ExceptionMessage.DataIsEmpty);
        	}
        	if(assesmentNameDao.check(list)) {
        		AssesmentName save = assesmentNameDao.save(list);
        		return new Response(ExceptionMessage.StatusSuccess, save);
        	}
        	else return new Response(ExceptionMessage.DuplicateRecord);
        	        	
        } 
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("{id}/{regId}")
        public  @ResponseBody  Response read(@PathParam("id") Long id , @PathParam("regId") Long regId) {
        
            AssesmentName list = this.assesmentNameDao.getById(id, regId);

            if (list == null) {
                return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
            }
            else
            return new Response(ExceptionMessage.StatusSuccess,list);
        }
        
        @GET
        @Path("/findAll/{pageNo}/{pageSize}/{regId}")
        @Produces(MediaType.APPLICATION_JSON)
        @ResponseBody
        public Response getAll(@PathParam("pageNo") Integer  pageNo , @PathParam("pageSize") Integer pageSize ,
        		@PathParam("regId") Long regId) {
			return assesmentNameDao.getAll(pageNo, pageSize, regId);
        	 
        }  
        
   
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, AssesmentName assesmentName) {
		System.out.println("==========inside post method====RPO");
		assesmentName.setId(id);
		Response response = null;
		if (assesmentName.getId() == null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid customer id");
		}
		if (this.assesmentNameDao.save(assesmentName) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else {
			response = new Response(ExceptionMessage.StatusSuccess, assesmentName);
			response.setRes("Assessment Details Updated Successfully ");
		}
		return response;
	}
}
