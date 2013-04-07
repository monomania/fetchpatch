package core.handle;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JComboBox;

import core.FPConstant;
import core.ToolKit;
import core.cache.StartPathRecordCache;

/**
 * UI数据处理
 * 
 * @author szy
 * 
 */
public class UIDataHandle {

	/**
	 * 下拉框数据,处理
	 * 
	 * @param selectText
	 */
	public static void handleComboboxData(String selectText,JComboBox comboBox) {
		Boolean isContains = StartPathRecordCache.containsValue(selectText);
		if (!isContains) {// 如果未包含
			// 写入startpathrecord.TXT
			RandomAccessFile randomFile = null;
			try {
				// 打开一个随机访问文件流，按读写方式
				randomFile = new RandomAccessFile(FPConstant.STARTPATH_RECORD_FILENAME, "rw");
				long fileLength = randomFile.length();
				// 将写文件指针移到文件尾
				randomFile.seek(fileLength);
				randomFile.writeBytes(selectText);
				randomFile.writeBytes(ToolKit.fetchLineSeparator());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != randomFile) {
					try {
						randomFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			//加入缓存
			StartPathRecordCache.add(selectText);
			//加入控件中
			comboBox.addItem(selectText);
		}
	}
}
