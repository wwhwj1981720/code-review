package lex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import unicom.ReadFileStartLex;
import unicom.WordUnit;

/**
 * @author asp.net 语法也是<%%>内部是 c#或者是vb
 *
 */
public class AspAnayase extends BaseLex implements LexInterface {
	
	//temps 文件名
	int rownum=0;
	
	int nouse=0;
	int validenum=0;
	

	/* (non-Javadoc)
	 *    将 html 部分 与 jsp 部分抽出
	 * @see lex.BaseLex#wordAnalysis(java.lang.String)
	 */
	@Override
	
	public int wordAnalysis(String content) {
		
		// TODO Auto-generated method stub
		//匹配 <%%>
		int rownum=0;
		int nouse=0;
		int valideline=0;
		String test="<!--7897"+"\n"+"7799-->";
		Pattern p=Pattern.compile("\\n",Pattern.CASE_INSENSITIVE);//匹配换行
		Pattern word=Pattern.compile("\\b\\w+\\b",Pattern.CASE_INSENSITIVE);
		Pattern d=Pattern.compile("\\b\\d+\\b",Pattern.CASE_INSENSITIVE);
		Pattern nodes=Pattern.compile("<!-{2,}.[\\r]*[\\n]*.-{2,}>");
		Matcher m=p.matcher(content);
		boolean b=false;
		while(b=m.find())
		{
			rownum++;
			//System.out.println(m.start()+"| "+m.group()+" "+rownum);
		}
		
		List<String> in=new LinkedList<String>();
		StringBuffer note=new StringBuffer();
		StringBuffer delnote=new StringBuffer();
		delnote=findNotes(note,content);//去掉注释之后的内容
		//System.out.println(note);
		//System.out.println(delnote);
		Matcher mnote=p.matcher(note);
		//注释内部 的 行数
		while(b=mnote.find())
		{
			nouse++;
			//System.out.println(m.start()+"| "+m.group()+" "+rownum);
		}
		
		StringBuffer jsp=new StringBuffer();
		StringBuffer deljsp=new StringBuffer();
		deljsp=Javacontent(jsp,delnote.toString());//去掉注释之后的内容
		String javastr=jsp.toString();
		super.wordAnalysis(javastr);//对于java内容使用基本的词法分析
		this.rownum=rownum;
		this.validenum=rownum-nouse;
		
		

		return 0;
	}

	@Override
	public void outputWord(String midname) {
		// TODO Auto-generated method stub
		super.outputWord(midname);

	}

	@Override
	public List<WordUnit> getWordlist() {
		// TODO Auto-generated method stub
		
		return super.getWordlist();
	}

	@Override
	public int getRownum() {
		// TODO Auto-generated method stub
		return this.rownum;
	}

	@Override
	public int getNouse() {
		// TODO Auto-generated method stub
		return this.nouse;
	}

	@Override
	public int getValideline() {
		// TODO Auto-generated method stub
		return this.validenum;
	}
	//统计注释内容，同时去掉注释串
	public StringBuffer findNotes(StringBuffer in,String content)
	{
		StringBuffer newstr=new StringBuffer();
		int t=content.length();
		int bi=content.indexOf("<!--");
		int ei=content.indexOf("-->");
		if((bi>0)&&(ei>0)&&(ei>bi))
		{
			String substr=content.substring(bi,ei+3);
			newstr.append(content.substring(0,bi));
			
			in.append(substr);
			if(ei<t)
			{
				String othersub=content.substring(ei+3);
				newstr.append(findNotes(in, othersub));
			}
			
		}
		else 
		{
			newstr.append(content);
		}
		 return newstr;
	}
	/**
	 * @author wangh
	 * param in 匹配得到的标签内容
	 * parm  content  输入的原始内容       
	 * return 去掉标签之后的内容 
	 */
	public StringBuffer Javacontent(StringBuffer in,String content)
	{
		StringBuffer newstr=new StringBuffer();
		int t=content.length();
		int bi=content.indexOf("<%");
		int ei=content.indexOf("%>");
		if((bi>=0)&&(ei>0)&&(ei>bi))
		{
			String substr=content.substring(bi,ei+2);
			newstr.append(content.substring(0,bi));
			
			in.append(substr);
			if(ei<t)
			{
				String othersub=content.substring(ei+2);
				newstr.append(Javacontent(in, othersub));
			}
			
		}
		else 
		{
			newstr.append(content);
		}
		 return newstr;
	}
	public static void main(String args[])
	{
		String file="e:\\versionfunction\\cpp\\find11.jsp";
		String wordpath="e:\\versionfunction\\wordlist13.txt";
		String funfile="e:\\versionfunction\\function13.txt";
		ReadFileStartLex startlex=new ReadFileStartLex(file);
		startlex.readFileContent();
		String content=startlex.getContent();
		LexInterface lex=new AspAnayase();
		lex.wordAnalysis(content);
		lex.outputWord(funfile);
	}
	public void readfile(String input) throws IOException
	{
		int rownum=0;
		int validenum=0;
		int nouse=0;
		boolean delflagpr=false;
		boolean delflagaf=false;
		boolean code=false;
		String codestr="";
		FileInputStream incode=null;
		try {
			incode = new FileInputStream(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line="";
		BufferedReader strcode=new BufferedReader(new InputStreamReader(incode));
		while((line=strcode.readLine())!=null)
		{
			rownum++;
			if(line.matches("^\\s*$")){
				System.out.println("空行！");
				nouse++;
				//output.write(String.format("空行！")+System.getProperty("line.separator"));
			}
			//取出 <% %>部分
			if(line.matches("<%@.+%>"))
			{
				int begin,end=0;
				begin=line.indexOf("<%");
				end=line.indexOf("%>");
				String s=line.substring(begin,end+2);
				System.out.println(s);
			};
			//取出 <%! %> java部分代码 
			if(line.matches("<%!.+%>"))
			{
				int begin,end=0;
				begin=line.indexOf("<%");
				end=line.indexOf("%>");
				String s=line.substring(begin,end+2);
				System.out.println(s);
			}
			if(line.contains("<%"))
			{
				code=true;
				codestr+=line;
			}
			if(code)
			{
				codestr+=line;
			}
			if(line.contains("%>"))
			{
				code=false;
			}
			//注释部分
			if(line.matches("//"))
			{
				nouse++;
			}
			//html 注释
			if(line.matches("<!--"))
			{
				delflagpr=true;
			}
			//有注释块前缀
			if(delflagpr&&line.matches("-->"))
			{
				delflagaf=true;
			}
		}
	}
}
