package njnu.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileProcessing {
	
	/**
	 * 从指定路径读取文件列表名称
	 * @param path
	 * @return
	 */
	public static String[] getfile(String path) {
        File file=new File(path); 
        String[] filelist=file.list();
         
        return filelist;
	}
	
	/**
	 * 从路径读取txt
	 * @param filepath
	 * @return
	 */
	public static String readFile(String filepath){
		String filetext="";
		if(filepath != null && !"".equals(filepath.trim())) {
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)),"UTF-8"));
				StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
				}
				reader.close();
				filetext=stringBuffer.toString();

			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return filetext;
	}
	
	
	
	/**
	 * read txt config files
	 * @param path, which locates the file.
	 * @return ArrayList<String> list read in line
	 */
	public static ArrayList<String> getruleconfig(String path){
		ArrayList<String> temp=new ArrayList<String>();
		if(path != null && !"".equals(path.trim())) {
			try{
				StringBuilder Builder = new StringBuilder();			
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));
				String str = reader.readLine();		
				while(str != null && !"".equals(str)){
					Builder.append(str);
					Builder.append("\n");
					str = reader.readLine();
				}
				reader.close();	
				String[] arrays = Builder.toString().split("\n");	
				for(String element : arrays){
					temp.add(element);
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return temp;
	}
}
