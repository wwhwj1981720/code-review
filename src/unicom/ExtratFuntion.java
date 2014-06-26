package unicom;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import function.FunctionJudge;


import unicom.MD5;

/**
 * @author wangwh
 * 
 *
 */

public class ExtratFuntion {
	List <String> mdlist=new LinkedList<String>();
	List<String> funlist=null;
	List<WordUnit> wordlist=null;//词法分析的结果 
	
	
	public List<String> getFunlist() {
		return funlist;
	}
	public void setFunlist(List<String> funlist) {
		this.funlist = funlist;
	}
	public List<WordUnit> getWordlist() {
		return wordlist;
	}
	//从外部直接注入 词法分析结果
	public void setWordlist(List<WordUnit> wordlist) {
		this.wordlist = wordlist;
	}
	//一种接口从词法分析中间文件中读取
	public void extratFunction(String wordpath,String funfile) throws IOException 
	{
		String path=wordpath;
		BufferedReader br=new BufferedReader(new FileReader(path));
		String line=null;
		//word midarr[]=new word[100000];
		wordlist=new ArrayList<WordUnit>();
		int num=0;
		for(;(line=br.readLine())!=null;)
		{
			if(line!=null)
			{
				String a[]=line.split("\\s{1,}");
				WordUnit oneword=new WordUnit();
				try{
				oneword.setID(Integer.parseInt(a[0]));
				}
				catch (Exception e)
				{
					//System.out.println();
					e.printStackTrace();
				}
				
				oneword.setValue(a[1]);
				wordlist.add(oneword);
			} 
			num++;
		}
		FunctionJudge fu=new FunctionJudge(wordlist,num);
		fu.recganize();
		CFile out=new CFile(funfile);
		funlist=fu.getFunlist();
		out.writeList(funlist);
		
	}
	/**
	 * @author Administrator
	 *
	 */
	public void getMd5List()
	{
		for(String s:funlist)
		{
			
			mdlist.add(MD5.makeMD5(s));
			
		}
	}
	/**
	 * @author Administrator
	 *
	 */
	public void outPutFunctionMd5(String md5path)
	{
		CFile md5=new CFile(md5path);
		md5.writeList(mdlist);
	}
	/**
	 * @author 对 MD进行排序 这样 就是人为打乱顺序也没是
	 *
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		String wordlist="e:\\versionfunction\\wordlist10.txt";
		String funfile="e:\\versionfunction\\function7.txt";
		String md5path="e:\\md5.txt";
		ExtratFuntion ef=new ExtratFuntion();
		ef.extratFunction(wordlist,funfile);
		ef.getMd5List();
		ef.outPutFunctionMd5(md5path);
		
	}

}
