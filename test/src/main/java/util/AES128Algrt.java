package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES 加密，解密
 *
 * @author vlees
 *
 */
public class AES128Algrt {

	private static Logger CurLogger = LoggerFactory.getLogger(AES128Algrt.class);
	private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";// "算法/模式/补码方式"
	private static final String ivString = "0000000000000000"; // 偏移量

	/**
	 * 加密
	 *
	 * @param sSrc
	 *            加密内容
	 * @param sKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) {
		byte[] iv = ivString.getBytes();
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes();
			int length = dataBytes.length;
			// 计算需填充长度
			if (length % blockSize != 0) {
				length = length + (blockSize - (length % blockSize));
			}
			byte[] plaintext = new byte[length];
			// 填充
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			// 设置偏移量参数
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encryped = cipher.doFinal(plaintext);
			return parseByte2HexStr(encryped);
		} catch (Exception e) {
			CurLogger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 解密
	 *
	 * @param sSrc
	 *            解密内容
	 * @param sKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) {
		byte[] iv = ivString.getBytes();
		try {
			byte[] encryp = parseHexStr2Byte(data);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] original = cipher.doFinal(encryp);
			return new String(original);
		} catch (Exception e) {
			CurLogger.error(e.getMessage(), e);
			return null;
		}
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		} else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; ++i) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
	}

	public static String parseByte2HexStr(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; ++i) {
			String hex = Integer.toHexString(buf[i] & 255);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

}
