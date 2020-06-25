package com.ojas.rpo.security.rest.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.AccessToken;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.entity.UserDetailsList;
import com.ojas.rpo.security.service.UserService;
import com.ojas.rpo.security.transfer.uesrAlpha;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Component
@Path("/user")
public class UserResource {
	@Autowired
	private UserService userService;
	// private DaoUserService daoUserService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	@Autowired
	private UserDao userDao;

	/**
	 * Retrieves the currently logged in user.
	 *
	 * @return A transfer containing the username and the roles.
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public uesrAlpha getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			throw new WebApplicationException(200);
		}
		User userDetails = (User) principal;

		return new uesrAlpha(userDetails.getUsername(), userDetails.getRole());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserById/{id}")
	public uesrAlpha getUserById(@PathParam("id") Long id) {

		User user = userDao.find(id);

		return new uesrAlpha(user.getUsername(), user.getRole());
	}

	/**
	 * Authenticates a user and creates an access token.
	 *
	 * @param username The name of the user.
	 * @param password The password of the user.
	 * @return The generated access token.
	 */

	@Path("/authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AccessToken authenticate(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("domain") String domain) {
		Object principal = null;		 
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);		
	//	System.out.println("authenticate>>>>>>>>>>>>>>>> getPrincipal ::: "+authenticationToken.isAuthenticated());
		
		
//		if(authenticationToken.getName().isEmpty()) {
//			return new AccessToken(new User(), "Bad credentials");
//		}
		AccessToken token = null;
		
		
		try {		
			
			Authentication authentication = this.authManager.authenticate(authenticationToken);			
			SecurityContextHolder.getContext().setAuthentication(authentication);			
			principal = authentication.getPrincipal();				
			if (!(principal instanceof User)) {
				throw new WebApplicationException(401);
			}
			
			token = this.userService.createAccessToken((User) principal);			
			if (!token.getUser().getDomin().equals(domain)) {				
				token = null;
				//throw new WebApplicationException(406);
			}

		} catch (Exception e) {
			
			//System.out.println("authenticate>>>>>>>>>>>>>>>> Exception"+e.getMessage());
		}
		finally {
			if(token != null) {
				return token;
			}else {
				return new AccessToken(new User(), "Bad credentials");
			}
			
		}
		//return token;

		// System.out.println("authentication isAuthenticated boolean :"+
		// authentication.isAuthenticated());

	}

	private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{regId}")
	public @ResponseBody Response read(@PathParam("id") Long id,@PathParam("regId") Long regId) {
		User result = new User();
		User find = this.userDao.find(id);
		if(find.getRegistrationId() == regId) {
			result=find;
		}
		System.out.println("Inside getUser service");
		if (find == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, result);
	}

}
