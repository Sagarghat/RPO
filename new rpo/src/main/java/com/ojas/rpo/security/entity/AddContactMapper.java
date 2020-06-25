package com.ojas.rpo.security.entity;

import java.util.List;

public class AddContactMapper {

	private AddContact contact;
	private List<ContactAddressDetails> address;

	public AddContactMapper() {
		super();
	}

	public AddContact getContact() {
		return contact;
	}

	public void setContact(AddContact contact) {
		this.contact = contact;
	}

	public List<ContactAddressDetails> getAddress() {
		return address;
	}

	public void setAddress(List<ContactAddressDetails> address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ContactMapper [contact=" + contact + ", address=" + address + "]";
	}

}
