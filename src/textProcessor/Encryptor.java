package textProcessor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Hashtable;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	private static String defaultKeyString = "LV";

	public static String byte2binary(byte[] b){
		String hs = "";
		String stmp = "";
		for(int n=0; n<b.length;n++){
			stmp = (java.lang.Integer.toBinaryString(b[n]));
			if (stmp.length() < 8){
				while(stmp.length()<8)
					stmp = "0" + stmp;
				hs = hs + stmp;	
			}
			else
				hs = hs + stmp;
			System.out.println(stmp);
		}
		return hs;
	}
	
	public static byte[] binary2byte(byte[] b) {
		if ((b.length % 8) != 0)
			throw new IllegalArgumentException("Not a valid length");
		byte[] b2 = new byte[b.length / 8];
		for (int n = 0; n < b.length; n += 8) {
			String item = new String(b, n, 8);
			b2[n / 8] = (byte) Integer.parseInt(item, 2);
		}
		return b2;
	}
	
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("Not a even length");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	private static SecretKey generateKey(String nounce) {
		nounce = Integer.toString(Math.abs(nounce.hashCode()));
		while (nounce.length() < 8) {
			nounce = nounce.concat(nounce);
		}
		nounce = nounce.substring(0, 8);
		// System.out.println(key);
		SecretKey secretKey = new SecretKeySpec(nounce.getBytes(), "DES");
		return secretKey;
	}

	public static String encrypt(String plaintextString, String key) {
		String ciphertextString = "";
		try {
			return byte2hex(encrypt(plaintextString.getBytes(), key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ciphertextString;
	}

	public static String decrypt(String ciphertextString, String key) {
		String plaintextString = "";
		try {
			return new String(decrypt(hex2byte(ciphertextString.getBytes()),
					key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plaintextString;
	}

	public static byte[] encrypt(byte[] plaintext, String key)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		SecretKey secretKey = generateKey(key);// new
												// SecretKeySpec(key.getBytes(),
												// "DES");
		// System.out.println(secretKey.hashCode());
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] ciphertext = cipher.doFinal(plaintext);
		return ciphertext;
	}

	public static byte[] decrypt(byte[] ciphertext, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException {
		SecretKey secretKey = generateKey(key);// new
												// SecretKeySpec(key.getBytes(),
												// "DES");
		// System.out.println(secretKey.hashCode());
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plaintext = "".getBytes();
		try {
			plaintext = cipher.doFinal(ciphertext);
			return plaintext;
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return plaintext;
	}

	public static String formString(String publicString, String privateString,
			String key) throws NoSuchAlgorithmException,
			NoSuchProviderException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException {
		if (publicString.length() > 140)
			publicString = "";
		if (privateString.length() > 140)
			privateString = "";
		String cipherPrivateString = encrypt(privateString, key).toString();
		String result = publicString + "/" + cipherPrivateString;
		result = encrypt(result, defaultKeyString);
		return result;
	}

	public static Hashtable<Integer, String> analyse(String formedString,
			String key) {
		formedString = decrypt(formedString, defaultKeyString);
		String[] stringarray = formedString.split("/");
		String publicString = stringarray[0];
		String privateString = stringarray[1];
		if (key == "") {
			privateString = "";
		} else {
			privateString = decrypt(privateString, key);
		}
		Hashtable<Integer, String> result = new Hashtable<>();
		result.put(0, publicString);
		result.put(1, privateString);
		return result;
	}
}
