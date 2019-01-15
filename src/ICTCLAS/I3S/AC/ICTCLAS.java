/**
 * 实现ICTCLAS50.dll的JNI接口
 */
package ICTCLAS.I3S.AC;

import path.PathConfig;

/**
 * @author liming
 * 更新时间：2014/2/22
 */
public class ICTCLAS {
	
	private static ICTCLAS ictclas=new ICTCLAS();
	
	public static ICTCLAS  getInstance(){
		return ictclas;
	}
	

	private ICTCLAS50 ictclas50;
	private String arg ;
	
	/**
	 * 构造函数初始化分词类及用户字典，标注集
	 * @throws Exception
	 */
	private ICTCLAS(){
		//初始化路径
		arg =PathConfig.ICTCLAS_ROOT;
		//初始化接口
		this.ictclas50 = new ICTCLAS50();

		//分词初始化
		try
		{
			//System.out.println(arg);
			if (ictclas50.ICTCLAS_Init(arg.getBytes("GB2312")) == false)
			{
				System.out.println("ICTCLAS初始化失败...");
				return;
			}
			
			//用户字典路径  导入用户字典
			String usrdir = arg+"/"+"userdict.txt"; 
			//将string转化为byte类型
			byte[] usrdirb = usrdir.getBytes();
			//导入用户字典,返回导入用户词语个数第一个参数为用户字典路径，第二个参数为用户字典的编码类型
			ictclas50.ICTCLAS_ImportUserDictFile(usrdirb, 3);
			//设置词性标注集(0 计算所二级标注集，1 计算所一级标注集，2 北大二级标注集，3 北大一级标注集)
			ictclas50.ICTCLAS_SetPOSmap(0);//标注标准
			ictclas50.ICTCLAS_SaveTheUsrDic();//保存用户字典
		}
		catch(Exception ex){
			System.out.println("ICTCLAS Initialized Failed...");
			ictclas=null;
		}
	}
	
	/**
	 * 功能：语句分词
	 * @param inputstr:待分词语句
	 * @return :分词后的语句
	 */
	public String paraProcess(String inputstr)
	{
		String nativeStr="";
		try
		{
			byte nativeBytes[] = ictclas50.ICTCLAS_ParagraphProcess(inputstr.getBytes("GB2312"), 2, 3);//第一个数字是字符编码类型。第二个0就是不标词性非0就标。
			nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
			return  nativeStr;	
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return  nativeStr;
	}
	
	/**
	 * 释放分词组件资源
	 */
	public void release(){//释放分词组件资源
		ictclas50.ICTCLAS_Exit();
	}

	/**
	 * 测试用例--测试通过，此类可用
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ICTCLAS tm;
		try {
			String str;
			tm = new ICTCLAS();
			String sInput = "震源深度为六公里";
			str=tm.paraProcess(sInput);//同testimportuserdict和testSetPOSmap
			System.out.println(str);
			tm.release();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
