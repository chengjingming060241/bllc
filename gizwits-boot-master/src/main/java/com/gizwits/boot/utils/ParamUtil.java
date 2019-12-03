package com.gizwits.boot.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

public class ParamUtil {
	

	public static String uncapitalize(String str) {
	    int strLen;
	    if (str == null || (strLen = str.length()) == 0) {
	        return str;
	    }

	    return new StringBuffer(strLen)
	        .append(Character.toLowerCase(str.charAt(0)))
	        .append(str.substring(1))
	        .toString();
	}
	public static boolean isNullOrEmpty(String arg) {
		return isNullOrEmptyOrZero(arg);
	}

	public static boolean isNullOrZero(Object arg) {
		return isNullOrEmptyOrZero(arg);
	}

	public static boolean isNullOrEmptyOrZero(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Long) {
			Long value = (Long) obj;
			if ( value <= 0)
				return true;
		} else if (obj instanceof Integer) {
			Integer value = (Integer) obj;
			if ( value <= 0)
				return true;
		} else if (obj instanceof Double) {
			Double value = (Double) obj;
			if (value <= 0)
				return true;
		} else if (obj instanceof String) {
			String value = (String) obj;
			if (StringUtils.isEmpty(value) || StringUtils.isBlank(value))
				return true;
		} else if (obj instanceof List) {
			List<?> value = (List<?>) obj;
			if (value.size() <= 0)
				return true;
		}

