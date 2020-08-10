package com.matiange.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class StringHelper extends StringUtils {

	/**
	 * 返回类型 与 值
	 *
	 * @param str
	 * @return 1手机号； 2 身份证号 ; 3座机
	 */
	public static Map<String, Object> getStringReplaceStar(String str) {
		Map<String, Object> ret = new HashMap<String, Object>();
		boolean status = false;
		// 不满足任何匹配条件: 字符串总长度小于7位不予考虑
		if (str == null || "".equals(str) || str.length() < 7) {
			ret.put("msgType", 0);
			ret.put("isMatch", status);
			return ret;
		}
		// 如果手机号或座机号已86或 +86 开头
		if (str.startsWith("+86") || str.startsWith("86")) {
			// 如果已86或 +86 开头 去掉+86或86后在计算
			str = str.substring(str.indexOf("86")+2, str.length());
		} else if (str.indexOf("-") != -1) { // 如果 以010-开头
			// 获取 以010-开头 去掉 - 后在计算
			str = str.replace("-", "");
		}
		// 字符串长度>=15位 按照身份证号计算
		if (str.length() >= 15) {
			ret.put("msgType", 2);
			// 中间生日部分隐藏，返回 前14位
			String idcard = "";
			if (str.length() == 15) {
				idcard = str.replaceAll(str.substring(3, 13), "********");
				//idcard = idcard.substring(0, 11);
				ret.put("codeInfo", idcard);
				status = true;
			} else if (str.length() == 18) {
				idcard = str.replaceAll(str.substring(3, 16), "***********");
				//idcard = idcard.substring(0, 14);
				ret.put("codeInfo", idcard);
				status = true;
			} else {
				ret.put("msgType", 0);
			}
			// 如果字符串长度 大于=8 小于15 分情况考虑是手机号还是座机 msgType统一为 1
		} else if (str.length() >= 8 && str.length() <15) {
			ret.put("msgType", 1);
			// 手机号
			if (str.length() == 11 && !str.startsWith("010") && !str.startsWith("0")) {// 11位手机号
				// 中间四位*好代替，返回前7位
				String phone = str.replaceAll(str.substring(3, 7), "****");
				//phone = phone.substring(0, 7);
				ret.put("codeInfo", phone);
				status = true;
			} else 	if (str.length() == 10 && !str.startsWith("010")) { // 10位手机号-- 属于数据错误
				// 直接返回前四位
				String phone = str.replaceAll(str.substring(3, 6), "****");//str.substring(0, 3)+"***";
				ret.put("codeInfo", phone);
				status = true;
			} else 	if (str.length() == 9 && !str.startsWith("010")) { // 9位手机号-- 属于数据错误
				// 直接返回前四位
				String phone = str.replaceAll(str.substring(3, 5), "****");
				ret.put("codeInfo", phone);
				status = true;
			} else 	if (str.length() == 8) { // 8位座机
				// 直接返回前四位
				String phone = str.replaceAll(str.substring(4, 6), "****");
				ret.put("codeInfo", phone);
				status = true;
			}  else if (str.length() == 11 && str.startsWith("010")) {// 带有区号的 8位座机
				// 直接返回前四位
				String phone  = str.substring(0, 5)+"**";
				ret.put("codeInfo", phone);
				status = true;
			} else if (str.length() == 10 && str.startsWith("010")) {// 带有区号的 7位座机
				// 直接返回前四位
				String phone  = str.substring(0, 5)+"*";
				ret.put("codeInfo", phone);
				status = true;
			} else if (str.length() == 12 && str.startsWith("010")) {// 带有区号的 9位座机
				// 直接返回前四位
				String phone  = str.substring(0, 6)+"**";
				ret.put("codeInfo", phone);
				status = true;
			} else  {// 返回0
				if (str.length() == 13 && str.startsWith("0")) {// 带有区号的 9位座机
					// 直接返回前四位
					String phone  = str.substring(0, 6)+"***";
					ret.put("codeInfo", phone);
					status = true;
				}  else if (str.length() == 12 && str.startsWith("0")) {// 带有区号的8位座机
					// 直接返回前四位
					String phone  = str.substring(0, 6)+"**";
					ret.put("codeInfo", phone);
					status = true;
				} else if (str.length() == 11 && str.startsWith("0")) {// 带有区号的 7位座机
					// 直接返回前四位
					String phone  = str.substring(0, 6)+"*";
					ret.put("codeInfo", phone);
					status = true;
				} else {
					ret.put("msgType", 0);
				}
			}
		} else 	if (str.length() == 7) { // 正好符合7位座机的情况
			// 直接返回前四位
			String phone  = str.substring(0, 2)+"*****";
			ret.put("codeInfo", phone);
			ret.put("msgType", 1);
			status = true;
		}
		ret.put("isMatch", status);
		// 原号码
		ret.put("code", str);
		return ret;
	}
	
	/**
	 * 字符串拼接
	 * 
	 * @param strs
	 */
	public static String concat(String... strs) {
		if (null == strs) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String str : strs) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String trim(String sour, String f) {
		if (StringUtils.isBlank(sour)) {
			return sour;
		}
		if (sour.startsWith(f)) {
			sour = sour.substring(f.length());
		}
		if (sour.endsWith(f)) {
			sour = sour.substring(0, sour.length() - f.length());
		}
		return sour;
	}

	/**
	 * 根据main函数args参数 获取spring.profiles.active
	 * @param args
	 * @return
	 */
	public static String getProfilesByArgs(String[] args, String keyType) {
		String profile = "";
		for (String str : args) {
			if (str.contains(keyType)) {
				profile = str;
				break;
			}
		}
		if (StringUtils.isNotBlank(profile)) {
			String trim = profile.split("=")[1].trim();
			return trim;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据main函数args参数 获取spring.config.location
	 * @param args
	 * @return
	 */
	/*public static String getConfigLocationByArgs(String[] args) {
		String profile = "";
		for (String str : args) {
			if (str.contains(EnvConstant.ENV_PROFILE_KEY)) {
				profile = str;
				break;
			}
		}
		if (StringUtils.isNotBlank(profile)) {
			String trim = profile.split("=")[1].trim();
			return trim;
		} else {
			return null;
		}
	}*/
}
