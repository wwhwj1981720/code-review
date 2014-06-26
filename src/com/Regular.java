package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Regular {
//	j->SV
	Stack s;//
	List<String> handleall;//handle
	Rule rulearr[];
	String handlenow;//产生当前句柄
	Rule rulenow;
	Stack rsave;
	public void init()
	{
		s=new Stack();
		rsave=new Stack();
		handleall=new ArrayList<String>();
		s.add("#");
		setRule();
	}
	public void setRule()
	{
		rulearr=new Rule[6];
		rulearr[0]=new Rule("S","C");
		rulearr[1]=new Rule("S","D");
		rulearr[2]=new Rule("C","aC");
		rulearr[3]=new Rule("C","b");
		rulearr[4]=new Rule("D","aD");
		rulearr[5]=new Rule("D","c");
		
		
		
	}
	public boolean inputString(String analystr)
	{
		boolean flag=false;
		if(analystr==null||analystr.equals(""))	 flag=false;
		char ss[]=analystr.toCharArray();
		for(char a:ss)
		{
			String e=String.valueOf(a);
			insertElemet(e);
			boolean is=false;
			is=getInternet();
			while(is) 
			{
				redueRule(this.handlenow);
				is=getInternet();
			}
		}
		if(s.get(1).equals("S")||s.get(0).equals("#")) flag=true;
		return flag;
	}
	public void insertElemet(String e)
	{
		s.add(e);
		searchHandle();
		
	}
	//查看当前所能形成的句柄
	public void searchHandle()
	{
		handleall.clear();
		int total=s.size()-1;
		String basestr="";
		for(int i=total;i>0; i--)
		{
			basestr=(String)s.get(i)+basestr;
			handleall.add(basestr);
		}
		
	}
	//判断是否可以进行规约  handleall yu Rule r
	public boolean getInternet()
	{
		searchHandle();
		boolean flag=false;
		String result=null;
		for(String s:handleall)
		{
			if(obeyRule(s)) 
			{
				result=s ;
				handlenow=s;
				flag=true;
				break;
			}
		}
		return flag;
	}
	public boolean obeyRule(String s)
	{
		int len=rulearr.length;
		boolean flag=false;
		for(int i=0;i<len; i++)
		{
			if(rulearr[i].r.equals(s)) 
			{
				flag=true;
				rulenow=rulearr[i];
				break;//现在考虑简单情况
			}
		}
		return flag;
	}
	//从站里面 将形成的句柄弹出,将右部压入
	public void redueRule(String str)
	{
		int total=s.size()-1;
		String basestr="";
		for(int i=total;i>0; i--)
		{
			basestr=(String)s.get(i)+basestr;
			if(basestr.equals(str))
			{
				int count=0;
				count=total-i+1;
				for(int k=0; k<count;k++) s.pop();
				s.push(rulenow.l);
				break;
			}
		}
	}
	public static void main(String args[])
	{
		Regular re=new Regular();
		re.init();
//		re.inputString("");
		re.inputString("aaab");
		
//		re.insertElemet("a");
//		re.insertElemet("a");
//		re.insertElemet("a");
//		re.insertElemet("b");
//		re.searchHandle();
		
		
	}

}
