package test;

import qrCodeBase.QRCoder;
import textProcessor.Encryptor;

public class Test {
	public static void main(String[] args){
		QRCoder qrCoder = new QRCoder();
		String contentString = "ophis";
		qrCoder.generateQR(contentString);
		String cipherString = Encryptor.encrypt("Thisisaplaintext", "ophis");
		//System.out.println(cipherString);
		//System.out.println(cipherString.length());
		String binaryString = Encryptor.byte2binary(contentString.getBytes());
		byte[] conString = Encryptor.binary2byte(binaryString.getBytes());
		System.out.println(new String(conString));
	}
}
