package task;

//Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) 
//Source File Name:   DoTask.java



import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Callable;


import factory.Factory;
import function.FunctionInterface;
import function.FunctionJudge;
import function.JsFunctionJudge;

import lex.AspAnayase;
import lex.LexInterface;

import unicom.CFile;
import unicom.CFrame;
import unicom.CountTotal;
import unicom.Dir;
import unicom.FileInfo;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;
import unicom.start.Const;




/**
 * @author 保留中间生成的 词法分析表和解析函数表
 *
 */
public class SaveMidFileDoTask extends DoTask implements Callable
{
	
	CFrame frame;
	TextArea textshow;
	//FunctionInterface fj;//使用基类的
	//
 public SaveMidFileDoTask(List<String> lf,String suffix,CFile totalresult,CFrame frame)
 {
	 super(lf,suffix,totalresult);
	 //fj=new JsFunctionJudge();//
	 Dir filedir=new Dir(Const.funDir);
	 Dir worddir=new Dir(Const.wordDir);
	 System.out.println("start---type"+suffix);
	 this.frame=frame;
	 if(frame!=null)
	 {
		 this.textshow=frame.getError_text();
	 }
	
 }
 public SaveMidFileDoTask(List<String> lf,String suffix,CFile totalresult)
 {
	 super(lf,suffix,totalresult);
	 //fj=new JsFunctionJudge();//
	 Dir filedir=new Dir(Const.funDir);
	 Dir worddir=new Dir(Const.wordDir);
	 System.out.println("start---type"+suffix);
	
	
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
		
		return ct;
 }
 
 public void reverseMd(String path)
	{
		
		System.out.println(path);
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
		//每个文件一个函数和词输出
		
		fj.outPutFunctionList(Const.funDir+fname);
		lex.outputWord(Const.wordDir+fname);
		
		//
		result.writeCsv(fi);
		synchronized(totalresult)
		{
			totalresult.writeCsv(fi);
			if(frame!=null)
			{
				textshow.append(path+"\n");
			}
		}
		totalrow+=lex.getRownum();
		totalvalrow+=lex.getValideline();
		
		return ;
	
	}
 
}

