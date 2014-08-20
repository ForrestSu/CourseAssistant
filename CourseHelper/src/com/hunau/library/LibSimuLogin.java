package com.hunau.library;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.hunau.coursehelper.HUNAU;






public class LibSimuLogin {

	/**
	         LibSimuLogin l = new LibSimuLogin();
			 InputStream in=l.login("201141842121"); 
			 ParseHtmlLib ph=new ParseHtmlLib();
			 ph.AanalysisHtml(in,HUNAU.url_lib_borrow);
	*/
	    private String cookieVal;
		public InputStream login(String name) throws Exception {
			URL url = new URL(HUNAU.url_lib_login);
		    HttpURLConnection con= (HttpURLConnection) url.openConnection();
		    con.setDoOutput(true);
		    con.setRequestMethod("POST");
		    String info="dztm="+name+"&dzmm=0000&x=30&y=16";
		    OutputStream out = con.getOutputStream();
			out.write(info.getBytes("GBK"));  
			out.close(); 
			con.connect();
			
			
			cookieVal = con.getHeaderField("Set-Cookie"); 
			if(cookieVal==null){return null;}
			 InputStream in =con.getInputStream();
			 return in;	  
			  /*BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK")); 
			String line = br.readLine();
			while(line != null){
			  System.out.println(line);
			  line = br.readLine();
			}*/
	    }
		//缁�鍥句功 
		public boolean borrow(String name,String bookcode)
		{
		   try{	
			    URL url = new URL(HUNAU.url_lib_borrow);
				HttpURLConnection con= (HttpURLConnection) url.openConnection();
				con.setRequestProperty("Cookie", cookieVal); 
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				String xj="dztm="+name+"&mm=0000&chk1="+bookcode;
				OutputStream out = con.getOutputStream();
				out.write(xj.getBytes("GBK"));
				out.close(); 
				con.connect();
				//涓嬮潰杩欏彞蹇呴』鍐欙紝涓嶇劧涓嶄細鎴愬姛
				con.getInputStream();
				return true;
		   }catch (Exception e) {
			    return false;
		   }
			
		}
	
}
