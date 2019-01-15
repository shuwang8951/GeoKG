package path;

import java.io.File;

import ICTCLAS.I3S.AC.ICTCLAS;

public class PathConfig {
	/**
	 * ICTCLAS PATH
	 */
	//ICTCLAS.dll名字
	public static String ICTCLAS_NAME="ICTCLAS50.dll";
	//ICTCLAS文本分词文件夹根目录
	public static String ICTCLAS_ROOT="./files/ictclasfiles/";
	//ICTCLASdll Path
	public static String ICTCLAS_DLLPATH=new File("").getAbsolutePath()+"\\files\\ictclasfiles\\"+ICTCLAS_NAME;
	
	
	
	/**
	 * SPATIAL RELATION PATH
	 */
	public static String Relation_Signal="./files/relation/spatialrelationfiles/SpatialRelationSignals";
	
	public static String Relation_Rules="./files/relation/spatialrelationfiles/SpatialRelationRules";
	
	
	
	/**
	 * corpus path
	 * @param args
	 */
	public static String TESTCORPUSFILESPATHS="./files/corpus";
	
	public static String CORPUSFILESPATHS="./files/allcorpus";
	
	public static String CORPUSESUMFILESPATHS="./files/corpusEsum";
	
	
	/**
	 * NEO4J  database path
	 * @param args
	 */
	public static File NEO4JDATABASEPATH=new File("./neo4jdatabase/databases/GeoKG.db");
	
	
	public static void main(String[] args) {
		System.out.println(ICTCLAS_DLLPATH);
	}

}
