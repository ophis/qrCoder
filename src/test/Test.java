package test;

import qrCodeBase.QRCoder;

public class Test {
	public static void main(String[] args){
		QRCoder qrCoder = new QRCoder();
		char [] c = {'a','b','c'};
		String teString = new String(c);
		String contentString = "abc";
		qrCoder.generateQR(contentString);
		System.out.println(teString);
		System.out.println(qrCoder.readQR("C:\\Users\\Frank\\Desktop\\unitag_qrcode_1391453126140.png"));
	}
}
