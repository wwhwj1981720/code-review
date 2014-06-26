package thread;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import function.FunctionInterface;
import function.FunctionJudge;

import lex.AspAnayase;
import lex.LexInterface;
import unicom.CFile;
import unicom.CountTotal;
import unicom.FileInfo;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;

public class javaThread extends Thread {
	
	LexInterface lex;
	FunctionInterface fj;
	List<String> lf;
	CFile result=new CFile("e:\\javaresulttest.csv");//每个结果分别输出
	CFile totalresult=null;
	long totalrow=0;
	long totalvalrow=0;
	
	public javaThread(List<String> lf,CFile totalresult) {
		super();
		this.lf = lf;
		this.totalresult=totalresult;
		try {
			result.initCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<String> getLf() {
		return lf;
	}
	public javaThread() {
		super();
	}
	public void setLf(List<String> lf) {
		this.lf = lf;
	}
	public CFile getTotalresult() {
		return totalresult;
	}
	public void setTotalresult(CFile totalresult) {
		this.totalresult = totalresult;
	}
	/**
	 * @param args
	 */
	
	
	String wordpath="e:\\versionfunction\\javawordlist12.txt";
	String md5path="e:\\versionfunction\\javamd512.txt";
	String funfile="e:\\versionfunction\\javafunction12.txt";
	public void countFile()
	{
		CountTotal ct=new CountTotal();
		ct.setTotalrow(totalrow);
		ct.setTotalvalrow(totalvalrow);
		try {
			result.writeCsvTotal(totalrow,totalvalrow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run()
	{
		System.out.println("start---java");
		lex=new AspAnayase(); 
		fj=new FunctionJudge();
		for(String path:lf)
		{
			 try {
				reverseMd(path);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lex=null;
		fj=null;
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
		synchronized(totalresult)
		{
			totalresult.writeCsv(fi);
		}
		totalrow+=lex.getRownum();
		totalvalrow+=lex.getValideline();
		System.out.println("--------------");
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
