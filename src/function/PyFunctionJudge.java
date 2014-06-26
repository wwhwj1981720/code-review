package function;

import java.util.ArrayList;
import java.util.List;


import unicom.CFile;
import unicom.End;
import unicom.FunUnit;
import unicom.Pre;
import unicom.WordUnit;



/**
 * @author wangwh 2014
 *  param  inow 词移动时的指示表
 *  param  len  
 *  param  funlist 识别出的函数存放列表（String）
 *  param  fununitlist 识别出的函数存放列表(WordUnit)
 */
public class PyFunctionJudge implements FunctionInterface {
	//String input="";
	int inow;//词移动时的指示表
	int len;//keyword　　个数
	List<String> funlist;
	List<FunUnit> fununitlist;//带行号
	List<WordUnit> wordlist=null;
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
	
	public PyFunctionJudge() {
		super();
		funlist=new ArrayList<String>();
		fununitlist=new ArrayList<FunUnit>();
	}

	public PyFunctionJudge(List<WordUnit> wordlist,int length) {
		super();
		this.wordlist = wordlist;
		
		funlist=new ArrayList<String>();
		fununitlist=new ArrayList<FunUnit>();
		len=length;
		//wordlist.toArray(midarr);
	}
	//识别pyhon的函数 根据特征 def之间
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
			if(wordlist.get(inow).getValue().equals("def")) 
			{
				begin=inow;//record start pos
				int inext=0;
				inext=begin;
				End fend=findEnd(inext);
					if(fend.b)
					{
					
						makeFunctionStr(begin,fend.iend-1);//把下一个开始的def也包含了
						inow=fend.iend-1;//jump ready function
						
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
	 * 继续判断找到 函数终结 
	 */
	public End findEnd(int istart)
	{
		boolean flag=false;
		int iend=0;
		End reend=new End(iend,flag);
		int inext=0;
		inext=istart;
		inext++;
		
		while(inext<len)
		{
			if(wordlist.get(inext).getValue().equals("def"))
			{
				iend=inext;
				flag=true;
				reend.setB(flag);
				reend.setIend(iend);
				break;
			}
			inext++;	
		}
		return reend;
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
	public List<FunUnit> getFununitlist() {
		return fununitlist;
	}

	public void setFununitlist(List<FunUnit> fununitlist) {
		this.fununitlist = fununitlist;
	}

	public void outPutFunctionList(String path)
	{
		CFile md5=new CFile(path);
		//md5.writeList(funlist);
		md5.writeFunUnitList(fununitlist);
	}
	
}
