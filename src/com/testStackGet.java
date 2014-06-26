package com;

import java.util.Stack;

public class testStackGet {

	/**
	 * @param args
	 */
	public Stack s=new Stack();
	String []box=new String[30];
	
	public void matchString(String[] in)
	{
		String a[]=in;
		int i=0;
		int m=0;
		boolean f=false;
		for(String c:a)
		{
			if(c.equals("{")&&(in[m-1].equals(")"))) f=true; 
			if(f) s.push(c);
			if(c.equals("}")) 
			{
				f=false;
				String newstr="";
				while(!s.empty())
				{
					newstr=s.pop()+newstr;
					
				}
				box[i]=newstr;
				i++;
			}
			m++;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Stack s=new Stack();
//		s.add("q");
//		s.add("w");
//		s.add("e");
//		//for(int i=0;i)
//		System.out.println(s.get(2));
//		System.out.println(s.get(0));
//	
		String in[]={"word",")","{","new","}","year",")","{","one","two","{","}"};
		testStackGet test=new testStackGet();
		test.matchString(in);
		int k=0;
		while(test.box!=null)
		{
			System.out.println(test.box[k]);
			k++;
		}
	}

}
