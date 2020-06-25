package com.ojas.rpo.security.util;

import java.io.IOException;
import java.util.Properties;

public class ReadingAppProps {

	public String get() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  props.getProperty("excelFiles");
	}
}
