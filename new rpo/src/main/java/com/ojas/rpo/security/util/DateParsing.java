package com.ojas.rpo.security.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateParsing {
	
	public static String dateParsing(Date date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		//System.out.println("D1"+dat);
		String s=formatter.format(date).toString();
		
		return s;
	}

	public static int timeDiff(Date createdDate,Date endDate) {
		
		return endDate.getDay()-createdDate.getDay();
		
		
		
	}
	
	
	
	
	public static String sqlDateconverter(Date d) {
		SimpleDateFormat sd= new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			
		String format = sd.format(d);
		return format;
		
	}
	
	public static String textConvertor(String str) {
		   // Create a char array of given String 
        char ch[] = str.toCharArray(); 
        for (int i = 0; i < str.length(); i++) { 
  
            // If first character of a word is found 
            if (i == 0 && ch[i] != ' ' ||  
                ch[i] != ' ' && ch[i - 1] == ' ') { 
  
                // If it is in lower-case 
                if (ch[i] >= 'a' && ch[i] <= 'z') { 
  
                    // Convert into Upper-case 
                    ch[i] = (char)(ch[i] - 'a' + 'A'); 
                } 
            } 
  
            // If apart from first character 
            // Any one is in Upper-case 
            else if (ch[i] >= 'A' && ch[i] <= 'Z')  
  
                // Convert into Lower-Case 
                ch[i] = (char)(ch[i] + 'a' - 'A');             
        } 
  
        // Convert the char array to equivalent String 
        String st = new String(ch); 
        return st;
	}
	
	
	public static Date createDate(String date, String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 date = date + time;
		 Date d= null;
		try {
			d= sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return d;
	}
}
