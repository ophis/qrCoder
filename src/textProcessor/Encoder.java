package textProcessor;

import org.json.JSONException;
import org.json.JSONObject;

public class Encoder {
	public static byte[] encodeBlank(byte[] binary){
		byte[] encoded = new byte[binary.length];
		for(int i=0;i<binary.length;i++){
			if(binary[i]=='0'){
				encoded[i]='\n';
			}
			else if (binary[i]=='1') {
				encoded[i]='\n';
			}
			else {
				throw new IllegalArgumentException("Contains none binary characters");
			}
		}
		return encoded;
	}
	
	public static byte[] decodeBlank(String encodedContent){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<encodedContent.length();i++){
			if(encodedContent.charAt(i)=='\n'){
				sb.append('0');
			}
			else if (encodedContent.charAt(i)=='\b') {
				sb.append('1');
			}
			else {
				throw new IllegalArgumentException("Contains invalid characters");
			}
		}
		return sb.toString().getBytes();
	}
	
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
	
	//convert text to blank encoded string
	public static String toBlankString(String hexString){
		StringBuilder sBuilder = new StringBuilder();
		for(int i=0;i<hexString.length();i++){
			int hexValue = Integer.parseInt(String.valueOf(hexString.charAt(i)),16)+1;
			sBuilder.append(Character.toChars(hexValue));
			//System.out.println(hexValue);
		}
		return sBuilder.toString();
	}
	
	//convert blank encoded string to visible text
	public static String toHexString(String blankString){
		StringBuilder sBuilder = new StringBuilder();
		for(int i=0;i<blankString.length();i++){
			int a = blankString.charAt(i)-1;
			sBuilder.append(Integer.toHexString(a));
			//System.out.println(hexValue);
		}
		return sBuilder.toString();
	}
	
	public static String encodeJSON(String publicString, String privateString) throws JSONException{
		String JString = "{\"PU\":\""+publicString+"\",\"PR\":\""+privateString+"\"}";
		return JString;
	}
	
	public static JSONObject decodJSON(String JString) throws JSONException{
		JSONObject jObject = new JSONObject(JString);
		return jObject;
	}
}
