package core.handle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import core.SimpleDateFormatFactory;
import core.ToolKit;
import core.cache.ConfCache;

/**
 * @author szy
 */
public class PatchHandle {

	/**
	 * 補丁路径集合
	 */
	private List<String> fileList;

	private String				searchDir;

	public PatchHandle(List<String> fileList, String searchDir) {
		this.fileList = fileList;
		this.searchDir = searchDir;
	}

	/**
	 * 處理
	 */
	public void handle() {
		cleanupFile();
		fetchPathFiles();
		statistics();
	}

	/**
	 * 整理文件 得出{java,編譯後的class文件,集合;靜態資源文件,集合}
	 */
	private void cleanupFile() {}

	/**
	 * 獲取補丁 創建補丁存放目錄及其目錄結構
	 */
	private void fetchPathFiles() {
		// 路徑
		String patchPath = ConfCache.get("patchPath");
		String temp = searchDir;
		for(int i = 0;i<2;i++){
			int index = temp.indexOf("\\");
			temp = temp.substring(index+1);
		}
		int index = temp.indexOf("\\");
		temp = temp.substring(0,index);
		patchPath = patchPath.concat(temp);
		
		if (!patchPath.endsWith(File.separator)) {
			patchPath = patchPath.concat(File.separator);
		}
		// 目錄
		String patchDir = ConfCache
				.get("patchDir")
				.concat(
						ToolKit
								.obtainCurrentDateStr(SimpleDateFormatFactory.DATE_FORMAT_yyyyMMddHHmmssS));
		// 補丁完整路徑
		patchPath = patchPath.concat(patchDir);
		
		genPatchFile(searchDir,patchPath);
	}
	
	
	private void genPatchFile(String rootPath,String patchPath){
		File[] files = new File(rootPath).listFiles();
		String fileName = null;
		for(File file : files){
			if(file.isFile()){
				fileName = file.getName();
				if (fileList.contains(fileName)
						|| (fileName.contains("$") && 
								fileList.contains(
										fileName.replaceAll("\\$[^\\.]*\\b","")
												  )
							)) {
					System.out.println(file.getPath());
					String sourcePath = file.getPath();
					sourcePath = sourcePath.substring(searchDir.length());
					if(sourcePath.startsWith(File.separator)){
						sourcePath = File.separator+ sourcePath;
					}
					
					try {
						BufferedInputStream bis = new BufferedInputStream(
								new FileInputStream(file));
						String factPatchPath = patchPath + sourcePath;
						System.out.println("補丁現路徑: " + factPatchPath);
						File outputFile = new File(factPatchPath);
						if (!outputFile.exists()) {
							File parentFile = outputFile.getParentFile();
							if (!parentFile.exists()) {
								parentFile.mkdirs();
							}
							outputFile.createNewFile();
						}
						BufferedOutputStream bos = new BufferedOutputStream(
								new FileOutputStream(outputFile));

						byte[] bytes = new byte[1024];
						int len;
						while ((len = bis.read(bytes)) != -1) {
							bos.write(bytes, 0, len);
						}
						bos.flush();
						bos.close();
						bis.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				genPatchFile(file.getPath(),patchPath);
			}
		}
	}

	

	/**
	 * 統計文件數
	 */
	private void statistics() {

	}
	
	public static void main(String[] args) {
		String aaa = "111$aaaaaaaaaaaaasssxxx1.class";
		System.out.println(aaa = aaa.replaceAll("\\$[^\\.]*\\b", ""));
	}

}
