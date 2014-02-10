package test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;

import qrCodeBase.QRCoder;
import textProcessor.Encoder;
import textProcessor.Encryptor;

public class Test {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		QRCoder qrCoder = new QRCoder();
		String contentString = "ophis";
		qrCoder.generateQR(contentString);
		Encryptor encryptor = new Encryptor();
		String secret = encryptor.encrypt("hello", "ophis");
		//ncryptor.binary2byte("1101101,1000100,11111111111111111111111111000000,111010,11001,11111111111111111111111111011101,1110001,1001001".getBytes());
		System.out.println(secret);
//		String blankString = Encoder.toBlankString(secret);
//		System.out.println(blankString);
//		System.out.println(Encoder.toHexString(blankString));
		System.out.println(encryptor.decrypt(secret, "ophis"));
		
	}
}
