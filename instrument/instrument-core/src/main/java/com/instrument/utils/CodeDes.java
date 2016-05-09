package com.instrument.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * The class CodeDes.
 *
 * Description:用于存放消息码
 *
 * @author: zengbo
 * @since: 2016年4月27日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class CodeDes {

	private static Map<String, String> code = new HashMap<String, String>();

	public static Map<String, String> getCode(String key, String value) {
		code.clear();
		code.put(key, value);
		return code;
	}

}
