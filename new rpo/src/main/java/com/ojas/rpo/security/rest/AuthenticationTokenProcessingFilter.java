package com.ojas.rpo.security.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.service.UserService;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean  {

	String restUrl;

	private final UserService userService;
	// DaoUserService daoUserService =new DaoUserService(null, null);

	public AuthenticationTokenProcessingFilter(UserService userService) {
		this.userService = userService;
	}

	public boolean isRestCall(String url) {
		if (restUrl.contains("/rest")) {
			return true;
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);
		String requestURI = httpRequest.getRequestURI();
		System.out.println("queryString" + httpRequest.getQueryString());
		restUrl = requestURI;
		//String xyz = requestURI;
		System.out.println("call" + requestURI);
		if (isRestCall(requestURI)) {
			if (requestURI.contains("/authenticate") || requestURI.contains("/user")
					|| requestURI.contains("/companyreg/registration") || requestURI.contains("/registration")
					|| requestURI.contains("/activate") || requestURI.contains("/plans")) {

				System.out.println("-->>;;;;;;;;;;;;;;;;;;;;;>preHandle");
				
				((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
		        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
		        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
		        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Cache-Control,  Origin, Authorization, Content-Type,  Accept");
		        
	 
		        HttpServletResponse resp = (HttpServletResponse) response;
		 
		        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
		        if (httpRequest.getMethod().equals("OPTIONS")) {
		            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		            return;
		        }
				chain.doFilter(request, resp);

			} else {
				
				((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
		        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
		        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
		        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With,X-Access-Token, Content-Type,  Accept");
		     
		        HttpServletResponse resp = (HttpServletResponse) response;
		 
		        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
		        if (httpRequest.getMethod().equals("OPTIONS")) {
		            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		            return;
		        }
				
				// HttpServletRequest httpRequest = this.getAsHttpRequest(request);
				String accessToken = this.extractAuthTokenFromRequest(httpRequest);
				System.out.println("TOKEN   :" + accessToken);

				if (null != accessToken) {
					User user = this.userService.findUserByAccessToken(accessToken);
					System.out.println("TOKEN RELATED USER   :" + user);

					if (null != user) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								user, null, user.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);						

						chain.doFilter(request, resp);
					}
				}

			}

		}
		// chain.doFilter(request, response);
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Access-Token");

		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}
