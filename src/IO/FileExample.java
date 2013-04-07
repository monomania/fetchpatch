package IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.StringTokenizer;

public class FileExample {
	public FileExample() {
		super();// 调用父类的构造函数
	}

	public static void main(String[] args) {
		try {
			String outfile = "demoout.xml";
			// 定义了一个变量, 用于标识输出文件
			String infile = "demoin.xml";
			// 定义了一个变量, 用于标识输入文件
			DataOutputStream dt = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(outfile)));
			/**
			 * 用FileOutputStream定义一个输入流文件，
			 * 然后用BuferedOutputStream调用FileOutputStream对象生成一个缓冲输出流
			 * 然后用DataOutputStream调用BuferedOutputStream对象生成数据格式化输出流
			 */
			BufferedWriter NewFile = new BufferedWriter(new OutputStreamWriter(
					dt, "gbk"));// 对中文的处理
			DataInputStream rafFile1 = new DataInputStream(
					new BufferedInputStream(new FileInputStream(infile)));
			/**
			 * 用FileInputStream定义一个输入流文件，
			 * 然后用BuferedInputStream调用FileInputStream对象生成一个缓冲输出流
			 * ，其后用DataInputStream中调用BuferedInputStream对象生成数据格式化输出流
			 */
			BufferedReader rafFile = new BufferedReader(new InputStreamReader(
					rafFile1, "gbk"));// 对中文的处理
			String xmlcontent = "";
			char tag = 0;// 文件用字符零结束
			while (tag != (char) (-1)) {
				xmlcontent = xmlcontent + tag + rafFile.readLine() + "";
			}
			NewFile.write(xmlcontent);
			NewFile.flush();// 清空缓冲区
			NewFile.close();
			rafFile.close();
			System.gc();// 强制立即回收垃圾，即释放内存。
		} catch (NullPointerException exc) {
			exc.printStackTrace();
		} catch (java.lang.IndexOutOfBoundsException outb) {
			System.out.println(outb.getMessage());
			outb.printStackTrace();
		} catch (FileNotFoundException fex) {
			System.out.println("fex" + fex.getMessage());
		} catch (IOException iex) {
			System.out.println("iex" + iex.getMessage());
		}
	}
}

class FileRandomRW {
	// 需要输入的person数目。
	public static int	NUMBER	= 3;

	public static void main(String[] args) {
		Persons[] people = new Persons[NUMBER];
		people[0] = new Persons("张峰", 26, 2000, "N");
		people[1] = new Persons("艳娜", 25, 50000, "Y");
		people[2] = new Persons("李朋", 50, 7000, "F");
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					"peoplerandom.dat"));
			// 将人员数据保存至“peoplerandom.dat”二进制文件中。
			writeData(people, out);
			// 关闭流。
			out.close();
			// 从二进制文件“peoplerandom.dat”中逆序读取数据。
			RandomAccessFile inOut = new RandomAccessFile("peoplerandom.dat",
					"rw");
			Persons[] inPeople = readDataReverse(inOut);
			// 输出读入的数据。
			System.out.println("原始数据：");
			for (int i = 0; i < inPeople.length; i++) {
				System.out.println(inPeople[i]);
			}
			// 修改文件的第三条记录。
			inPeople[2].setSalary(4500);
			// 将修改结果写入文件。
			inPeople[2].writeData(inOut, 3);
			// 关闭流。
			inOut.close();
			// 从文件中读入的第三条记录，并输出，以验证修改结果。
			RandomAccessFile in = new RandomAccessFile("peoplerandom.dat", "r");
			Persons in3People = new Persons();
			// 随机读第三条记录。
			in3People.readData(in, 3);
			// 关闭流。
			in.close();
			System.out.println("修改后的记录");
			System.out.println(in3People);
		} catch (IOException exception) {
			System.err.println("IOException");
		}
	}

	// 将数据写入输出流。
	static void writeData(Persons[] p, DataOutputStream out) throws IOException {
		for (int i = 0; i < p.length; i++) {
			p[i].writeData(out);
		}
	}

	// 将数据从输入流中逆序读出。
	static Persons[] readDataReverse(RandomAccessFile in) throws IOException {
		// 获得记录数目。
		int record_num = (int) (in.length() / Persons.RECORD_LENGTH);
		Persons[] p = new Persons[record_num];
		// 逆序读取。
		for (int i = record_num - 1; i >= 0; i--) {
			p[i] = new Persons();
			// 文件定位。
			in.seek(i * Persons.RECORD_LENGTH);
			p[i].readData(in, i + 1);
		}
		return p;
	}
}

