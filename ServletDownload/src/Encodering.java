import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Encoder;

public class Encodering {

	public static void main(String[] args) {

	}
	
	public static String base64EncodeFileName(String filename) throws UnsupportedEncodingException {
		BASE64Encoder base64Encoder=new BASE64Encoder();
		return "=?UTF-8?B?"+new String(base64Encoder.encode(filename.getBytes("UTF-8")))+"?=";

	}

}
