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


	/** ��һ�η�������
	 * @param urladdress  url
	 * @param sendmsg
	 */
	public void login(String urladdress, String sendmsg)  {
		URL url;
		try {
			url = new URL(urladdress);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			// �𳬹�6��
			con.setConnectTimeout(6 * 1000);
			OutputStream out = con.getOutputStream();
			out.write(sendmsg.getBytes("GBK"));
			out.close();
			con.connect();
			cookieVal = con.getHeaderField("Set-Cookie");
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**�ڶ��η�������
	 * @param urladdress  url
	 * @param sendmsg     ���͵���Ϣ��Ϊ���򲻷�
	 * @param isSaveCookie  �Ƿ񱣴�cookie
	 * @return  ����InputStream
	 */
	public InputStream login(String urladdress,String sendmsg,boolean isSaveCookie) {
		try {
			if(cookieVal==null){return null;}
			URL url = new URL(urladdress);
		    HttpURLConnection con= (HttpURLConnection) url.openConnection();
		    con.setRequestProperty("Cookie", cookieVal);
		    // �𳬹�5��
		    con.setConnectTimeout(5 * 1000);
		    OutputStream out=null;
		    if(sendmsg!=null)
		    {
		    	con.setDoOutput(true);
		    	out = con.getOutputStream();
				out.write(sendmsg.getBytes("GBK"));
				out.close();
		    }
		    con.connect();
		    if(isSaveCookie)cookieVal = con.getHeaderField("Set-Cookie");
			InputStream in =con.getInputStream();
		    return in;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//��ȡInputStream ����
	public String getContent(InputStream in) {
		String line, ret;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));
			line = br.readLine();
			ret = line;
			while (line != null) {
				line = br.readLine();
				ret += line;
			}
			if (in != null)
				in.close();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