class Persons {
	private String			name;

	private int				age;											// 4个字节

	private double			salary;										// 8个字节

	private String			married;

	public static final int	NAME_LENGTH		= 20;							// 姓名长度

	public static final int	MARRIED_LENGTH	= 2;							// 婚否长度

	public static final int	RECORD_LENGTH	= NAME_LENGTH * 2 + 4 + 8
													+ MARRIED_LENGTH * 2;

	public Persons() {}

	public Persons(String n, int a, double s) {
		name = n;
		age = a;
		salary = s;
		married = "F";
	}

	public Persons(String n, int a, double s, String m) {
		name = n;
		age = a;
		salary = s;
		married = m;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getSalary() {
		return salary;
	}

	public String getMarried() {
		return married;
	}

	public String setName(String n) {
		name = n;
		return name;
	}

	public int setAge(int a) {
		age = a;
		return age;
	}

	public double setSalary(double s) {
		salary = s;
		return salary;
	}

	public String setMarried(String m) {
		married = m;
		return married;
	}

	// 设置输出格式。
	public String toString() {
		return getClass().getName() + "[name=" + name + ",age=" + age
				+ ",salary=" + salary + ",married=" + married + "]";
	}

	// 写入一条固定长度的记录，即一个人的数据到输出流。
	public void writeData(DataOutput out) throws IOException {
		FixStringIO.writeFixString(name, NAME_LENGTH, out);
		out.writeInt(age);
		out.writeDouble(salary);
		FixStringIO.writeFixString(married, MARRIED_LENGTH, out);
	}

	// 写入一条固定长度的记录到随机读取文件中。
	private void writeData(RandomAccessFile out) throws IOException {
		FixStringIO.writeFixString(name, NAME_LENGTH, out);
		out.writeInt(age);
		out.writeDouble(salary);
		FixStringIO.writeFixString(married, MARRIED_LENGTH, out);
	}

	// 随机写入一条固定长度的记录到输出流的指定位置。
	public void writeData(RandomAccessFile out, int n) throws IOException {
		out.seek((n - 1) * RECORD_LENGTH);
		writeData(out);
	}

	// 从输入流随机读入一条记录，即一个人的数据。
	private void readData(RandomAccessFile in) throws IOException {
		name = FixStringIO.readFixString(NAME_LENGTH, in);
		age = in.readInt();
		salary = in.readDouble();
		married = FixStringIO.readFixString(MARRIED_LENGTH, in);
	}

	// 从输入流随机读入指定位置的记录。
	public void readData(RandomAccessFile in, int n) throws IOException {
		in.seek((n - 1) * RECORD_LENGTH);
		readData(in);
	}
}

// 对固定长度字符串从文件读出、写入文件
class FixStringIO {
	// 读取固定长度的Unicode字符串。
	public static String readFixString(int size, DataInput in)
			throws IOException {
		StringBuffer b = new StringBuffer(size);
		int i = 0;
		boolean more = true;

		while (more && i < size) {
			char ch = in.readChar();
			i++;
			if (ch == 0) {
				more = false;
			} else {
				b.append(ch);
			}
		}
		// 跳过剩余的字节。
		in.skipBytes(2 * (size - i));
		return b.toString();
	}

	// 写入固定长度的Unicode字符串。
	public static void writeFixString(String s, int size, DataOutput out)
			throws IOException {
		int i;
		for (i = 0; i < size; i++) {
			char ch = 0;
			if (i < s.length()) {
				ch = s.charAt(i);
			}
			out.writeChar(ch);
		}
	}
}

class FileRW {
	// 需要输入的person数目。
	public static int	NUMBER	= 3;

