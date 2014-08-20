package com.hunau.util_course;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hunau.coursehelper.HUNAU;


public class SimuLogin{

	/**调用方法：
	        String name="201141842121";
	        String pass="123456";
			 SimuLogin l = new SimuLogin();
	         l.login(name,pass); //账号和密码
		
	*/
	   public String cookieVal=null;
	   public void login(String name ,String pass)throws Exception 
	   {
		    URL url = new URL(HUNAU.url_course_login);
			//System.out.println("sunquan2");
		    HttpURLConnection con= (HttpURLConnection) url.openConnection();
		    con.setDoOutput(true);
		    con.setRequestMethod("POST");
		    //con.setConnectTimeout(6* 1000);
		    // 别超过6秒
		    String info="userName="+name+"&userPass="+pass;
		    
		    OutputStream out = con.getOutputStream();
			out.write(info.getBytes("GBK"));
			out.close(); 		
			con.connect();
		   cookieVal = con.getHeaderField("Set-Cookie"); 
		   con.disconnect();
	   }
	   public InputStream getPage(String[] args) throws Exception {
			URL url = new URL(args[0]); 
			 HttpURLConnection second_con = (HttpURLConnection) url.openConnection();  
			if (cookieVal != null) {   
					second_con.setRequestProperty("Cookie", cookieVal);  
				} else {return null;}
			second_con.connect(); 
			cookieVal = second_con.getHeaderField("Set-Cookie"); 
			//断开连接
			second_con.disconnect();
			URL url2 = new URL(args[1]); 
			  HttpURLConnection three_con = (HttpURLConnection) url2.openConnection();  
				if (cookieVal != null) {  
					three_con.setRequestProperty("Cookie", cookieVal);  
				} else {return null;  } 
			 three_con.connect();
			 InputStream in = three_con.getInputStream();  
			 
			 return in;	
	    }
	   public InputStream getRank(String info) throws Exception
	    {
		    
	    	URL url = new URL(HUNAU.url_grade_rank);
		    HttpURLConnection con= (HttpURLConnection) url.openConnection();
		    if (cookieVal != null) {  
				con.setRequestProperty("Cookie", cookieVal);  
			} else {return null;  } 
		    con.setDoOutput(true);
		    con.setRequestMethod("POST");
		    OutputStream out = con.getOutputStream();
			out.write(info.getBytes("GBK"));
			out.close(); 		
			con.connect();	
		    InputStream in = con.getInputStream();  
			return in;	
	    }
	
}
