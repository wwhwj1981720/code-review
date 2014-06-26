package test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


import unicom.CFile;
import unicom.FileInfo;
import unicom.FileViewer;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;

public class testbinary {
	Map<String,List<String>>  filetypelist=null;
	CFile result=new CFile("e:\\dirinfo.csv");
	long c=0;
	
	public void scanDirectry(String path)
	{

		 String suffix="java";
		 filetypelist=FileViewer.getListFiles(path,true);
		 CFile out=new CFile("e:\\testout.txt");
		 //out.writeMap(filetypelist);
		 try {
			result.initCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.writeFileMap(filetypelist);
		
	}
	
	public void travelMap()
	{
		Map<String,List<String>> map=this.filetypelist;
		if(map!=null) 
		{
			for ( String key : map.keySet()) 
			{
				List<String> lf=map.get(key);
				for(String s:lf)
				{
					try {
						c++;
						//writeCountRow(c,s);
						try {
							reverseMd(s);
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static boolean emptyNotPrintChar(String str) {

		boolean flag = true;
		byte[] bts = str.getBytes();
		int btsLength = bts.length;
		byte[] newBytes = new byte[btsLength];
		for (int i = 0; i < btsLength; i++) {

			byte b = bts[i];
			if ((b >= 14 && b <= 31) || b == 127) {
				flag = false;
				break;
			}

			// newBytes[i]=b;
		}
		return flag;
	}
	public void reverseMd(String path) throws IOException, NoSuchAlgorithmException
	{
		
		System.out.println(path);
		FileInfo fi=new FileInfo();
		String fname="";
		fi.setPath(path);
		File fin=new File(path);
		if(fin!=null) fname=fin.getName(); 
		
		ReadFileStartLex startlex=new ReadFileStartLex(path);
		startlex.readFileContent();
		
		//String content=startlex.getContent();
		String istxt="否";
		if(startlex.isTxt())	istxt="是";
				
		//if(emptyNotPrintChar(content)) 
		//result.writeRow()//
		result.writeCountFile(c,path,istxt);
	
	
	}
	public static void main(String args[])
	{
		String Dir="e:\\软件代码";
		testbinary test=new testbinary();
		test.scanDirectry(Dir);
		test.travelMap();
		System.out.println("end");
		
	}
}
