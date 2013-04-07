package core;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * SimpleDateFormat的工厂类
 * 生成SimpleDate实例
 * @author 石泽源
 * 2012-8-17
 */
public class SimpleDateFormatFactory {
	
	/**
	 * 指定时间格式
	 */
	public static final String DATE_FORMAT_yyyyMM = "yyyyMM";
	public static final String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
	public static final String DATE_FORMAT_yyyyMMddHHmmssS = "yyyy年MM月dd日HH时mm分ss秒S";
	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	
	
	public static final String DEFAULT_FORMAT = DATE_FORMAT_LONG;
	//缓存
	private static final Map<String, SimpleDateFormat> sdfmap = new HashMap<String, SimpleDateFormat>();

	private SimpleDateFormatFactory() {
		super();
	}

	private static SimpleDateFormat sdf = null;

	
	public synchronized static final SimpleDateFormat getInstance() {
		return getInstance(DEFAULT_FORMAT);
	}
	
	public  synchronized static final SimpleDateFormat getInstance(String format) {
		if (ToolKit.isEmpty(format)) {
			return getInstance();
		}

		// 先用缓存中取
		sdf = sdfmap.get(format);
		if (sdf == null) {
			// 没有则生成
			sdf = new SimpleDateFormat(format);
			// 加入缓存
			sdfmap.put(format, sdf);
		}
		return sdf;
	}


}
