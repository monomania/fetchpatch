package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具類
 * 
 * @author szy
 */
@SuppressWarnings( { "unchecked" })
public abstract class ToolKit {

	/**
	 * 返回当前类class路径
	 * 
	 * @return
	 */
	public static String obtainRootPath() {
		String rootPath = ToolKit.class.getResource("/").getPath();
		System.out.println("Root路径为: "+rootPath);
		return rootPath;
	}

	/**
	 * 返回当前路径,以ToolKit的位置
	 * 
	 * @return
	 */
	public static String obtainCurrentPath() {
		return ToolKit.class.getResource("").getPath();
	}

	public static Boolean isEmpty(Object obj) {
		if (null == obj || "".equals(obj))
			return true;
		return false;
	}

	public static Boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	/**
	 * 加載屬性集文件
	 * 
	 * @param prop
	 */
	public static Properties loadPropOfStream(InputStream is) {
		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	

	/**
	 * 加載屬性集文件
	 * 
	 * @param prop
	 */
	public static Properties loadProp(String propPath) {
		if (ToolKit.isEmpty(propPath))
			return null;
		File file = new File(propPath);
		if (!file.exists()) {
			System.err.println("找不到配置文件 . " + propPath);
			return null;
		}

		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * 綁定屬性集文件到Map
	 * 
	 * @param prop
	 * @param Cache
	 */
	public static void bindPropToCache(Properties prop, Map cache) {
		Set<Object> keySet = prop.keySet();
		for (Object key : keySet) {
			cache.put(key, prop.get(key));
		}
	}

	/**
	 * 讀取配置文件,獲取緩沖字符輸入流
	 * 
	 * @param filePath
	 * @return
	 */
	public static BufferedReader loadFileOfIn(String filePath) {
		if (ToolKit.isEmpty(filePath))
			return null;
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("找不到配置文件 . " + filePath);
			return null;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return br;
	}

	/**
	 * 讀取配置文件,獲取緩沖字符輸出流
	 * 
	 * @param filePath
	 * @return
	 */
	public static BufferedWriter loadFileOfOut(String filePath) {
		if (ToolKit.isEmpty(filePath))
			return null;
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("找不到配置文件 . " + filePath);
			return null;
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return bw;
	}

	/**
	 * 獲取下拉框的字符串數據
	 * 
	 * @param filePath
	 *            文件存儲路徑
	 * @return
	 */
	public static void bindComboBoxStrDataOfStream(String fileName, List cache) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ToolKit.isEmpty(br))
			return;
		String line = null;
		try {
			while (null != (line = br.readLine())) {
				cache.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 獲取下拉框的字符串數據
	 * 
	 * @param filePath
	 *            文件存儲路徑
	 * @return
	 */
	public static void bindComboBoxStrData(String filePath, List cache) {
		BufferedReader br = ToolKit.loadFileOfIn(filePath);
		if (ToolKit.isEmpty(br))
			return;
		String line = null;
		try {
			while (null != (line = br.readLine())) {
				cache.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 驗證數組中是否包含有val
	 * 
	 * @param dataArr
	 *            數組
	 * @param val
	 *            值
	 * @return
	 */
	public static Boolean contains(String[] dataArr, String value) {
		if (ToolKit.isEmpty(value)) {
			return null;
		}
		for (String data : dataArr) {
			if (data.trim().equalsIgnoreCase(value.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 獲取文件名
	 * 
	 * @param source
	 * @return
	 */
	public static String fetchFileName(String source) {
		if (ToolKit.isEmpty(source)) {
			return source;
		}
		return source.substring(source.lastIndexOf("/") + 1);
	}

	/**
	 * 獲取行符號
	 * 
	 * @return
	 */
	public static String fetchLineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Date obtainCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @param format
	 *            指定时间格式
	 * @return
	 */
	public static String obtainCurrentDateStr() {
		return obtainCurrentDateStr(SimpleDateFormatFactory.DEFAULT_FORMAT);
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @param format
	 *            指定时间格式
	 * @return
	 */
	public static String obtainCurrentDateStr(String format) {
		SimpleDateFormat dateFormat = SimpleDateFormatFactory
				.getInstance(format);
		return dateFormat.format(ToolKit.obtainCurrentDate());
	}
	
	/**
	 * 将首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterToUpper(String str) {
		char[] chars = str.toCharArray();
		chars[0] -= 32;
		return String.valueOf(chars);
	}

	/**
	 * 将首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterToLower(String str) {
		char[] chars = str.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}

	/**
	 * 将一个时间转换为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String converDateToStr(Date date, String pattern) {
		if (isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = null;
		if (isEmpty(pattern)) {
			sdf = SimpleDateFormatFactory
					.getInstance(SimpleDateFormatFactory.DEFAULT_FORMAT);
		} else {
			sdf = SimpleDateFormatFactory.getInstance(pattern);
		}
		return sdf.format(date);
	}

	/**
	 * 获取字符 str 里面的 symbol 的个数
	 * 
	 * @param str
	 *            字符串
	 * @param symbol
	 *            符号
	 * @return
	 */
	public static int obtainSymbolCount(String str, String symbol) {
		Pattern p = Pattern.compile(symbol);
		Matcher m = p.matcher(str);
		int num = 0;
		while (m.find()) {
			num++;
		}
		return num;
	}
	
	/**
	 * 根據配置的#文件路徑中的截斷字,得出文件在補丁包裡的路徑
	 * @param sourcePath
	 * @return
	 */
	public static String[] genPathInPatch(String searchDir,String patchPath,String sourcePath) {
		if(ToolKit.isEmpty(searchDir) || ToolKit.isEmpty(patchPath) || ToolKit.isEmpty(sourcePath))
			return null;
		String[] snippets = new String[3];
		
		String temp = searchDir.toLowerCase();
		int index = temp.indexOf("apps\\webcontent");
		String host = searchDir.substring(0,index);
		System.out.println("THIS HOST PATH IS : " + host);
		
		ClassPathHandle[] classPathHandles = ClassPathHandle.values();
		//如果是host路径里的文件
		if((temp = sourcePath.toLowerCase()).contains(host.toLowerCase())){
			for(ClassPathHandle classPathHandle : classPathHandles){
				if (temp.contains(classPathHandle.value)
						&& classPathHandle.toString().toLowerCase().startsWith("host")) {
					classPathHandle.setHost(host);
					sourcePath = classPathHandle.handle(sourcePath);
					break;
				}
			}
		}else{
			for(ClassPathHandle classPathHandle : classPathHandles){
				if (temp.contains(classPathHandle.value)
						&& !classPathHandle.toString().toLowerCase().startsWith("host")) {
					classPathHandle.setHost(host);
					sourcePath = classPathHandle.handle(sourcePath);
					break;
				}
			}
		}
		
		if(!sourcePath.startsWith("\\"))
			sourcePath =  "\\"+sourcePath;
		
		String fileName = fetchFileNameByFilePath(sourcePath);
		searchDir += sourcePath;
		patchPath += sourcePath;
		
		snippets[0] = fileName;
		snippets[1] = searchDir.replaceAll(fileName, "");
		snippets[2] = patchPath.replaceAll(fileName, "");
		
		return snippets;
	}
	
	/**
	 * 判断前一个文件是否是由后一个文件产生的
	 * @param compiledFileName
	 * @param sourceFileName
	 * @return
	 */
	public static Boolean isGenBySourceFile(String compiledFileName,
			String sourceFileName) {
		if (compiledFileName.contains(".") && compiledFileName.contains(".")) {
			String[] compiledFileNames = compiledFileName.split("\\.",2);
			String[] sourceFileNames = sourceFileName.split("\\.",2);
			compiledFileName = compiledFileNames[1]; 
			sourceFileName = sourceFileNames[1]; 
			if(!compiledFileName.equalsIgnoreCase(sourceFileName) && !compiledFileName.equalsIgnoreCase("class") && !sourceFileName.equalsIgnoreCase("java")){
				return false;
			}
			compiledFileName = compiledFileNames[0]; 
			sourceFileName = sourceFileNames[0]; 
		}
		if (compiledFileName.equals(sourceFileName)
				|| (compiledFileName.startsWith(sourceFileName + "$") && compiledFileName
						.contains(sourceFileName))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 从文件路径中得到文件名
	 * @param filePath
	 * @return
	 */
	public static String fetchFileNameByFilePath(String filePath){
		if(!filePath.contains(File.separator)){
			return filePath;
		}
		String fileName =  filePath.substring(filePath.lastIndexOf(File.separator)+1);
		if(!fileName.contains(".")){
			 System.err.println("可能不是正确的文件名: "+fileName);
			 System.err.println("路径为: ["+filePath+"]");
		}
		return fileName;
	}
	public static void main(String[] args) {
		System.out.println(ToolKit.fetchFileNameByFilePath("aa.xx"));
	}
}