	public static void main(String[] args) {
		Person[] people = new Person[NUMBER];
		// 暂时容纳输入数据的临时字符串数组。
		String[] field = new String[4];
		// 初始化field数组。
		for (int i = 0; i < 4; i++) {
			field[i] = "";
		}
		// IO操作必须捕获IO异常。
		try {
			// 用于对field数组进行增加控制。
			int fieldcount = 0;

			// 先使用System.in构造InputStreamReader，再构造BufferedReader。
			BufferedReader stdin = new BufferedReader(new InputStreamReader(
					System.in));
			for (int i = 0; i < NUMBER; i++) {
				fieldcount = 0;
				System.out.println("The number " + (i + 1) + " person");
				System.out
						.println("Enter name,age,salary,married(optional),please separate fields by ':'");
				// 读取一行。
				String personstr = stdin.readLine();
				// 设置分隔符。
				StringTokenizer st = new StringTokenizer(personstr, ":");
				// 判断是否还有分隔符可用。
				while (st.hasMoreTokens()) {
					field[fieldcount] = st.nextToken();
					fieldcount++;
				}
				// 如果输入married，则field[3]不为空，调用具有四个参数的Person构造函数。
				if (field[3] != "") {
					people[i] = new Person(field[0],
							Integer.parseInt(field[1]), Double
									.parseDouble(field[2]), field[3]);
					// 置field[3]为空，以备下次输入使用。
					field[3] = "";
				}
				// 如果未输入married，则field[3]为空，调用具有三个参数的Person构造函数。
				else {
					people[i] = new Person(field[0],
							Integer.parseInt(field[1]), Double
									.parseDouble(field[2]));
				}
			}
			// 将输入的数据保存至“people.dat”文本文件中。
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("people.dat")));
			writeData(people, out);
			// 关闭流。
			out.close();
			// 从文件“people.dat”读取数据。
			BufferedReader in = new BufferedReader(new FileReader("people.dat"));
			Person[] inPeople = readData(in);
			// 关闭流。
			in.close();
			// 输出从文件中读入的数据。
			for (int i = 0; i < inPeople.length; i++) {
				System.out.println(inPeople[i]);
			}
		} catch (IOException exception) {
			System.err.println("IOException");
		}
	}

	// 将所有数据写入输出流。
	static void writeData(Person[] p, PrintWriter out) throws IOException {
		// 写入记录条数，即人数。
		out.println(p.length);
		for (int i = 0; i < p.length; i++) {
			p[i].writeData(out);
		}
	}

	// 将所有数据从输入流中读出。
	static Person[] readData(BufferedReader in) throws IOException {
		// 获取记录条数，即人数。
		int n = Integer.parseInt(in.readLine());

		Person[] p = new Person[n];
		for (int i = 0; i < n; i++) {
			p[i] = new Person();
			p[i].readData(in);
		}
		return p;
	}
}

class Person {
	private String	name;

	private int		age;

	private double	salary;

	private String	married;

	public Person() {}

	public Person(String n, int a, double s) {
		name = n;
		age = a;
		salary = s;
		married = "F";
	}

