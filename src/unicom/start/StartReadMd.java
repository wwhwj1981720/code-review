package unicom.start;


import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


import factory.Factory;

import lex.CppAnayase;
import lex.HtmlAnayase;
import lex.AspAnayase;
import lex.LexInterface;
import lex.lexAnayase;

import thread.cppThread;
import thread.defaultThread;
import thread.javaThread;
import thread.jsThread;
import thread.jspThread;
import tool.MyStatic;
import unicom.CFile;
import unicom.FileViewer;

/**
 * @author wangwh
 *   filetypelist 文件的类型目录
 *
 */
public class StartReadMd {
	//List finfolist=new 
	long totalrow=0;
	long totalvalrow=0;
	Map<String,List<String>>  filetypelist=null;
	CFile totalresult=new CFile("e:\\resulttest.csv");
	
	

	 

	/**
	 * 扫描一个目录下面的文件，并且按照类型划分存在 Map<String,List<String>>
	 * key 是 文件类型 
	 * value 是文件列表
	 */
	public void scanDirectry(String path)
	{

		 String suffix="java";
		 filetypelist=FileViewer.getListFiles(path,true);
		 CFile out=new CFile("e:\\testout.txt");
		 out.writeMap(filetypelist);
		 try {
			totalresult.initCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @author wangwh 
	 *   suffix 是文件的后缀名
	 *   all表示所有的都统计 
	 *
	 */
	public void travelMap(String suffix) throws NoSuchAlgorithmException, IOException
	{
		if(suffix.equals("all"))
		{ 
			if(filetypelist!=null) 
			{
					for (String key:filetypelist.keySet()) 
					{
						List<String> lf=filetypelist.get(key);//每个一个大类
						if(key.equals("java"))
						{
							javaThread javathread=new javaThread(lf,totalresult);
							javathread.start();
						}
						else if(key.equals("cpp"))
						{
							cppThread cppthread=new cppThread(lf,totalresult);
							cppthread.start();
						}
						else if(key.equals("jsp"))
						{
							jspThread jspthread=new jspThread(lf,totalresult);
							jspthread.start();
						}
						else if(key.equals("js"))
						{
							jsThread jsthread=new jsThread(lf,totalresult);
							jsthread.start();
						}
						else
						{
							defaultThread defthread=new defaultThread(lf,totalresult,key);
							defthread.start();
						}
						
					}
					//result.writeCsvTotal(totalrow,totalvalrow);
				}
		}
		else 
		{
			//只统计特定后缀的
			if((suffix!=null)&&(!suffix.equals("")))
			{
				List<String> lf=filetypelist.get(suffix);//每个一个大类
				javaThread thread=(javaThread)Factory.createThreadObject(suffix);
				thread.setLf(lf);
				thread.setTotalresult(totalresult);
				thread.start();
			}
		}
	}
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException
	{
		String mdr = "E:\\versionfunction\\cpp";
		mdr=args[0];
		String suffix = "all";
		suffix=args[1];
		System.out.println(mdr+"|"+suffix);
		MyStatic.readFileSet("binary/file.txt");
		StartReadMd rd = new StartReadMd();
		rd.scanDirectry(mdr);
		rd.travelMap(suffix);
		System.out.println("end");	
		
	}

}
