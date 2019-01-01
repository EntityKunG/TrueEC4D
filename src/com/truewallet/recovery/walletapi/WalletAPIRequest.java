package com.truewallet.recovery.walletapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class WalletAPIRequest {

	public static String get(String url) throws IOException {
	HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Host", "mobile-api-gateway.truemoney.com");
	conn.setRequestProperty("X-Requested-With", "okhttp/3.8.0");
        conn.setDoOutput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null) {
            	in.close();
                return sb.toString();
            }
            sb.append(line);
        }
	}
	
	public static String post(String url) throws IOException {
	   HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
           conn.setRequestMethod("POST");
           conn.setRequestProperty("Host", "mobile-api-gateway.truemoney.com");
           conn.setRequestProperty("X-Requested-With", "okhttp/3.8.0");
           conn.setDoOutput(true);
           BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
           StringBuilder sb = new StringBuilder();
           for (;;) {
             String line = in.readLine();
             if (line == null) {
            	 in.close();
                 return sb.toString();
             }
             sb.append(line);
           }
	}
	
}
