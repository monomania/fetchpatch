package core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import core.ToolKit;

/**
 * 窗口使用到的字符的緩存
 * 
 * @author szy
 * 
 */
public class StringsCache {

	public static final Map<String,String>	stringsCacheMap	= new HashMap<String,String>();
	
	public static Map<String, String> getStringsCacheMap() {
		return stringsCacheMap;
	}

	public static String get(String Stringey) {
		return stringsCacheMap.get(Stringey);
	}

	public static String get(String Stringey, String defaultValue) {
		String value = StringsCache.get(Stringey);
		return ToolKit.isEmpty(value)?defaultValue:value;
	}
	
	public static void put(String Stringey, String value) {
		stringsCacheMap.put(Stringey, value);
	}

	public static void putAll(Map<String, String> stringsMap) {
		stringsCacheMap.putAll(stringsMap);
	}

	public static void remove(String Stringey) {
		stringsCacheMap.remove(Stringey);
	}

	public static void clear() {
		stringsCacheMap.clear();
	}

	public static Boolean containsStringey(String Stringey) {
		return stringsCacheMap.containsKey(Stringey);
	}

	public static Boolean containsValue(String value) {
		return stringsCacheMap.containsValue(value);
	}

	public static String toStr() {
		StringBuilder str_build = new StringBuilder("stringsCacheMap: ");
		Set<String> StringeySet = stringsCacheMap.keySet();
		int i = 0;
		for(String Stringey : StringeySet){
			if(i++ != 0){
				str_build.append(",");
				if(i % 7 == 0){
					str_build.append("\r\n\t\t");
				}
			}
			str_build.append("[").append(Stringey.toString())
					.append("=").append(stringsCacheMap.get(Stringey)).append("]");
		}
		return str_build.toString();
	}

	
}
