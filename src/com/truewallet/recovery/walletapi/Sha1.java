package com.truewallet.recovery.walletapi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {

	public static String hash(String input) throws NoSuchAlgorithmException {
	    byte[] result = MessageDigest.getInstance("SHA1").digest(input.getBytes());
	    StringBuffer sb = new StringBuffer();
	    for (byte b : result) {
	        sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
	    }
	    return sb.toString();
	}
	
}
