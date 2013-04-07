package core.cache;

import java.util.ArrayList;
import java.util.List;


/**
 * @author szy
 *
 */
public class StartPathRecordCache {

	private static final List<String>	startPathRecordCacheList = new ArrayList<String>();

	public static List<String> getStartPathRecordCacheList() {
		return startPathRecordCacheList;
	}
	public static String[] getStartPathRecordCacheArray() {
		return startPathRecordCacheList.toArray(new String[]{});
	}

	public static void add(String value) {
		startPathRecordCacheList.add(value);
	}
	
	public static void clear() {
		startPathRecordCacheList.clear();
	}

	public static Boolean containsValue(String value) {
		return startPathRecordCacheList.contains(value);
	}

}
