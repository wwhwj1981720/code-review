package unicom;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import function.FunctionJudge;

import lex.LexInterface;
import lex.lexAnayase;



/**
 * @param args wangwh 
 * @throws IOException 
 */

public class ReadFileStartLex {

	String fileRead;//要分析的文件名
	String content;//存源文件内容到字符串
	boolean isTxt=true;
	
	public String getFileRead() {
		return fileRead;
	}
	public void setFileRead(String fileRead) {
		this.fileRead = fileRead;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ReadFileStartLex(String fileRead) {
		super();
		this.fileRead = fileRead;
		this.content="";
	}
	//
	public void readFileContentline()throws IOException
	{
		boolean isTxt=false;
		String input ="";
		FileInputStream fin = new FileInputStream(fileRead);
		int itemp = fin.read();
		while(itemp!=-1)
		{
			
			char tempi = (char)itemp;
			String tempin = String.valueOf(tempi);
			input = input+tempin;
			itemp = fin.read();
		}
		input = input.trim();
		content=input;
	}
	//加快文件的速度
	
	public void readFileContent()
	{
		//boolean isTxt=false;
		isTxt=true;
		String input ="";
		BufferedReader fin=null;
		try {
			fin = new BufferedReader(new InputStreamReader(new FileInputStream(fileRead)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//int itemp = fin.read();
		String line=null;
		StringBuffer sb=new StringBuffer();
		try {
			while((line=fin.readLine())!=null)
			{
				if(!isTxtJudge(line)) 
				{
					isTxt=false;
					break;//说明是二进制文件
				}
				sb.append(line+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		content=sb.toString();
	}
	public boolean isTxt() {
		return isTxt;
	}
	public void setTxt(boolean isTxt) {
		this.isTxt = isTxt;
	}
	public void packetFile(String wordpath) 
	{
		
		System.out.println(content);
		//SentenceAnalysis  sa = new SentenceAnalysis(); 
		
		lexAnayase lex = new lexAnayase();
		//lex.setInput(content);					
		int i = lex.wordAnalysis(content);//进行词法分析，并把input字符串形成相应的w[]数组，返回i就是一共分析出来几个标识符
		lex.outputWord(wordpath);
	}
	/**
	 * @author wangwh
	 *  param str 文本的一行数据
	 *	return flag  true 是文本  false 不是文本
	 */
	public boolean isTxtJudge(String str) {
		boolean flag = true;
		byte[] bts = str.getBytes();
		int btsLength = bts.length;
		byte[] newBytes = new byte[btsLength];
		for (int i = 0; i < btsLength; i++) {

			byte b = bts[i];
			if ((b >= 14 && b <= 31) || b == 127||(b>4&&b<8)) {
				flag = false;
				break;
			}

			// newBytes[i]=b;
		}
		return flag;

	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String file="e:\\versionfunction\\cpp\\find11.jsp";
		String wordpath="e:\\versionfunction\\wordlist10.txt";
		String md5path="e:\\versionfunction\\md510.txt";
		String funfile="e:\\versionfunction\\function10.txt";
		ReadFileStartLex startlex=new ReadFileStartLex(file);
		startlex.readFileContent();
		//startlex.packetFile(wordpath);
		String content=startlex.getContent();
		LexInterface lex=new lexAnayase();
		lex.wordAnalysis(content);
		content=null;//减少引用
		lex.outputWord(wordpath);
		List<WordUnit> wordlist=lex.getWordlist();
		FunctionJudge fj=new FunctionJudge();
		fj.setWordlist(wordlist);
		fj.recganize();
		List<String> funlist=fj.getFunlist();
		fj.outPutFunctionList(funfile);
		ExtratFuntion ef=new ExtratFuntion();
		ef.setFunlist(funlist);
		ef.getMd5List();
		ef.outPutFunctionMd5(md5path);

		wordlist=null;
		
		
		
	}

}
