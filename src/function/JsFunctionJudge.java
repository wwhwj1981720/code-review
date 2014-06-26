package function;

import java.util.ArrayList;
import java.util.List;


import unicom.CFile;
import unicom.End;
import unicom.FunUnit;
import unicom.Pre;
import unicom.WordUnit;



/**
 * @author wangwh
 *
 */
public class JsFunctionJudge implements FunctionInterface {
	//String input="";
	int inow;//词移动时的指示表
	List<WordUnit> wordlist=null;
	int len;//keyword　　个数
	List<String> funlist;
	List<FunUnit> fununitlist;//带行号
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
	
	public JsFunctionJudge() {
		super();
		funlist=new ArrayList<String>();
		fununitlist=new ArrayList<FunUnit>();
	}

	public JsFunctionJudge(List<WordUnit> wordlist,int length) {
		super();
		this.wordlist = wordlist;
		
		funlist=new ArrayList<String>();
		fununitlist=new ArrayList<FunUnit>();
		len=length;
		//wordlist.toArray(midarr);
	}
	//识别java的函数 根据特征 ){
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
		if((ipre-1)<0) flag=false;
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
		
		funlist.add(restr);
		int endid=0;
		endid=wordlist.get(end).getRow();
		fun.setEnd(endid);
		fununitlist.add(fun);
		
	}
	public void outPutFunctionList(String path)
	{
		CFile md5=new CFile(path);
		md5.writeFunUnitList(fununitlist);
	}
	
}
