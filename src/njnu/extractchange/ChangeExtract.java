package njnu.extractchange;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import njnu.extractattr.AttrExtract;

/**
 * 利用正则通过设置规则识别关系
 * @author ws
 *
 */
public class ChangeExtract {
	

	//关键词在中间的匹配模式
	public static String[] Begin = {"改为", "改名" , "改称", "划归" };
	//关键词在中间的匹配模式
	public static String[]  End= {"改府" ,"改县","改市","改区"};
	//关键词在中间的匹配模式
	public static String[][] Combine = {{ "设", "县" }, { "设", "市" },};
			
	public static List<String> getChange(String str) {
		List<String> list = new ArrayList<String>();
		
		str=str.replaceAll(" ", "");
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

			}
						
		}
		//字符在两边的正则表达式
		for (int k = 0; k < Combine.length; k++) {
				String comb = Combine[k][0] + "(.*)" + Combine[k][1];
				Pattern pt = Pattern.compile(comb);
				Matcher match = pt.matcher(str);
				if (match.find()) {
					String st = match.group();
					list.add(st);
					
				}
			}
		return list;
	}

	public static void main(String[] args) {
		ChangeExtract ae=new ChangeExtract();
		String temp= "1902 年改府，1913 年改县。 1922 年县境南部设阿瓦提县佐，1930 年阿瓦提县析出，1958 年曾将温宿县 撤并于阿克苏县，1962 年温宿县恢复。1983 年改为自治区直辖市，1984 年 改为县级市。";
		List<String> tempal=ae.getChange(temp);
		System.out.println(tempal.size());
		for(String a:tempal){
			System.out.println(a);
		}
		
	}
}
