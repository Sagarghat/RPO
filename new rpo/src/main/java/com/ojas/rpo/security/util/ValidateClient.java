package com.ojas.rpo.security.util;

import org.springframework.http.HttpStatus;

import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.Response;

public class ValidateClient {
	
	public Response validateClient(Client client) {

		if (client.getClientName() == null || client.getClientName().trim().isEmpty()) {
			return new Response(HttpStatus.CONFLICT, "Client Name Must require");
		} else if (client.getEmail() == null) {
			return new Response(HttpStatus.CONFLICT, "Client Email Must require");
		} else if (client.getTdspercentage() == null) {
			return new Response(HttpStatus.CONFLICT, "Client Tdspercentage Must require");
		} else if (client.getStartDate() == null) {
			return new Response(HttpStatus.CONFLICT, "Client Start Date Must require");
		} else if (client.getLeavesAllowed() == null) {
			return new Response(HttpStatus.CONFLICT, "Client Leaves Must require");
		} else if (client.getAccountManagerId() == null) {
			return new Response(HttpStatus.CONFLICT, "Client Account Manager Id Must require");
		} else if (client.getLeadId() == null) {
			return new Response(HttpStatus.CONFLICT, "client Lead Id Must require");
		}				
		else if (client.getBillingModel() == null) {
			return new Response(HttpStatus.CONFLICT, "Billing Mode must require");
		} else if (client.getPaymentTerms() == null) {
			return new Response(HttpStatus.CONFLICT, "PaymentTerms must require");
		} else if (client.getServices() == null || client.getServices().isEmpty()) {
			return new Response(HttpStatus.CONFLICT, "Services must require");
		} else if (client.getCustomerType() == null) {
			return new Response(HttpStatus.CONFLICT, "Customer Type Must require");
		}
		
		return null;
	}

}
