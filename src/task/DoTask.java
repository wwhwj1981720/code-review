package task;

//Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) 
//Source File Name:   DoTask.java



import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Callable;


import factory.Factory;
import function.FunctionInterface;
import function.FunctionJudge;
import function.JavaFunctionJudge;
import function.JsFunctionJudge;

import lex.AspAnayase;
import lex.LexInterface;

import unicom.CFile;
import unicom.CountTotal;
import unicom.FileInfo;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;
import unicom.start.Const;


   

/**
 * @author wangwh
 *  param	lf 文件列表
 *  param	LexInterface 词法分析接口(工厂动态创建)
 *  param	FunctionInterface 函数识别的接口（先默认使用java,以后动态创建）
 *
 */
public class DoTask implements Callable
{
	List<String> lf;
	String type;
	LexInterface lex;
	FunctionInterface fj;
	CFile result=null;//每个结果分别输出
	CFile totalresult=null;
	long totalrow=0;
	long totalvalrow=0;
	

 public DoTask(List<String> lf,String suffix,CFile totalresult)
 {
	 System.out.println("start---type"+suffix);
	 this.lf = lf;
	 this.lex=(LexInterface)Factory.createLexObject(suffix);
	 this.fj=(FunctionInterface)Factory.createFuntionObject(suffix);
	 //this.fj=new JavaFunctionJudge();
	 this.totalresult=totalresult;
	 this.type=suffix;
	 result=new CFile(Const.resut+type+".csv");//每种类型汇总文件分别输出结果
	 try {
		result.initCsv();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }

 public CountTotal call() throws Exception
 {
	 System.out.println("start---java");
	 CountTotal ct=new CountTotal();

		for(String path:lf)
		{
			
			reverseMd(path);
			
		}
		ct.setTotalrow(totalrow);
		ct.setTotalvalrow(totalvalrow);
		result.writeCsvTotal(totalrow,totalvalrow);//jsp文件的汇总输出
		//result.close();
		return ct;
 }

 public void reverseMd(String path) 
	{
		
		System.out.println(path);
		//CountTotal ct=new CountTotal();
		FileInfo fi=new FileInfo();
		String fname="";
		fi.setPath(path);
		File fin=new File(path);
		if(fin!=null) fname=fin.getName(); 
		ReadFileStartLex startlex=new ReadFileStartLex(path);
		startlex.readFileContent();
		if(!startlex.isTxt()) return ;//不是文本文件直接退出
		String content=startlex.getContent();
		lex.wordAnalysis(content);
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
		result.writeCsv(fi);
		synchronized(totalresult)
		{
			totalresult.writeCsv(fi);
		}
		totalrow+=lex.getRownum();
		totalvalrow+=lex.getValideline();
		//System.out.println("--------------");
		return ;
	
	}
 
}

