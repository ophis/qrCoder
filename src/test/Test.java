package test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Hashtable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;

import qrCodeBase.QRCoder;
import textProcessor.Encryptor;

public class Test {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, JSONException{
		QRCoder qrCoder = new QRCoder();
		String contentString = "ophis";
		qrCoder.generateQR(contentString);
		String secret = Encryptor.encrypt("hello", "ophis");
		//ncryptor.binary2byte("1101101,1000100,11111111111111111111111111000000,111010,11001,11111111111111111111111111011101,1110001,1001001".getBytes());
		System.out.println(secret);
//		String blankString = Encoder.toBlankString(secret);
//		System.out.println(blankString);
//		System.out.println(Encoder.toHexString(blankString));
		System.out.println(Encryptor.decrypt(secret, "ophis"));
		String result = Encryptor.formString("baidu", "google", "ophis");
		System.out.println(result);
		Hashtable<Integer, String> ret = Encryptor.analyse(result, "ophis");
		System.out.println("Public:"+ret.get(0)+"Private:"+ret.get(1));
	}
}
