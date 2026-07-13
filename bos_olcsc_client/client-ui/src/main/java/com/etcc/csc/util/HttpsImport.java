package com.etcc.csc.util;

  
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.*;

import org.apache.log4j.Logger;

public class HttpsImport {

	private static Logger logger = Logger.getLogger(HttpsImport.class); 
	
	public static void main(String[] args) {
		String url = "http://rchq-hctraboswcd02-vmli.etcchostedservices.local/payment/internal/";
		url += "addPAN.do?myaction=load&applicationName=CSC&rsessionId=0ac8041c22b8277c197e939640ca92df8b15aa518335";
		url += "&loggedUserName=anonymous&clientIP=10.200.10.13";
		url +="&redirectUrl=http://ddinh7.etcc.local:8888/eztagstore/";
		//url += "&token=dc66d2bd45b9f47392cb4ea3bd0cd4e3&redirectUrl=&accountId=208&clientIP=";
		String html = importUrl(url);
		System.out.println(html);
	}

	public static String importUrl(String url) {		 
		try {
			logger.debug("PCI url " + url);
			URLConnection connection = new URL(url).openConnection();
			//connection.setDoOutput(true);
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			copyStream(input, bos);
			String html = new String(bos.toByteArray());

			return html;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	 

	private static void copyStream(InputStream ins, OutputStream ops)
			throws Exception {
		byte[] buffer = new byte[5000];
		int count = -1;

		do {
			count = ins.read(buffer, 0, buffer.length);

			if (count > 0) {
				ops.write(buffer, 0, count);
			}
		} while (count > 0);

		ops.flush();
	}

}
