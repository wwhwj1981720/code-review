package cpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import lex.LexInterface;

import unicom.CFile;
import unicom.ReadFileStartLex;
import unicom.WordUnit;


/**
 * @author wangwh 
 * cpp 语言的词法解析器
 *
 */
public class cppAnalysis implements LexInterface {

	//String input;
	List<WordUnit> wordlist = new ArrayList<WordUnit>();
	
	int rownum=0;//总行数
	int nouse=0;//有效行数
	int valideline=0;
public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getNouse() {
		return nouse;
	}
	public void setNouse(int nouse) {
		this.nouse = nouse;
	}
	//	s
	//一个文件一个
	public List<WordUnit> getWordlist() {
		return wordlist;
	}
	public void setWordlist(List<WordUnit> wordlist) {
		this.wordlist = wordlist;
	}
	public int wordAnalysis(String input){
		int i = 0;
		int row=1;//全部行号
		
		int unrow=0;
		basicFunction bf = new basicFunction(input);
		int lo  =  input.length();
		while(bf.index<lo){
			WordUnit word = new WordUnit();//用来存放每次分析出来的标识符
			bf.GetChar();
			bf.Concat();
			/*
			 * 输入是字母时的处理
			 */
			if(bf.IsLetter()){
				int f = 0;
				if(bf.index<lo){
					bf.GetChar();
					bf.Concat();
					while(bf.IsLetter()||bf.IsDigit()){
						if(bf.index<lo){
							bf.GetChar();
							bf.Concat();
						}else{
							bf.ch=' ';
							f=1;//结束
						}						
					}
				}				
				if(f!=1){bf.Retract();}//将搜索指示器回调一个字符位置
				int m = bf.Reserve();
				if(m==0){//不是保留字
					word.setValue(bf.strToken);
					word.setID(7);//设置为ID的编号，说明是标识符
					word.setRow(row);
				}else{//是保留字
					word.setValue(bf.strToken);
					word.setID(m);
					word.setRow(row);
				}
				i++;
				wordlist.add(word);
				bf.RetractStr();//将strToken置空
			}
			/*
			 * 输入是数字时的处理(比较简单)   表示
			 */
			else if(bf.IsDigit()){
				int f = 0;
				if(bf.index<lo){
					bf.GetChar();
					bf.Concat();
				}
				while(bf.IsDigit()){
					if(bf.index<lo){
						bf.GetChar();
						bf.Concat();
					}else{
						bf.ch=' ';
						f=1;
					}		
				}
				if(f!=1){bf.Retract();}	
				word.setValue(bf.strToken);
				word.setID(8);
				word.setRow(row);
				wordlist.add(word);
				i++;
				bf.RetractStr();
			}
			/*
			 * 输入可能是 注释 40 表示
			 */
			
			else if(bf.ch=='/')
			{
				int f = 0;
				bf.GetChar();
				bf.Concat();
				if (bf.ch == '/') {
					bf.GetChar();
					bf.Concat();
					while ((bf.ch) != '\n') {
						bf.GetChar();
					}
					if(bf.ch=='\n') unrow++;//后面有一个 \n 才能确认一个无效行
					bf.Retract();
					word.setValue(bf.strToken);
					word.setID(40);
					word.setRow(row);
					wordlist.add(word);
					i++;
					bf.RetractStr();
				}
				else if(bf.ch=='*')
				{
					int invaliderow=0;
					bf.GetChar();
					bf.Concat();
					boolean flag=false;
					bf.savePos();
					while (bf.index<lo) {
						if(bf.ch=='\n') 
						{
							row++;
							invaliderow++;//可能的无效行
						}
						if((bf.ch)=='*'&&(bf.nextch=='/')) 
						{
							flag=true;
							bf.index++;
							bf.index++;
							break;
						}
						bf.GetChar();
					}
					if(flag) 
					{
						
						invaliderow++;//可能的无效行
						word.setValue(bf.strToken+"*/");
						word.setID(40);
						word.setRow(row);
						wordlist.add(word);
						i++;
						bf.RetractStr();
					}
					else 
					{
						invaliderow=0;//确认没有 /* */
						bf.backPos();
					}
					unrow+=invaliderow;
				}
			}
			else if((bf.ch=='.'))
			{
				bf.GetChar();
				bf.Concat();
				bf.Retract();
				word.setValue(bf.strToken);
				word.setID(50);
				word.setRow(row);
				wordlist.add(word);
				i++;
				bf.RetractStr();
			}
			/*
			 * 输入特殊字符时的处理
			 */
			else if(bf.ch=='+'||bf.ch=='-'||bf.ch=='*'||bf.ch=='('||bf.ch==')'||
					bf.ch=='['||bf.ch==']'||bf.ch=='{'||bf.ch=='}'||bf.ch==','||bf.ch==':'||bf.ch==';'){
				int m = bf.Reserve();
				word.setValue(bf.strToken);
				word.setID(m);
				word.setRow(row);
				wordlist.add(word);
				i++;
				bf.RetractStr();
			}
			/*
			 * 由于这四个符号有可能不会单独出现，所以要分开来处理
			 */
			else if(bf.ch=='>'||bf.ch=='<'||bf.ch=='='||bf.ch=='!'){
				int f = 0;
				if(bf.index<lo){
					bf.GetChar();
					bf.Concat();
				}else{//结束
					bf.index++;
					bf.ch=' ';
					f=1;
				}	
				if(bf.ch!='='&&f!=1){//是单独的符号，而不是<=类似的
					bf.Retract();//将搜索指示器回调一个字符位置
				}
				int m = bf.Reserve();
				if(m==0){
					word.setValue(bf.strToken);//不是29个标识符中的一个
					word.setID(88);
					word.setRow(row);
				}else{
					word.setValue(bf.strToken);
					word.setID(m);
					word.setRow(row);
				}
				wordlist.add(word);
				i++;
				bf.RetractStr();
			}
			/*
			 * 输入是回车、换行和空格时直接跳过
			 */
			else if(bf.ch==' '||bf.ch=='\r'||bf.ch=='\t'){		
				bf.RetractStr();
			}
			else if(bf.ch=='\n')
			{
				row++;
				bf.RetractStr();
				
			}
		}
		this.rownum=row;
		this.nouse=unrow;
		this.valideline=row-unrow;
		return i;		
	}
	public int getValideline() {
		return valideline;
	}
	public void setValideline(int valideline) {
		this.valideline = valideline;
	}
	public void outputWord(String midname)
	{
		CFile file=new CFile(midname);
		WordUnit word=null;
		for(int i=0; i<wordlist.size(); i++)
		{
			word=wordlist.get(i);
			try {
				//if(word!=null) file.writeRow(word.ID+"\t"+word.getValue()+"\t"+word.getRow());
				if(word!=null) file.writeRow(word.getID()+"\t"+word.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			file.writeRow("总行数"+"\t"+this.rownum+"\t"+"无效行数"+this.nouse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]) throws IOException
	{
		String file="e:\\versionfunction\\AnalysisFB.java";
		String wordpath="e:\\versionfunction\\wordlist3.txt";
		ReadFileStartLex startlex=new ReadFileStartLex(file);
		startlex.readFileContent();
		String content=startlex.getContent();
		LexInterface lex=new cppAnalysis();
		lex.wordAnalysis(content);
		lex.outputWord(wordpath);
		
		
	}
}