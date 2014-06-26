package function;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import tool.MyStatic;
import unicom.CFile;
import unicom.End;
import unicom.FunUnit;
import unicom.Pre;
import unicom.WordUnit;



/**
 * @author Administrato
 *
 */
public class JavaFunctionJudge implements FunctionInterface {
	//String input="";
	int inow;//词移动时的指示表
	List<WordUnit> wordlist=null;
	int len;//keyword　　个数
	List<String> funlist;
	List<FunUnit> fununitlist;//带行号
	
	Set<String> javaset=null;
	public List<FunUnit> getFununitlist() {
		return fununitlist;
	}

	public void setFununitlist(List<FunUnit> fununitlist) {
		this.fununitlist = fununitlist;
	}
	
	public List<WordUnit> getWordlist() {
		return wordlist;
	}

	public void setWordlist(List<WordUnit> wordlist) {
		this.wordlist = wordlist;
	}
	
	public List<String> getFunlist() {
		return funlist;
	}

	public void setFunlist(List<String> funlist) {
		this.funlist = funlist;
	}
	
	public JavaFunctionJudge() {
		super();
		funlist=new ArrayList<String>();
		fununitlist=new ArrayList<FunUnit>();
		
	}

	public JavaFunctionJudge(List<WordUnit> wordlist,int length) {
		super();
		this.wordlist = wordlist;
		
		funlist=new ArrayList<String>();
		fununitlist=new ArrayList<FunUnit>();
		
		
		len=length;
		//wordlist.toArray(midarr);
	}
	//识别java的函数 根据特征 ){ )throws
	/* (non-Javadoc)
	 * @see function.FunctionInterface#recganize()
	 *  1  函数特征   ){
	 *  2 函数特征   )throws
	 */
	public void recganize()
	{
		//while
		funlist.clear();
		fununitlist.clear();
		inow=0;
		len=wordlist.size();
		
		int begin=0;
		int end=0;
		
		while(inow<len)
		{
			if(wordlist.get(inow).getValue().equals(")")) 
			{
				begin=inow;//record start pos
				int inext=0;
				inext=begin;
				inext++;
				if(inext>=len) break;//防止越界
				if(wordlist.get(inext).getValue().equals("{"))
				{
					End fend=findEnd(inext);
					if(fend.b)
					{
						Pre pre=findPre(inext);
						if(pre.b)
						{
							makeFunctionStr(pre.ipr,fend.iend);
							inow=fend.iend;//jump ready function
						}
					}
				}
				//)throws也是函数
				else if(wordlist.get(inext).getValue().equals("throws"))
				{
					End fend=findThrowsEnd(inext);
					if(fend.b)
					{
						Pre pre=findPre(inext);
						if(pre.b)
						{
							makeFunctionStr(pre.ipr,fend.iend);
							inow=fend.iend;//jump ready function
						}
					}
				}
				else 
				{
					begin=0;
				}
				
			}
			else 
			{
				begin=0;
				end=0;
			}
			inow++;//
		}
	}
	/*
	 * 输入特殊字符时的处理
	 * istart是 意思 { 位置
	 * 看是否有{
	 * 继续判断找到 函数终结 }
	 */
	public End findEnd(int istart)
	{
		boolean flag=false;
		int iend=0;
		End reend=new End(iend,flag);
		int inext=0;
		inext=istart;
		inext++;
		int count=0;
		while(inext<len)
		{
			if(wordlist.get(inext).getValue().equals("}")&&(count==0))
			{
				iend=inext;
				flag=true;
				reend.setB(flag);
				reend.setIend(iend);
				break;
			}
			if(wordlist.get(inext).getValue().equals("{")) count++;
			else if(wordlist.get(inext).getValue().equals("}")) count--;
			inext++;	
		}
		return reend;
	}
	/*
	 * ）throws与){向后找不同
	 * 
	 * 
	 */
	public End findThrowsEnd(int istart)
	{
		boolean flag=false;
		int iend=0;
		End reend=new End(iend,flag);
		int inext=0;
		inext=istart;
		inext++;
		int count=0;
		while(inext<len)
		{
			if(wordlist.get(inext).getValue().equals("}")&&(count==1))
			{
				iend=inext;
				flag=true;
				reend.setB(flag);
				reend.setIend(iend);
				break;
			}
			if(wordlist.get(inext).getValue().equals("{")) count++;
			else if(wordlist.get(inext).getValue().equals("}")) count--;
			inext++;	
		}
		return reend;
	}
	/*
	 * 向前查找函数的开始，现在设置最大的回硕次数为10
	 * 
	 * 
	 */
	public Pre findPre(int istart)
	{
		boolean flag=false;
		int ipre=0;
		Pre repre=new Pre(ipre,flag);
		int inext=0;
		inext=istart;
		inext--;
		int count=10;//向前最大回硕次数
		while(inext>0&&count>0)
		{
			if(wordlist.get(inext).getValue().equals("("))
			{
				ipre=inext;
				flag=true;
				
				break;
			}
		
			inext--;	
		}
		
		//可有有 while,for ,switch,if 等有 while(){}, for(){},i.f(){} 等被误判为函数
		if((ipre-1)<0) flag=false;
		String startstr="";
		startstr=wordlist.get(ipre-1).getValue();
		if(MyStatic.javaset.contains(startstr)) flag=false;
		//
		repre.setB(flag);
		repre.setIpr(ipre-1);
		return repre;
	}
	//	一个函数的开始和结尾
	public void makeFunctionStr(int start,int end)
	{
		FunUnit fun=new FunUnit();
		StringBuffer sb=new StringBuffer();
		int i=0;
		for(i=start;i<=end;i++)
		{
			sb.append(wordlist.get(i).getValue());
		}
		String restr=null;
		restr=sb.toString();
		fun.setFunstr(restr);
		int rowid=0;
		
		rowid=wordlist.get(start).getRow();
		fun.setRow(rowid);
		int endid=0;
		endid=wordlist.get(end).getRow();
		fun.setEnd(endid);
		funlist.add(restr);
		fununitlist.add(fun);
		
	}
	
	public void outPutFunctionList(String path)
	{
		CFile md5=new CFile(path);
		//md5.writeList(funlist);
		md5.writeFunUnitList(fununitlist);
	}
	
}
