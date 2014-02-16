package textProcessor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Hashtable;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

public class Encryptor {
	private static String defaultKeyString = "LV";

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

	public static byte[] encryptWithBlank(String plaintextString, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		return Encoder.encodeBlank(Encoder.byte2binary(encrypt(plaintextString.getBytes(), key)).getBytes());
	}
	
	public static String decryptWithBlank(String ciphertextString, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		byte[] binary = Encoder.decodeBlank(ciphertextString);
		byte[] plain = decrypt(Encoder.binary2byte(binary),key);
		return new String(plain);
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
			plaintextString = "Decryption error!";
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
			e.printStackTrace();
		}
		return plaintext;
	}

	public static String formString(String publicString, String privateString,
			String key) throws NoSuchAlgorithmException,
			NoSuchProviderException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, JSONException {
		if (publicString.length() > 140)
			publicString = "";
		if (privateString.length() > 140)
			privateString = "";
		String cipherPrivateString = encrypt(privateString, key).toString();
		//String result = publicString + "/" + cipherPrivateString;
		String result = Encoder.encodeJSON(publicString, cipherPrivateString);
		result = encrypt(result, defaultKeyString);
		return result;
	}

	public static Hashtable<Integer, String> analyse(String formedString,
			String key) throws JSONException {
		formedString = decrypt(formedString, defaultKeyString);
		JSONObject jObj = Encoder.decodJSON(formedString);
		String publicString = jObj.getString("PU");
		String privateString = jObj.getString("PR");
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
