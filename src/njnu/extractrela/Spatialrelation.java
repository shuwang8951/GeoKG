package njnu.extractrela;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import path.PathConfig;
import ICTCLAS.I3S.AC.ICTCLAS;
import njnu.extractloc.LocExtract;
import njnu.utility.FileProcessing;
import njnu.utility.TextProcessing;

public class Spatialrelation {
	
	
	/**
	 * signal word collection
	 */
	public static ArrayList<String> signals=null;
	
	/**
	 * spatial relation rules collection
	 */
	public static ArrayList<String> srrules=null;
	
	
	/**
	 * spatial relation collection
	 */
	public static ArrayList<String> spatialrelation=new ArrayList<String>();
	
	//构造函数
	public Spatialrelation(){
		super();
		signals=FileProcessing.getruleconfig(PathConfig.Relation_Signal);
		srrules=FileProcessing.getruleconfig(PathConfig.Relation_Rules);
	}
		
	
	/**
	 * extract spatial relations from paragraph 
	 * @param paragraph,String type 
	 * @return ArrayList in SpatialRelation type
	 */
	public ArrayList<String> extractSR(String paragraph){
		ArrayList<String> sr = new ArrayList<String>();
		//分句
		ArrayList<String> sentences=TextProcessing.paraSplit(paragraph);
		
		//过滤地名语句
		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0;i<sentences.size();i++){
			if(LocExtract.containLoc(sentences.get(i))==false){
				temp.add(sentences.get(i));
			}
		}
		for(String t:temp){
			for(int m=0;m<sentences.size();m++){
				if(sentences.get(m).equals(t)){
					sentences.remove(m);
				}
			}
		}
		temp.clear();
		
		//过滤空间关系词汇语句
		ArrayList<String> temps = new ArrayList<String>();
		for(int i=0;i<sentences.size();i++){
			if(containSignal(sentences.get(i))==false){
				temps.add(sentences.get(i));
			}
		}
		for(String t:temps){
			for(int m=0;m<sentences.size();m++){
				if(sentences.get(m).equals(t)){
					sentences.remove(m);
				}
			}
		}
		temps.clear();
		
		ArrayList<String> splitsentences=new ArrayList<String>();
		for(int i=0;i<sentences.size();i++){
			splitsentences.add(sentences.get(i));
		}
		//标注空间关系词汇
		for(int i=0;i<splitsentences.size();i++){
			splitsentences.set(i, TextProcessing.ictclasSplit(splitsentences.get(i)));
			splitsentences.set(i, splitsentences.get(i).replaceAll("/un", "/signal"));
			//System.out.println(splitsentences.get(i));
		}
		
		//根据规则去除非法空间关系语句
		ArrayList<String> rt=new ArrayList<String>();
		for(int i=0;i<splitsentences.size();i++){
			if(containRelation(splitsentences.get(i))!=true){
				rt.add(sentences.get(i));
			}
		}
		for(int i=0;i<sentences.size();i++){
			for(String rttemp:rt){
				if(sentences.get(i).equals(rttemp)){
					sentences.remove(i);
				}
			}
		}
			
//		//访问控制台输出
//		System.out.println(paragraph);
//		System.out.println("包含以下空间关系");
//		for(String s:sentences){
//			System.out.println(s);
//			System.out.println("-------------------------------");
//		}
		
		//存入空间关系列表
		for(String s:sentences){
						
			try{
				sr.add(s);
			}catch(Exception e){
				System.out.println(e.toString());
			}	
		}
		return sr;
	}
	
	
	/**
	 * extract spatial relations from paragraph with no parameter out
	 * @param paragraph,String type 
	 * @return ArrayList in SpatialRelation type
	 */
	public void extractSR2static(String paragraph){
		ArrayList<String> sr = new ArrayList<String>();
		//分句
		ArrayList<String> sentences=TextProcessing.paraSplit(paragraph);
		
		//过滤地名语句
		ArrayList<String> temp = new ArrayList<String>();
		for(int i=0;i<sentences.size();i++){
			if(LocExtract.containLoc(sentences.get(i))==false){
				temp.add(sentences.get(i));
			}
		}
		for(String t:temp){
			for(int m=0;m<sentences.size();m++){
				if(sentences.get(m).equals(t)){
					sentences.remove(m);
				}
			}
		}
		temp.clear();
		
		//过滤空间关系词汇语句
		ArrayList<String> temps = new ArrayList<String>();
		for(int i=0;i<sentences.size();i++){
			if(containSignal(sentences.get(i))==false){
				temps.add(sentences.get(i));
			}
		}
		for(String t:temps){
			for(int m=0;m<sentences.size();m++){
				if(sentences.get(m).equals(t)){
					sentences.remove(m);
				}
			}
		}
		temps.clear();
		
		ArrayList<String> splitsentences=new ArrayList<String>();
		for(int i=0;i<sentences.size();i++){
			splitsentences.add(sentences.get(i));
		}
		//标注空间关系词汇
		for(int i=0;i<splitsentences.size();i++){
			splitsentences.set(i, TextProcessing.ictclasSplit(splitsentences.get(i)));
			splitsentences.set(i, splitsentences.get(i).replaceAll("/un", "/signal"));
			//System.out.println(splitsentences.get(i));
		}
		
		//根据规则去除非法空间关系语句
		ArrayList<String> rt=new ArrayList<String>();
		for(int i=0;i<splitsentences.size();i++){
			if(containRelation(splitsentences.get(i))!=true){
				rt.add(sentences.get(i));
			}
		}
		for(int i=0;i<sentences.size();i++){
			for(String rttemp:rt){
				if(sentences.get(i).equals(rttemp)){
					sentences.remove(i);
				}
			}
		}
		
		//存入空间关系列表
		for(String s:sentences){
						
			try{
				sr.add(s);
			}catch(Exception e){
				System.out.println(e.toString());
			}	
		}
		this.spatialrelation=sr;
	}
	
	
	
	
	/**
	 * whether the text contains signal word
	 * @param text
	 * @return true-contain; false- do not cotain
	 */
	private static boolean containSignal(String text){
		boolean cs=false;
		if(signals.isEmpty()!=true){
			for(String s:signals){
				if(text.contains(s)==true){
					cs=true;
					break;
				}
			}
		}		
		return cs;
	}
	
	/**
	 * check whether the split sentence contain relation
	 * @param splitsentence
	 * @return
	 */
	private static boolean containRelation(String splitsentence){
		boolean cr=false;
		if(srrules.isEmpty()!=true){
			for(int i=0;i<srrules.size();i++){
				Pattern p=Pattern.compile(srrules.get(i));
				Matcher m=p.matcher(splitsentence);
				if(m.find()==true){
					cr=true;
				}
			}
		}
		return cr;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Spatialrelation sr=new Spatialrelation();
		String temp="位于内蒙古自治区境内大兴安岭中段西麓";
		
		ArrayList<String> srl=sr.extractSR(temp);
		for(String srtext:srl){
			System.out.println(srtext);
		}

	}

}
