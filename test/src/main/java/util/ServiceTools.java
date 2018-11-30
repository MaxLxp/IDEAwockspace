package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceTools {
	private static Logger CurLogger = LoggerFactory.getLogger(ServiceTools.class);

	/**
	 * 时间格式化
	 *
	 * @param time1
	 * @param time2
	 * @param sdsStr
	 * @return
	 */
	public static String dateFormat(Date time1, Date time2, String sdsStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(sdsStr);
		String t1 = sdf.format(time1);
		String t2 = sdf.format(time2);
		String dateSdf = t1 + "-" + t2;
		return dateSdf;
	}

	/**
	 * 时间格式化
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static String datesFormat(Date time1, Date time2, String sdsStr1, String sdsStr2) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(sdsStr1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(sdsStr2);
		String t1 = sdf1.format(time1);
		String t2 = sdf2.format(time2);
		String dateSdf = t1 + "-" + t2;
		return dateSdf;
	}

	/**
	 * 单个时间格式化
	 *
	 * @param time
	 * @param sdsStr
	 * @return
	 */
	public static String dateFormatMD(Date time, String sdsStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(sdsStr);
		String t = sdf.format(time);
		return t;
	}

	/**
	 * 根据本级机构域名生成随机域名
	 *
	 * @param cate
	 *            设备类型 或 虚拟机构 或 分组
	 * @param domain
	 *            本级机构域名
	 * @return
	 */
	public static String getNetDomain(String cate, String domain) {

		// 获取系统当前事件并转换为【年】【月】【日】【时】【分】【秒】【毫秒】，180426142530111 格式的字符串
		String dateStr = dateFormatMD(new Date(), "yyMMddHHmmssSSS");

		// 转发服务器
		if ("SVR04".equals(cate)) {
			return "ZF" + dateStr + "." + domain;
		}
		// 管理中心服务器
		if ("SVR06".equals(cate)) {
			return "GL" + dateStr + "." + domain;
		}
		// 摄像机
		if (cate.startsWith("CAM")) {
			return "CAM" + dateStr + "." + domain;
		}
		// 存储服务器
		if ("NVR".equals(cate)) {
			return "NVR" + dateStr + "." + domain;
		}
		// 客户端
		if (cate.startsWith("CNT")) {
			return "KHD" + dateStr + "." + domain;
		}
		return "";
	}

	/**
	 * 0,1转换Boolean类型
	 *
	 * @param str
	 * @return
	 */
	public static Boolean strToBoolean(String str) {
		if (str.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Boolean转换0，1
	 *
	 * @return
	 */
	public static String booleanToStr(Boolean bl) {
		if (bl) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * ｛‘ret’:'','msg':''｝,0表示成功转换为1，其他返回错误码
	 *
	 * @return
	 */
	public static String retMapTransstr(Map<String, String> retMap) {
		if (retMap.get("ret").equals("0")) {
			return "1";
		} else {
			return retMap.get("msg");
		}
	}

	/**
	 * 0转换为true，其他返回false
	 *
	 * @param ret
	 * @return
	 */
	public static Boolean strTransBool(String ret) {
		if (ret.equals("0")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 错误码转换成int类型，空转为：0
	 *
	 * @param ret
	 * @return
	 */
	public static Integer strToInt(String ret) {
		if (ret != null) {
			return Integer.parseInt(ret);
		} else {
			return 0;
		}
	}

	/**
	 * 生成UUID并去掉“—”线,转换全大写
	 *
	 * @return
	 */
	public static String getUUIDUpper() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 生成UUID并去掉“—”线
	 *
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取key 规则：UUID中的奇数位8个字节，在重复该奇数位的逆序，组成16个字节的密钥
	 *
	 * @param uuid
	 * @return
	 */
	public static String getAESKey(String uuid) {
		if (uuid == null) {
			CurLogger.debug("UUID为空");
			return null;
		}
		if (uuid.length() != 32) {
			CurLogger.debug("UUID格式不对");
			return null;
		}
		int js = 0;
		String str = "";
		String key = "";
		for (int i = 0; i < 8; i++) {
			js = i * 2 + 1;
			str = uuid.substring(2 * js - 2, 2 * js);
			key = key + str;
		}
		return key;
	}

	/**
	 * json转换Map<String,String>
	 *
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Map<String, String> jsonToMap(String json)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, String> map = oMapper.readValue(json, new TypeReference<Map<String, String>>() {
		});
		return map;
	}

	/**
	 * json转换List<Map<String,String>>
	 *
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<Map<String, String>> jsonToLstMap(String json)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper oMapper = new ObjectMapper();
		List<Map<String, String>> lst = oMapper.readValue(json, new TypeReference<List<Map<String, String>>>() {
		});
		return lst;
	}

	/**
	 * 首字母转小写
	 *
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
