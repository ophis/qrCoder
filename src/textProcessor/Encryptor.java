package textProcessor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Hashtable;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	public static String byte2hex(byte[] b) { // 一个字节的数， 
		// 转成16进制字符串 
		String hs = ""; 
		String stmp = ""; 
		for (int n = 0; n < b.length; n++) { 
		// 整数转成十六进制表示 
		stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
		if (stmp.length() == 1)
		hs = hs + "0" + stmp; 
		else 
		hs = hs + stmp; 
		}
		return hs.toUpperCase(); // 转成大写 
		} 

	public static byte[] hex2byte(byte[] b) { 
		if ((b.length % 2) != 0) 
		throw new IllegalArgumentException("长度不是偶数"); 
		byte[] b2 = new byte[b.length / 2]; 
		for (int n = 0; n < b.length; n += 2) { 
		String item = new String(b, n, 2);
		b2[n / 2] = (byte) Integer.parseInt(item, 16); 
		} 
		return b2; 
		} 

	public static String encrypt(String strMing, String key) { 
		String strMi = ""; 
		try { 
		return byte2hex(encrypt(strMing.getBytes(),key)); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		}
		return strMi; 
	}
	
	public static String decrypt(String strMi,String key) {
		String strMing = ""; 
		try { 
			return new String(decrypt(hex2byte(strMi.getBytes()),key));  
		} catch (Exception e) {
		e.printStackTrace(); 
		}
		return strMing; 
	} 
	
	public static byte[] encrypt(byte[] plaintext, String key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		key = Integer.toString(Math.abs(key.hashCode()));
		while(key.length()<8){
			key=key.concat(key);
		}
		key = key.substring(0, 8);
		//System.out.println(key);
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
//		System.out.println(secretKey.hashCode());
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] ciphertext = cipher.doFinal(plaintext);
		return ciphertext;
	}
	
	public static byte[] decrypt(byte[] ciphertext, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException{
		key = Integer.toString(Math.abs(key.hashCode()));
		while(key.length()<8){
			key=key.concat(key);
		}
		key = key.substring(0, 8);
		//System.out.println(key);
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
		//System.out.println(secretKey.hashCode());
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plaintext="".getBytes();
		try {
			plaintext = cipher.doFinal(ciphertext);
			return plaintext;
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return plaintext;
	}
	
	public static String formString(String publicString, String privateString, String key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException{
		if(publicString.length()>140)
			publicString="";
		if(privateString.length()>140)
			privateString="";
		String cipherPrivateString = encrypt(privateString, key).toString();
		String result = publicString+"/"+cipherPrivateString;
		result = encrypt(result, "LVLVLVLV");
		return result;
	}
	
	public static Hashtable<Integer, String> analyse(String formedString, String key){
		formedString = decrypt(formedString, "LVLVLVLV");
		String[] stringarray = formedString.split("/");
		String publicString = stringarray[0];
		String privateString = stringarray[1];
		if (key=="") {
			privateString="";
		}
		else {
			privateString = decrypt(privateString, key);
		}
		Hashtable<Integer, String> result = new Hashtable<>();
		result.put(0, publicString);
		result.put(1, privateString);
		return result;
	}
}
