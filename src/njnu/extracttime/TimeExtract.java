package njnu.extracttime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import njnu.models.*;
import njnu.utility.JudgeTimeType;
import njnu.utility.Tools;

public class TimeExtract {
	// 训练文件存放位置
	static String inputpath = "D:\\JavaFX MyEclipse\\JavaFX\\example";
	static String entity_name = "";
	
	
	public static String[] filelist;
	
	static int totalnum = 0;
	// exe和model所在目录
	public static File workDir = new File(System.getProperty("user.dir") + "\\files\\lib");

	public static String cmd;

	public static String noun[] = { "今天", "一周", "今年" };

	public static String bef[] = { "最近", "近", "头", "大约", "约",
			// "在",
			"自", "前", "不到", "直到", "持续", "此前", "到了", "截止到", "截止", "到", "过去", "截至", "今后", "早在", "连续", "始于", "随后", "未来",
			"一连", "约为", "整整", "至少", "最后", "过去的", "不超过",
			
			"光绪",

	};

	public static String beh[] = { "间", "后", "前夕", "前后",
			// "前",
			"初期", "以来", "左右", "以来", "底", "初", "半", "以前", "季节", "许", "内", "至今", "底前", "以后",
			// "来",
			"末期", "早期", "末", "起", "多", "期间", "起", "后期", "多来", "之内", "之后", "之前", "时间内", "起", "之前", "之间", "之际", "以上", };

	public static String be[][] = { { "近", "来" }, { "从", "开始" }, { "从", "起" }, { "于", "起" }, { "自", "起" },
			{ "自", "以来" }, { "自从", "以来" }, { "从", "至今" }, { "在", "前后" }, { "在", "来临之际" }, { "在", "中" }, { "进入", "以来" },
			{ "进入", "后" }, { "“", "”", }, { "在", "里", }, { "自", "至今", }, { "直至", "起", }, { "自从", "起", }, { "「", "」", },
			{ "到", "为止", }, { "从", "以来", }, { "从", "开始", }, { "自", "开始", }, { "在", "之前", }, { "在", "前", }, };

	public static String be2[][] = { { "自", "至" }, { "从", "到" }, { "从", "起到" }, { "在", "至" }, { "自", "一直到" },
			{ "由", "起至" }, { "从", "开始到" }, { "从", "持续到" }, };

	public static String be3[][] = { { "至", "间" }, { "至", "内" }, { "起至", "期间" }, { "与", "之间" }, };

	public static String be4[] = { "--", "到", "至", "－", "~", "-", "－", "、", "和", "～", "—", "的", "--", "或", "±", "直到",
			"及", "乃至", "前的", "起至", "起至", "一直到", "结束之前", "前" };
	
	
	
	/**
	 * 将字符串转存为crf测试所需的文本格式
	 * @param str 待处理字符串
	 * @param fileoutput 输出文件
	 */
	public static void StringToTxt(String str, File fileoutput) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileoutput));

			for (int i = 0; i < str.length(); i++) {
				out.write(str.substring(i, i + 1));
				out.newLine();
				out.flush(); // 把缓存区内容压入文件
			}

			out.close(); // 关闭文件
		}

		catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	
	/**
	 * 
	 * @param str
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static List<String> fore(String str) throws Exception {

		String filename="tempfile";
		// 测试数据
		File file = new File(workDir + "\\test.data");
		// 测试数据转为crf识别格式
		str=CtoH(str);
		str = str.replaceAll("    &nbsp", "");
		str = str.replaceAll(" &nbsp", "");
		str = str.replaceAll(" ", "");
		StringToTxt(str, file);
		// 使用crf模型进行识别
		cmd = "cmd.exe /c crf_test.exe -m model0426 test.data > " + filename;// 参数中的分割符需使用'\'
		Process p = null;
		p = Runtime.getRuntime().exec(cmd, null, workDir);// null部分是环境变量，这里没有给出说明
		p.waitFor();

		/* 对识别结果进行处理 */
		File fin = new File(workDir + "\\" + filename);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		// int forecastnum=0;//预测数目
		String line = null;
		int linenum = 0;// 行数
		String s = "";
		String forecastMark = "";// 存储预测的字母序列

		ArrayList<Integer> forecastList = new ArrayList<Integer>();
		// Step1.获取结果文本到字符串
		while ((line = br.readLine()) != null) {
			if (!line.trim().equals("")) {
				s = s + line.substring(0, 1);
				forecastMark = forecastMark + line.substring(2, 3);
				linenum++;
			}

		}
		br.close();

		StringBuffer fMark = new StringBuffer(forecastMark);

		for (int k = 0; k < noun.length; k++) {
			int index = s.indexOf(noun[k]);
			if (index != -1 && fMark.charAt(index) != 'B') {
				fMark.replace(index, index + 1, "B");
				fMark.replace(index + 1, index + 2, "E");
			}
		}

		// Step2.将多个连续时间单元（时间+时间+时间）合并为一个时间。
		for (int i = 0; i < fMark.length() - 1; i++) {
			if (fMark.charAt(i) == 'B' && fMark.charAt(i + 1) == 'B') {
				fMark.replace(i + 1, i + 2, "I");
			}
		}

		for (int i = 0; i < fMark.length() - 1; i++) {
			if (fMark.charAt(i) == 'E' && fMark.charAt(i + 1) == 'B') {
				fMark.replace(i, i + 1, "I");
				if (fMark.charAt(i + 2) == 'S') {
					fMark.replace(i + 1, i + 2, "E");
				} else
					fMark.replace(i + 1, i + 2, "I");

			}
		}

		int fMarknum = 0;
		for (int i = 0; i < fMark.length(); i++) {
			if (fMark.charAt(i) == 'B') {
				fMarknum++;
				forecastList.add(i);
			}

		}

		forecastList.add(linenum);

		int[][] flist = new int[fMarknum][2];
		Integer[] forecastvalues = new Integer[forecastList.size()];
		forecastList.toArray(forecastvalues);

		// 获取预测起始位置
		for (int i = 0; i < fMarknum; i++) {
			flist[i][0] = forecastvalues[i];
			for (int j = forecastvalues[i]; j < forecastvalues[i + 1]; j++) {
				if (fMark.charAt(forecastvalues[i] + 1) == 'S') {
					flist[i][1] = forecastvalues[i];
					break;
				}
				if (fMark.charAt(j) == 'E') {
					flist[i][1] = j;
					break;
				}
			}

		}
		List<String> str2 = new ArrayList<String>();

		for (int i = 0; i < flist.length; i++) {
			str2.add(s.substring(flist[i][0], flist[i][1] + 1));
		}
		File fileDelete = new File(workDir + "\\" + filename);
		if (fileDelete.exists()) {
			fileDelete.delete();
		}
		return str2;
	}
	
	//全角转半角
	public static String CtoH(String str) {

		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 12288) {
				result += (char) (str.charAt(i) - 12256);
				continue;
			}
			if (str.charAt(i) > 65293 && str.charAt(i) < 65306)
				result += (char) (str.charAt(i) - 65248);
			else
				result += (char) (str.charAt(i));
		}

		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		TimeExtract timeextract = new TimeExtract();
		String temp="始设昌图厅，1913 年改昌图县，沿袭至今";
		List<String> timel=timeextract.fore(temp);
		System.out.println(timel.size());
		for(String time:timel){
			System.out.println(time);
		}
	}
	
}
