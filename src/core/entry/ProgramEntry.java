package core.entry;

import java.util.Properties;

import ui.MainWindows;
import core.FPConstant;
import core.ToolKit;
import core.cache.ConfCache;
import core.cache.StartPathRecordCache;
import core.cache.StringsCache;


/**
 * 程序入口
 * @author szy
 *
 */
public class ProgramEntry {
	public static void main(String[] args) {
		initCache();
		
		MainWindows mw = new MainWindows();
		mw.init();
	}
	
	public static void initCache(){
		Properties confProp = ToolKit.loadPropOfStream(ClassLoader.getSystemResourceAsStream(FPConstant.CONF_FILENAME));
		ToolKit.bindPropToCache(confProp, ConfCache.getConfCacheMap());

		Properties stringsProp = ToolKit.loadPropOfStream(ClassLoader.getSystemResourceAsStream(FPConstant.STRINGS_FILENAME));
		ToolKit.bindPropToCache(stringsProp, StringsCache.getStringsCacheMap());

		ToolKit.bindComboBoxStrDataOfStream(FPConstant.STARTPATH_RECORD_FILENAME, StartPathRecordCache
				.getStartPathRecordCacheList());
	}
}
