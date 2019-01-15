package njnu.utility;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ICTCLAS.I3S.AC.ICTCLAS;

public class TextProcessing {
	/**
	 * split the sentence
	 * @param sentence, the splitting sentences
	 * @return the split result of sentence using String type
	 */
	public static String ictclasSplit(String sentence){
		String splitstr = "";
		try{
			ICTCLAS ictpara=ICTCLAS.getInstance() ;
			splitstr=ictpara.paraProcess(sentence);
		}catch(Exception e){
			System.out.println(e.toString());
			System.out.println("ictclas分词过程有误！");
		}
		if(splitstr.equals("")){
			return sentence;
		}else{
			return splitstr;
		}	
	}
	
	
	/**
	 * split paragraph to sentences
	 * @param processing paragraph
	 * @return ArrayList<String> sentences
	 */
	public static ArrayList<String> paraSplit(String paragraph){
		ArrayList<String> sentences=new ArrayList<String>();
		String[] temp=paragraph.split("[，；。？！\n]");
		for(String s:temp){
			sentences.add(s);
		}
		return sentences;
	}
	
	/**
	 * 清除文本中多余空格
	 * @param paragraph
	 * @return
	 */
	public static String deleteSpace(String paragraph){
		String temp=paragraph.replaceAll(" ", "");
		temp=temp.replaceAll("	", "");
		temp=temp.replaceAll(" ", "");
		return temp;
	}
	
	/**
	 * 删除标题
	 * @param txt
	 * @return
	 */
	public static String deleteHead(String txt) {
		String line = txt;
		String pattern = "(.*?)(\\）)(.*$)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if (m.find()) {
			line = m.group(3);
			
		}
		return line;
	}


	
	

}
