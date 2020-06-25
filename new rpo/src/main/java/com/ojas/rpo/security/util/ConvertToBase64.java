package com.ojas.rpo.security.util;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class ConvertToBase64 {
	
	public static String convertToBase64(String binaryValue) {
		
	    byte[] encoded = Base64.encodeBase64(binaryValue.getBytes());


	    String encodedString = new String(encoded);
	    
	    return encodedString;
	}

}
