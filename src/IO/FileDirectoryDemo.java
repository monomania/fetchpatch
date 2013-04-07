package IO;

import java.io.*;

public class FileDirectoryDemo {
	public static void main(String[] args) {
		// 如果没有指定参数，则缺省为当前目录。
		if (args.length == 0) {
			args = new String[] { "." };
		}
		try {
			// 新建指定目录的File对象。
			File currentPath = new File(args[0]);
			// 在指定目录新建temp目录的File对象。
			File tempPath = new File(currentPath, "temp");
			// 用“tempPath”对象在指定目录下创建temp目录。
			tempPath.mkdir();
			// 在temp目录下创建两个文件。
			File temp1 = new File(tempPath, "temp1.txt");
			temp1.createNewFile();
			File temp2 = new File(tempPath, "temp2.txt");
			temp2.createNewFile();

			// 递归显示指定目录的内容。
			System.out.println("显示指定目录的内容");
			listSubDir(currentPath);

			// 更改文件名“temp1.txt”为“temp.txt”。
			File temp1new = new File(tempPath, "temp.txt");
			temp1.renameTo(temp1new);
			// 递归显示temp子目录的内容。
			System.out.println("更改文件名后，显示temp子目录的内容");
			listSubDir(tempPath);

			// 删除文件“temp2.txt”。
			temp2.delete();
			// 递归显示temp子目录的内容。
			System.out.println("删除文件后，显示temp子目录的内容");
			listSubDir(tempPath);
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}

	// 递归显示指定目录的内容。
	static void listSubDir(File currentPath) {
		// 取得指定目录的内容列表。
		String[] fileNames = currentPath.list();
		try {
			for (int i = 0; i < fileNames.length; i++) {
				File f = new File(currentPath.getPath(), fileNames[i]);
				// 如果是目录，则显示目录名后，递归调用，显示子目录的内容。
				if (f.isDirectory()) {
					// 以规范的路径格式显示目录。
					System.out.println(f.getCanonicalPath());
					// 递归调用，显示子目录。
					listSubDir(f);
				}
				// 如果是文件，则显示文件名，不包含路径信息。
				else {
					System.out.println(f.getName());
				}
			}
		} catch (IOException e) {
			System.err.println("IOException");
		}
	}
}
