package util;

import org.apache.commons.codec.binary.Base64;

public class Base64Algrt {

	/**
	 * 编码。
	 * @param btStr
	 * @return String
	 */
	public static String encode(byte[] btStr) {
		return new Base64().encodeToString(btStr);
	}

	/**
	 * 解码。
	 * @param sStr
	 * @return String
	 */
	public static byte[] decode(String sStr) {
		return new Base64().decode(sStr);
	}

}
