package com.truewallet.recovery.walletapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import com.truewallet.recovery.json.JSONObject;

public class WalletAPI {

	public static String GetToken(String username, String password) throws IOException {
    	String passhash = null;
        try {
            passhash = Sha1.hash(new StringBuilder(String.valueOf(username)).append(password).toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    	Map<String, String> params = new LinkedHashMap<String, String>();
        JSONObject data = new JSONObject();
        data.put("username", (Object) username);
        data.put("password", (Object) passhash.toString());
        data.put("type", (Object) "email");
        StringBuilder postData = new StringBuilder();
        for (Entry<?, ?> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode((String) param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        HttpsURLConnection conn = (HttpsURLConnection) new URL(APIURL.api_host + APIURL.api_endpoint_signin).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "mobile-api-gateway.truemoney.com");
        conn.setRequestProperty("X-Requested-With", "okhttp/3.8.0");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null) {
                in.close();
                wr.close();
                return new JSONObject(sb.toString()).getJSONObject("data").getString("accessToken");
            }
            sb.append(line);
        }
        
    }
	
    public static String GetProfile(String token) throws IOException {
        return WalletAPIRequest.get(APIURL.api_host + APIURL.api_endpoint_profile + token);
    }
    
    public static String GetCurrentBalance(String token) throws IOException {
        return WalletAPIRequest.get(APIURL.api_host + APIURL.api_endpoint_currentbalance + token);
    }
    
    public static String GetTransaction(String token, String start, String end, int limit) throws IOException {
        return WalletAPIRequest.get(APIURL.api_host + APIURL.api_endpoint_gettran + token + "/?startDate=" + start + "&endDate=" + end + "&limit=" + limit + "&page=1&type=&action=");
    }

    public static String GetReport(String token, String id) throws IOException {
        return WalletAPIRequest.get(APIURL.api_host + APIURL.api_endpoint_getreport + id + "/detail/" + token);
    }
    
    public static String Topup(String token, String cashcard) throws IOException {
    	return WalletAPIRequest.post(APIURL.api_host + APIURL.api_endpoint_topup + Instant.now().getEpochSecond() + "/" + token + "/cashcard/" + cashcard);
    }
    
}
