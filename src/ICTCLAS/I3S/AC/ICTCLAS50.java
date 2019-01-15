package ICTCLAS.I3S.AC;

import java.io.*;

import path.PathConfig;


public class ICTCLAS50
{	
	public native boolean ICTCLAS_Init(byte[] sPath);
	public native boolean ICTCLAS_Exit();
	public native int ICTCLAS_ImportUserDictFile(byte[] sPath,int eCodeType);
	public native int ICTCLAS_SaveTheUsrDic();
	public native int ICTCLAS_SetPOSmap(int nPOSmap);
	public native boolean ICTCLAS_FileProcess(byte[] sSrcFilename, int eCodeType, int bPOSTagged,byte[] sDestFilename);
	public native byte[] ICTCLAS_ParagraphProcess(byte[] sSrc, int eCodeType, int bPOSTagged);
	public native byte[] nativeProcAPara(byte[] sSrc, int eCodeType, int bPOStagged);
	/* Use static intializer */
	
	static
	{
		String dllPath =PathConfig.ICTCLAS_DLLPATH;
//		System.out.println(dllPath);
		File file=new File(dllPath);
		if(file.exists())
		{
			try{
				System.load(dllPath);	
			}
			catch(Exception e){
				System.out.println("加载失败..."+e.toString());
			}
		}
		else{
			System.out.println(dllPath+"不存在...");
		}
		
	}
}