	public Person(String n, int a, double s, String m) {
		name = n;
		age = a;
		salary = s;
		married = m;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getSalary() {
		return salary;
	}

	public String getMarried() {
		return married;
	}

	// 设置输出格式。
	public String toString() {
		return getClass().getName() + "[name=" + name + ",age=" + age
				+ ",salary=" + salary + ",married=" + married + "]";
	}

	// 写入一条记录，即一个人的数据到输出流。
	public void writeData(PrintWriter out) throws IOException {
		// 格式化输出。
		out.println(name + ":" + age + ":" + salary + ":" + married);
	}

	// 从输入流读入一条记录，即一个人的数据。
	public void readData(BufferedReader in) throws IOException {
		String s = in.readLine();
		StringTokenizer t = new StringTokenizer(s, ":");
		name = t.nextToken();
		age = Integer.parseInt(t.nextToken());
		salary = Double.parseDouble(t.nextToken());
		married = t.nextToken();
	}
}

class FileStdRead {
	public static void main(String[] args) throws IOException {
		int b = 0;
		char c = ' ';
		System.out.println("请输入：");
		while (c != 'q') {
			int a = System.in.read();
			c = (char) a;
			b++;
			System.out.println((char) a);
		}
		System.err.print("counted    " + b + "    totalbytes.");
	}

}

// 读取输入的数据,直到数据中有Q这个字母然

class IOStreamExample {
	public static void main(String[] args) throws IOException {
		// 1. 读入一行数据:
		BufferedReader in = new BufferedReader(new FileReader(
				"FileStdRead.java"));
		String s, s2 = new String();
		while ((s = in.readLine()) != null) {
			s2 += s + "";
		}
		in.close();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.print("Enter a line:");
		System.out.println(stdin.readLine());
		// 2. 从内存中读入
		StringReader in2 = new StringReader(s2);
		int c;
		while ((c = in2.read()) != -1) {
			System.out.print((char) c);
		}
		// 3. 格式化内存输入
		try {
			DataInputStream in3 = new DataInputStream(new ByteArrayInputStream(
					s2.getBytes()));
			while (true) {
				System.out.print((char) in3.readByte());
			}
		} catch (EOFException e) {
			System.err.println("End of stream");
		}
		// 4. 文件输入
		try {
			BufferedReader in4 = new BufferedReader(new StringReader(s2));
			PrintWriter out1 = new PrintWriter(new BufferedWriter(
					new FileWriter("IODemo.out")));
			int lineCount = 1;
			while ((s = in4.readLine()) != null) {
				out1.println(lineCount++ + ": " + s);
			}
			out1.close();
		} catch (EOFException e) {
			System.err.println("End of stream");
		}
		// 5. 接收和保存数据
		try {
			DataOutputStream out2 = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream("Data.txt")));
			out2.writeDouble(3.14159);
			out2.writeUTF("That was pi");
			out2.writeDouble(1.41413);
			out2.writeUTF("Square root of 2");
			out2.close();
			DataInputStream in5 = new DataInputStream(new BufferedInputStream(
					new FileInputStream("Data.txt")));

			System.out.println(in5.readDouble());

			System.out.println(in5.readUTF());
			System.out.println(in5.readDouble());
			System.out.println(in5.readUTF());
		} catch (EOFException e) {
			throw new RuntimeException(e);
		}
		// 6. 随机读取文件内容
		RandomAccessFile rf = new RandomAccessFile("rtest.dat", "rw");
		for (int i = 0; i < 10; i++) {
			rf.writeDouble(i * 1.414);
		}
		rf.close();
		rf = new RandomAccessFile("rtest.dat", "rw");
		rf.seek(5 * 8);
		rf.writeDouble(47.0001);
		rf.close();
		rf = new RandomAccessFile("rtest.dat", "r");
		for (int i = 0; i < 10; i++) {
			System.out.println("Value " + i + ": " + rf.readDouble());
		}
		rf.close();
	}
}

/**
 * <p>
 * Title: JAVA进阶诀窍
 * </p>
 * 张峰 1.0
 */
class MakeDirectoriesExample {
	private static void fileattrib(File f) {
		System.out.println("绝对路径: " + f.getAbsolutePath() + "可读属性: "
				+ f.canRead() + "可定属性: " + f.canWrite() + "文件名: " + f.getName()
				+ "父目录: " + f.getParent() + "当前路径: " + f.getPath() + "文件长度: "
				+ f.length() + "最后更新日期: " + f.lastModified());
		if (f.isFile()) {
			System.out.println("输入的是一个文件");
		} else if (f.isDirectory()) {
			System.out.println("输入的是一个目录");
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			args = new String[3];
		}
		args[0] = "d";
		args[1] = "test1.txt";
		args[2] = "test2.txt";
		File old = new File(args[1]), rname = new File(args[2]);
		old.renameTo(rname);
		fileattrib(old);
		fileattrib(rname);
		int count = 0;
		boolean del = false;
		if (args[0].equals("d")) {
			count++;
			del = true;
		}
		count--;
		while (++count < args.length) {
			File f = new File(args[count]);
			if (f.exists()) {
				System.out.println(f + " 文件己经存在");
				if (del) {
					System.out.println("删除文件" + f);
					f.delete();
				}
			} else { // 如果文件不存在
				if (!del) {
					f.mkdirs();
					System.out.println("创建文件： " + f);
				}
			}
			fileattrib(f);
		}
	}
}
