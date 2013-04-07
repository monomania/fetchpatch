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
public class ConfCache {

	public static final Map<String,String>	confCacheMap	= new HashMap<String,String>();
	
	public static Map<String, String> getConfCacheMap() {
		return confCacheMap;
	}

	public static String get(String Stringey) {
		return confCacheMap.get(Stringey);
	}

	public static String get(String Stringey, String defaultValue) {
		String value = ConfCache.get(Stringey);
		return ToolKit.isEmpty(value)?defaultValue:value;
	}
	
	public static void put(String Stringey, String value) {
		confCacheMap.put(Stringey, value);
	}

	public static void putAll(Map<String, String> stringsMap) {
		confCacheMap.putAll(stringsMap);
	}

	public static void remove(String Stringey) {
		confCacheMap.remove(Stringey);
	}

	public static void clear() {
		confCacheMap.clear();
	}

	public static Boolean containsStringey(String Stringey) {
		return confCacheMap.containsKey(Stringey);
	}

	public static Boolean containsValue(String value) {
		return confCacheMap.containsValue(value);
	}

	public static String toStr() {
		StringBuilder str_build = new StringBuilder("stringsCacheMap: ");
		Set<String> StringeySet = confCacheMap.keySet();
		int i = 0;
		for(String Stringey : StringeySet){
			if(i++ != 0){
				str_build.append(",");
				if(i % 7 == 0){
					str_build.append("\r\n\t\t");
				}
			}
			str_build.append("[").append(Stringey.toString())
					.append("=").append(confCacheMap.get(Stringey)).append("]");
		}
		return str_build.toString();
	}

	
}