		return false;
	}

	public static boolean isOneOfNullorEmpty(Object... objs) {
		if (objs == null) {
			return true;
		}

		for (Object obj : objs) {
			boolean flag = isNullOrEmptyOrZero(obj);
			if(flag) {
				return flag;
			}
		}
		return false;
	}

	/**
	 * @desc 判断字符串是否是超长的
	 * @param text
	 *            要判断的字符串，其中汉字是当成2个字节长度（GBK码）处理
	 * @param maxLength
	 *            最大的长度(汉字长度的2倍)，如50个汉字，则maxLength应为100
	 * @return
	 */
	public static Boolean isOverLength(String text, int maxLength) {
		if (StringUtils.isEmpty(text))
			return false;

		// return (text.getBytes("gbk").length > maxLength*2);
		return text.length() > maxLength;
	}

	/**
	 * @desc 判断字符串是否是超长的
	 * @param text
	 *            要判断的字符串，其中汉字是当成2个字节长度（GBK码）处理
	 * @param maxLength
	 *            最大的长度(汉字长度的2倍)，如50个汉字，则maxLength应为100
	 * @return
	 */
	public static Boolean isNotEnoughLength(String text, int maxLength) {
		if (StringUtils.isEmpty(text))
			return true;
		return text.length() < maxLength;
	}

	/**
	 * @desc 从params获取名称为key的参数值，并转换为Integer类型
	 * @param key
	 * @return
	 */
	public static Integer getIntFromParams(HttpServletRequest request , String key) {
		String value = request.getParameter(key);
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		return Integer.valueOf(value);
	}

	/**
	 *
	 * @desc 将Map中的数据组装成url
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String mapToUrl(Map<String, String> params, String charset)
			throws UnsupportedEncodingException {
		if (params == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			// Logger.info("key:%s", key);
			String value = entry.getValue();

			if (isFirst) {
				isFirst = false;
			} else {
				sb.append("&");
			}

			if (value != null) {
				sb.append(key
						+ "="
						+ (charset == null ? value : URLEncoder.encode(value,
								charset)));
			} else {
				sb.append("&" + key + "=");
			}
		}
		return sb.toString();
	}

    public static String mapToUrlNotNull(Map<String, String> params, String charset)
            throws UnsupportedEncodingException {
        if (params == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            // Logger.info("key:%s", key);
            String value = entry.getValue();

            if (value != null) {
                if (!isFirst) {
                    sb.append("&");
                }
                sb.append(key
                        + "="
                        + (charset == null ? value : URLEncoder.encode(value,
                        charset)));
            }

            if (isFirst) {
                isFirst = false;
            }

        }
        return sb.toString();
    }

	/**
	 *
	 * @desc 将LinkedHashMap中的数据组装成url
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	/*
	 * public static String mapToUrl(LinkedHashMap <String, String> params,
	 * String charset) throws UnsupportedEncodingException { if (params == null)
	 * { return ""; } StringBuilder sb = new StringBuilder(); boolean isFirst =
	 * true; Iterator iterator = params.keySet().iterator(); while
	 * (iterator.hasNext()) { String key = iterator.next(); String value =
	 * params.get(key);
	 *
	 * if (isFirst) { isFirst = false; } else { sb.append("&"); }
	 *
	 * if (value != null) { sb.append(key + "=" + (charset == null ? value :
	 * URLEncoder.encode(value, charset))); } else { sb.append("&" + key + "=");
	 * } } return sb.toString(); }
	 */

	/**
	 * @desc 获得按Map键升序排序的字符串 如a=88&f=22&t=uu 或 a=88f=22t=uu
	 * @param params
	 * @param withChar
	 *            返回的字符串是否包含“&”字符
	 * @return
	 */
	public static String getSortStrFromMap(Map<String, String> params,
										   String[] notIn, boolean withChar) {
		StringBuffer content = new StringBuffer();

		if (params.size() <= 0) {
			return "";
		}
		// 按照key做排序
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		int index = 0;
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);

			if (notIn != null && Arrays.asList(notIn).indexOf(key) >= 0)
				continue;

			String value = params.get(key);

			if (withChar == true) {
				if (value != null) {
					content.append((index == 0 ? "" : "&") + key + "=" + value);
				} else {
					content.append((index == 0 ? "" : "&") + key + "=");
				}
			} else {
				if (value != null) {
					content.append(key + "=" + value);
				} else {
					content.append(key + "=");
				}
			}
			index++;
		}
		return content.toString();
	}




	/**
	 *
	 * @desc 将html中的< >分别替换为&lt; &gt;
	 * @param content
	 * @return
	 */
	public static String html2Xml(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		return content;
	}

	/**
	 * @desc 清除url中的参数paramName
	 * @param url
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public static String clearUrlParam(String url, String paramName,
									   String paramValue) {
		if (StringUtils.isEmpty(url) || StringUtils.isEmpty(paramName)) {
			return url;
		}

		if (url.indexOf(paramName) > 0) {
			int begin = url.indexOf(paramName);
			if (begin > 0 && url.indexOf(paramName + "=") < 0) {
				url = url.replace(paramName, "");
				return url;
			}
			int length = 0;
			if (url.indexOf(paramName + "=" + paramValue + "&") < 0
					&& begin > 0) {
				begin--;
			}
			length = begin + paramName.length() + 2 + paramValue.length();
			try {
				String replaceStr = url.substring(begin, length);
				url = url.replace(replaceStr, "");
			} catch (Exception e) {
			}
		}
		return url;
	}

	/**
	 * 把参数map转换成<String, String>格式
	 *
	 * @param param
	 */
	public static Map<String, String> formatParamMap(Map<String, Object> param) {
		Map<String, String> formatMap = new HashMap();
		for (Entry<String, Object> entry : param.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			String val = value == null ? "" : value.toString();
			formatMap.put(key, val);
		}
		return formatMap;
	}

	/**
	 * @desc 在content的每一行后面加上标签[br]
	 * @param content
	 * @return
	 */
	public static String blank2Nbsp(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		try {
			content = content.replaceAll(" ", "&nbsp;");
		} catch (Exception e) {
			return content;
		}
		return content;
	}


	/**
	 * 移除开放平台的参数
	 * */
	public static Map<String, String> removeOpenPlatformParam(
			Map<String, String> param_current) {
		Map<String, String> param_back = new HashMap<String, String>(
				param_current);
		if (param_back.containsKey("body")) {
			param_back.remove("body");
		}
		if (param_back.containsKey("service_ticket")) {
			param_back.remove("service_ticket");
		}
		if (param_back.containsKey("currentLoginUserId")) {
			param_back.remove("currentLoginUserId");
		}

		return param_back;
	}

	public static String removeUnsafeString(String origString){
		return origString.replaceAll("(?i)"+"javascript", "");
	}
	/**
	 * 移除参数中的空格
	 * @param map
	 * @return
	 */
	public static Map<String, String> trimMapValue(Map<String, String> map) {

		for(Entry<String, String> entry : map.entrySet()) {
			map.put(entry.getKey(), entry.getValue().trim());
		}
		return map;
	}

	/**
	 * 判断字符串是否为整数
	 * @author zhengqb
	 */
	public static boolean isInteger(String str) {
		return str.matches("[0-9]+");
	}

	/**
	 * 隐藏字符串中的部分字符
	 * @author yangjh
	 * @param origin
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String hideChar(String origin, int begin, int end) {

		if (null == origin) {
			return "";
		}
		int len = origin.indexOf("@") == -1 ? origin.length() : origin
				.indexOf("@");
		if (len < end) {
			return "";
		}
		StringBuilder sb = new StringBuilder(origin);
		len -= begin;
		for (int count = end - begin; count >= 0; count--) {
			sb.setCharAt(len--, '*');
		}
		return sb.toString();
	}
}
