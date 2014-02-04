package textProcessor;

public class Encoder {
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
}
