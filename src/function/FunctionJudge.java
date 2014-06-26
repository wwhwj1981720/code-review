package function;

import java.util.ArrayList;
import java.util.List;


import unicom.CFile;
import unicom.End;
import unicom.FunUnit;
import unicom.WordUnit;



/**
 * @author wangwh
 *      FunctionInterface 函数识别通用（没有实现向前搜索）
 *
 */
public class FunctionJudge implements FunctionInterface {
	//String input="";
	int inow;//词移动时的指示表
	
	List<WordUnit> wordlist=null;
	public List<WordUnit> getWordlist() {
		return wordlist;
	}

	public void setWordlist(List<WordUnit> wordlist) {
		this.wordlist = wordlist;
	}
	int len;//keyword　　个数
	List<String> funlist;
	
	public List<String> getFunlist() {
		return funlist;
	}

	public void setFunlist(List<String> funlist) {
		this.funlist = funlist;
	}
	
	public FunctionJudge() {
		super();
		funlist=new ArrayList<String>();
	}

	public FunctionJudge(List<WordUnit> wordlist,int length) {
		super();
		this.wordlist = wordlist;
		
		funlist=new ArrayList<String>();
		len=length;
		//wordlist.toArray(midarr);
	}
	//识别java的函数 根据特征 ){
	public void recganize()
	{
		//while
		funlist.clear();
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
						makeFunctionStr(begin,fend.iend);
						inow=fend.iend;//jump ready function
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
//	
	public void makeFunctionStr(int start,int end)
	{
		StringBuffer sb=new StringBuffer();
		int i=0;
		for(i=start;i<=end;i++)
		{
			sb.append(wordlist.get(i).getValue());
		}
		String restr=null;
		restr=sb.toString();
		funlist.add(restr);
		
	}
	public void outPutFunctionList(String path)
	{
		CFile md5=new CFile(path);
		md5.writeList(funlist);
	}

	@Override
	public List<FunUnit> getFununitlist() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
