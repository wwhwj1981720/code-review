package unicom.start;


import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


import function.FunctionInterface;
import function.FunctionJudge;


import lex.CppAnayase;
import lex.HtmlAnayase;
import lex.AspAnayase;
import lex.LexInterface;
import lex.lexAnayase;

import tool.MyStatic;
import unicom.CFile;
import unicom.FileInfo;
import unicom.FileViewer;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;

/**
 * @author Administrator
 *
 */
public class readMD {
	//List finfolist=new 
	Map<String,List<String>>  filetypelist=null;
	LexInterface lex;
	FunctionInterface fj;
	CFile result=new CFile("e:\\resulttest.csv");
	long totalrow=0;
	long totalvalrow=0;
	
	String wordpath="e:\\versionfunction\\wordlist12.txt";
	String md5path="e:\\versionfunction\\md512.txt";
	String funfile="e:\\versionfunction\\function12.txt";
	
	 
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
		
		String content=startlex.getContent();
		
		lex.wordAnalysis(content);
		
		//content=null;//减少引用
		lex.outputWord(wordpath);
		List<WordUnit> wordlist=lex.getWordlist();
		
		fj.setWordlist(wordlist);
		fj.recganize();
		
		List<String> funlist=fj.getFunlist();
		
	
		fi.setTotalrow(lex.getRownum());
		fi.setValrow(lex.getValideline());
		fi.setName(fname);
		fi.setFprint(MD5.makeMD5(content));
		fi.setFwprint(MD5.makeMD5Word(wordlist));
		fi.setFfprint(MD5.makeListMD5(funlist));//一页代码抽出的所有函数计算hash
		fj.outPutFunctionList(funfile);
		result.writeCsv(fi);
		totalrow+=lex.getRownum();
		totalvalrow+=lex.getValideline();
		System.out.println("--------------");
	
	}
//	
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
			result.initCsv();
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
							System.out.println("start---java");
							lex=new AspAnayase(); 
							fj=new FunctionJudge();
							for(String path:lf)
							{
								 reverseMd(path);
							}
							lex=null;
							fj=null;
						}
						else if(key.equals("cpp"))
						{
							System.out.println("start---cpp");
							lex=new CppAnayase(); 
							fj=new FunctionJudge();
							for(String path:lf)
							{
								 reverseMd(path);
							}
							lex=null;
							fj=null;
							System.out.println("start---cpp");
						}
						else if(key.equals("jsp"))
						{
							System.out.println("start---jsp");
							lex=new AspAnayase(); 
							fj=new FunctionJudge();
							for(String path:lf)
							{
								 reverseMd(path);
							}
							lex=null;
							fj=null;
							System.out.println("start---cpp");
						}
						else if(key.equals("js"))
						{
							System.out.println(key);
							lex=new lexAnayase(); 
							fj=new FunctionJudge();
							for(String path:lf)
							{
								 reverseMd(path);
							}
							lex=null;
							fj=null;
						}
						else
						{
							
						}
						
					}
					result.writeCsvTotal(totalrow,totalvalrow);
				}
		}
		else 
		{
			//只统计特定后缀的
			if(suffix!=null)
			{
			
				List<String> sl=null;
				sl=filetypelist.get(suffix);
				if(sl!=null) 
				{
					lex=new HtmlAnayase(); 
					fj=new FunctionJudge();
					for(String path:sl)
					{
						 reverseMd(path);
					}
					lex=null;
					fj=null;
				}
				result.writeCsvTotal(totalrow,totalvalrow);
			}
		}
	}
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException
	{
		String mdr = "E:\\versionfunction\\cpp";
		mdr=args[0];
		String suffix = "java";
		
		suffix=args[1];
		System.out.println(mdr+"|"+suffix);
		MyStatic.readFileSet("binary/file.txt");
		readMD rd = new readMD();
		rd.scanDirectry(mdr);
		rd.travelMap(suffix);
		System.out.println("end");	
		
	}

}
