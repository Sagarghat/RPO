package com.ojas.rpo.security.entity;

import java.util.List;

public class ClientMapper {

	private Client client;

	private List<AddressDetails> addressDetails;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<AddressDetails> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetails> addressDetails) {
		this.addressDetails = addressDetails;
	}
}

/*
 * //new filds private Long id; private String industry; private String about;
 * private String source; private String website; private String billingStreet;
 * private String billingCity; private String billingStatel; private String
 * biilingCode; private String billingCountry; private String shipStreet;
 * private String shipCity; private String shipState; private String shipCode;
 * private String shipCountry;
 */
