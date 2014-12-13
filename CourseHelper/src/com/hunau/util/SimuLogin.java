package com.hunau.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SimuLogin {

	public String cookieVal = null;


	/** 访问网络
	 * @param urladdress  url
	 * @param sendmsg     发送的消息，为空则不发
	 * @param isSaveCookie  是否保存cookie
	 * @return  返回InputStream
	 */
	public String login(String urladdress,String sendmsg,boolean isSaveCookie) {
		try {
			 
			URL url = new URL(urladdress);
		    HttpURLConnection con= (HttpURLConnection) url.openConnection();
		    if(cookieVal!=null)con.setRequestProperty("Cookie", cookieVal);
		    con.setRequestProperty("User-agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E; Shuame)");
		    // 别超过5秒
		    con.setConnectTimeout(5 * 1000);
		    con.setReadTimeout(5 * 1000);
		    OutputStream out=null;
		    if(sendmsg!=null)
		    {
		    	con.setDoOutput(true);
		    	out = con.getOutputStream();
		    	byte[] b=sendmsg.getBytes("GBK");
				out.write(b);
				out.close();
		    }
		    con.connect();
		    if(isSaveCookie)cookieVal = con.getHeaderField("Set-Cookie");
		    return getContent(con.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//获取InputStream 内容
	public String getContent(InputStream in) {
		String line, ret;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));
			ret ="";
			line = br.readLine();
			while (line != null) {
				ret += line;
				line = br.readLine();
			}
			if (in != null)in.close();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean isLoginFail(String html,String tag)
	{   if(html==null)return false;//说明不是账号或者密码错
		return html.contains(tag);
	}
}
