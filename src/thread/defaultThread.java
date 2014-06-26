package thread;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import function.FunctionInterface;
import function.FunctionJudge;

import lex.HtmlAnayase;
import lex.LexInterface;
import unicom.CFile;
import unicom.CountTotal;
import unicom.FileInfo;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;

public class defaultThread extends Thread {
	
	long totalrow=0;
	long totalvalrow=0;
	LexInterface lex;
	FunctionInterface fj;
	List<String> lf;
	CFile totalresult=null;
	CFile result=null;
	String suffer=null;
	public defaultThread(List<String> lf,CFile totalresult,String suffer) {
		super();
		this.lf = lf;
		this.totalresult=totalresult;
		this.suffer=suffer;
		result=new CFile("e:\\"+"default"+".csv");
		wordpath="e:\\versionfunction\\wordlist12"+".txt";
		md5path="e:\\versionfunction\\md512"+".txt";
		funfile="e:\\versionfunction\\function12"+".txt";
		try {
			result.initCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	String wordpath=null;
	String md5path=null;
	String funfile=null;
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
		System.out.println("start---default");
		lex=new HtmlAnayase(); 
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
		countFile();
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
		if(!startlex.isTxt()) return;//不是文本文件直接退出
		String content=startlex.getContent();
		
		lex.wordAnalysis(content);
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
