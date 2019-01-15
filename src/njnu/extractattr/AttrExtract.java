package njnu.extractattr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njnu.utility.*;
import njnu.models.*;
/**
 * 
 * @author zhangxiao
 *
 */
public class AttrExtract {
	
		//训练文件存放位置

		//关键词在中间的匹配模式
		public static String[] Mid = { "占","驻"};
		//关键词在中间的匹配模式
		public static String[] Begin = {"简称", "北纬", "东经", "省会", "特产", "又名", "被名","亦称" };
		//关键词在中间的匹配模式
		public static String[]  End= {"之称" ,"之一"};
		//关键词在中间的匹配模式
		public static String[][] Combine = {  { "面积", "万平方公里" }, { "人口", "万" }, { "人口总数", "万" }, { "年降水量", "毫米" }, { "宽约", "米" },
				{ "年均温", "℃" }, { "人口平均密度为", "人" }, { "年发电", "万度" }, { "发电量为", "亿度" }, { "面积", "平方公里" }, { "海拔", "米" },
				{ "全长", "公里" }, { "流量", "立方米/秒" }, { "宽", "公里" }, { "面积达", "平方公里" },{"以","为"}  };
		
		/**
		 *判断有无数字
		 * @param content 输入字符串
		 * @return 若为数字返回true，否则返回false
		 */
		public static boolean HasDigit(String content) {
			boolean flag = false;
			Pattern p = Pattern.compile(".*\\d+.*");
			Matcher m = p.matcher(content);
			if (m.matches()) {
				flag = true;
			}
			return flag;
		}
		/**
		 * 
		 * @param str
		 * @return
		 */
		public static List<String> getAttribute(String str) {
			List<String> list = new ArrayList<String>();
			// 字符在中间的正则表达式
			for (int k = 0; k < Mid.length; k++) {
				//以数字为结尾的规则
				String digitend = "\\W{0,}" + Mid[k] + "\\d{0,}";
				//以文本为结尾的规则
				String txtend = "\\W{0,}" + Mid[k] + ".*";
				// System.out.println(www);
				Pattern pt = Pattern.compile(digitend);
				Matcher match = pt.matcher(str);
				Matcher matchtxt = pt.matcher(str);
				if (match.find() && HasDigit(match.group())) {
					String st = match.group() + "%";
					list.add(st);
					
				}else if(matchtxt.find()){
					list.add(match.group());
					
				}
			}
			// 字符在后面的正则表达式
			for (int k = 0; k < Begin.length; k++) {
				
				String regularbegin =Begin[k] + "(.*)";
				
				Pattern pt = Pattern.compile(regularbegin);	
				Matcher match = pt.matcher(str);
				if (match.find()) {
					String st = match.group();
					list.add(st);
					
				}
				
			}
			// 字符在前面的正则表达式
			for (int k = 0; k < End.length; k++) {
							
				String regularbegin ="(.*)"+End[k];
							
				Pattern pt = Pattern.compile(regularbegin);	
				Matcher match = pt.matcher(str);
				if (match.find()) {
					String st = match.group();
					list.add(st);
					//System.out.println(st);
				}
							
			}
			//字符在两边的正则表达式
			for (int k = 0; k < Combine.length; k++) {
					String comb = Combine[k][0] + "[\\W|\\w]{0,}" + Combine[k][1];
					Pattern pt = Pattern.compile(comb);
					Matcher match = pt.matcher(str);
					if (match.find() && HasDigit(match.group())) {
						String st = match.group();
						list.add(st);
						
					}
				}
			return list;
		}
		
		@SuppressWarnings("resource")
		public static void main(String[] args) {
			AttrExtract ae=new AttrExtract();
			String temp= "亦称回城。";
			List<String> tempal=ae.getAttribute(temp);
			System.out.println(tempal.size());
			for(String a:tempal){
				System.out.println(a);
			}
			
		}
}