package lex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import function.FunctionInterface;
import function.FunctionJudge;

import unicom.CFile;
import unicom.KeyValue;
import unicom.ReadFileStartLex;
import unicom.WordUnit;

public class JavaAnayase implements LexInterface {
	List<WordUnit> wordlist = new ArrayList<WordUnit>();
	int rownum=0;//总行数
	int nouse=0;//有效行数
	int valideline=0;////有效行数
	

	@Override
	public int wordAnalysis(String content) {
		// TODO Auto-generated method stub
		wordlist.clear();
		this.rownum=0;
		this.nouse=0;
		this.valideline=0;
		int row = 1;// 数排列,横坐标
		int line = 1;// 横排行，竖坐标。当遇到回车换行的时候，line++,row = 0;
		int begin = 0;// 当读到第一个字母的时候，标志其位置
		int end = 0;
		content=content+" ";
		if (content.equals("")) {
			return row;
		}
		else {
			int i = 0;// 选择第i个字符进行检测。
			int N = content.length();// 文件大小

			// 假如是空格开头的文件会出现 java.lang.StringIndexOutOfBoundsException
			// 异常。非空格开头的即可文件正常
			// WordUnit word=new WordUnit();("N = " + N + '\n');

			int state = 0;// 状态标志
			try{
			for (i = 0; i < N; i++) {// 对所有字符进行检测
				row++;
				
				// charAt(i) 读取i相对于当前位置的给定索引处的字符
				char c = content.charAt(i);

				switch (state) {
				case 0:
					// row++;
					if (isLetter(c)) {
						state = 10;
						begin = i;
					}
					
					else if (c == '+')
						state = 1;
					else if (c == '-')
						state = 2;
					else if (c == '*')
						state = 3;
					else if (c == '/')
						state = 4;
					else if (c == '!')
						state = 5;
					else if (c == '>')
						state = 6;
					else if (c == '<')
						state = 7;
					else if (c == '=')
						state = 8;
					else if (c == '\n')
						state = 9;// 输入为回车(\n)
				
					else if (isDigit(c)) {
						begin = i;
						state = 11;
					} else if (c == '#')
						state = 12;
					else if (c == '&')
						state = 14;
					else if (c == '|')
						state = 15;
					else if (c == '"')
						state = 16;
					else if (isRange(c))
						state = 17;// 界限阜
					else if (isBool(c))
						state = 18;
					else if (c == '%') 
						state = 19;
					//else if(c=='\r') state=20;
					else if (c == ';') state=21;
					else if (c == '.') state=22;
					else if (c == ',') state=23;
					//else if(c==' '||c=='\r'||c=='\t') state=22;
					else 
					{
						WordUnit word = new WordUnit();//
						word.setValue(""+c);
						word.setRow(line);
						//wordlist.add(word);
						state=0;
						//i--;
						}
					break;
				case 1:// 标志符是 +
						// row++;
					if (c == '+') 
					{
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue("++");
						word.setRow(line);
						word.setID(8);
						wordlist.add(word);
					} 
					else if (c == '=') 
					{
						state = 0;
						WordUnit word=new WordUnit();
						word.setRow(line);
						word.setValue("+=");
						wordlist.add(word);

					} 
					else 
					{
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue("+");
						word.setRow(line);
						wordlist.add(word);
						i--;
						row--;
					}
					break;
				case 2:// 标志符是 -
					if (c == '-'){
						WordUnit word=new WordUnit();
					word.setValue("--");
					word.setRow(line);
					word.setID(8);
					wordlist.add(word);
				}
					else if (c == '=')
					{
						
						WordUnit word=new WordUnit();
						word.setValue("-=");
						word.setRow(line);
						word.setID(8);
						wordlist.add(word);
					}
						
					else 
					{
						WordUnit word=new WordUnit();
						word.setValue("-");
						word.setID(8);
						word.setRow(line);
						wordlist.add(word);
						i--;
						row--;
					}
					state = 0;
					break;
				case 3:// 标志符是 *
					if (c == '=')
						
					{
						WordUnit word=new WordUnit();
						word.setValue("*=");
						word.setRow(line);
						word.setID(8);
						wordlist.add(word);
						}
					else {
						WordUnit word=new WordUnit();
						word.setValue("*");
						word.setID(8);
						word.setRow(line);
						wordlist.add(word);
						i--;
						row--;
					}
					state = 0;
					break;
				case 4:// 标志符是 /
					if (c == '/') 
					{
						while ((c) != '\n') {
							if(i>=N) break; 
							c = content.charAt(i);
							i++;
						}
						if(c=='\n')
						{
							state = 0;
							WordUnit word=new WordUnit();
							word.setValue("//");
							word.setRow(line);
							wordlist.add(word);
							line++;
							nouse++;
							i--;
						}
						state = 0;
						
					} 
					else if (c == '=') 
					{
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue("/=");
						word.setRow(line);
						wordlist.add(word);
					} 
					else if (c == '*') 
					{
						int inow=i;
						int invaline=0;
						while (!(c == '/' && (content.charAt(i - 2) == '*'))) 
						{
							
							if(i>=N) break; 
								c = content.charAt(i);
								if(c=='\n') {
									line++;
									invaline++;
								}
								i++;
								
							
						}
						if (c == '/' && (content.charAt(i - 2) == '*'))
						{
							//line-=invaline;
							nouse+=invaline;
							WordUnit word=new WordUnit();
							
							word.setValue("/**/");
							word.setID(40);
							word.setRow(line);
							//(line);
							wordlist.add(word);
							i--;
						}
						else 
						{
							i=inow;//说明不是注释
							WordUnit word=new WordUnit();
							word.setValue("/*");
							word.setRow(line);
							wordlist.add(word);
							//i--;
						}
						state = 0;
						
					}
					else 
					{
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue("/");
						word.setRow(line);
						wordlist.add(word);
						i--;
						row--;
					}
					// state = 0;
					break;
				case 5:// 标志符是 !
					if (c == '=') {
						WordUnit word=new WordUnit();word.setValue("!=");
						state = 0;
					} else {
						state = 0;
						i--;
						row--;
						WordUnit word=new WordUnit();
						word.setValue("!");
						word.setRow(line);
						wordlist.add(word);
						
					}
					// state = 0;
					break;
				case 6:// 标志符是 >
					if (c == '=') {
						WordUnit word=new WordUnit();
						word.setValue(">=");
						word.setRow(line);
						wordlist.add(word);
						state = 0;
					} else {
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue(">");
						word.setRow(line);
						wordlist.add(word);
						i--;
						
					}
					// state = 0;
					break;
				case 7:// 标志符是 <
					if (c == '=') {
						WordUnit word=new WordUnit();
						word.setValue("<=");
						word.setRow(line);
						wordlist.add(word);
						state = 0;
					} else {
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue("<");
						word.setRow(line);
						wordlist.add(word);
						i--;
					}
					break;
				case 8:// 标志符是 =
					if (c == '=') {
						WordUnit word=new WordUnit();
						word.setValue("==");
						word.setRow(line);
						wordlist.add(word);
						state = 0;
					} else {
						state = 0;
						WordUnit word=new WordUnit();
						word.setValue("=");
						word.setRow(line);
						wordlist.add(word);
						i--;
					}
					break;
				case 9:// 标志符是 换行
				
					line++;
					//\n\n
					if(c=='\n') 
					{
						nouse++;
						line++;
						state=0;
						//i--;
					}
					//后面是 空格或者是 制表符 
					else if((c) == '\t'||((c) == ' ')||((c) == '\r'))
					{
						//i++;
						while ((c) == '\t'||((c) == ' ')||((c) == '\r')) {
							if(i>=N) break; 
							c = content.charAt(i);
							i++;
						}
						if(c=='\n'&& i<=N) 
						{
							nouse++;
							line++;
							state=0;
							i--;
						}
						else if(i>=N)
						{
							state=0;
							i--;
						}
						else 
						{
							state=0;
							i--;
							i--;
							
						}
					}
					//默认模式
					else 
					{					
						state = 0;
						row = 1;
						i--;
					//line++;
					}
					break;
//				case 20:// 标志符是 回车
//					state = 0;
//					row = 1;
//					i--;
//					//line++;
//					break;
				case 10:// 标志符是 字母
					if (isLetter(c) || isDigit(c)) 
					{
						state = 10;
					} 
					else 
					{
						end = i;
						String id = content.substring(begin, end);
						if (isKey(id)) 
						{
							
							WordUnit word=new WordUnit();
							word.setValue(id);
							word.setID(7);
							word.setRow(line);
							wordlist.add(word);
							
						}
						else
						{
								WordUnit word=new WordUnit();
								word.setValue(id );
								word.setRow(line);
								wordlist.add(word);
						}
						
						i--;
						row--;
						state = 0;
					}
					
					break;
				case 11:// 标志符是 数字
					
					if (isDigit(c) ) 
					{
						// 省略跳过，i加一操作
						//i--;
						state=11;
					} 
					else {
						end = i;
						String id = content.substring(begin, end);
					
							WordUnit word=new WordUnit();
							word.setValue(id);
							word.setID(7);
							word.setRow(line);
							wordlist.add(word);
							
						
						i--;
						row--;
						state = 0;
					}

					break;
				case 12:// 标志符是 #
					{
						//error_text.append("info: # \n");
					WordUnit word = new WordUnit();
					word.setValue("#");
					word.setRow(line);
					wordlist.add(word);
					
					
					i--;
					// row--;

					state = 0;
					}
					break;
				
				case 14:// 逻辑运算发 &&
					if (c == '&')
					{
						WordUnit word=new WordUnit();
						word.setValue("&&");
						word.setRow(line);
						word.setID(10);
						wordlist.add(word);
						
					}	
					else {
						i--;
						WordUnit word=new WordUnit();
						word.setValue("&");
						word.setRow(line);
						wordlist.add(word);
					}
					state = 0;
					break;
				case 15:// 逻辑运算发 ||
					if (c == '|')
					{
						WordUnit word=new WordUnit();
						word.setValue("||");
						word.setRow(line);
						wordlist.add(word);
						
					}
					else 
					{
						i--;
						WordUnit word=new WordUnit();
						word.setValue("|");
						word.setRow(line);
						wordlist.add(word);
					}
					state = 0;
					break;
				case 16:
					{WordUnit word=new WordUnit();
					word.setValue("\"");
					word.setRow(line);
					wordlist.add(word);
					i--;
					state = 0;}
					break;
				// 特殊符是 {
				case 17:
				{
					WordUnit word=new WordUnit();
					word.setValue( ""+content.charAt(i - 1));
					word.setRow(line);
					wordlist.add(word);
					i--;

					state = 0;
					break;
					}
				case 18:
				{
					WordUnit word = new WordUnit();
					word.setValue( ""+content.charAt(i - 1));
					word.setRow(line);
					wordlist.add(word);
					i--;

					state = 0;
				}
					break;
				case 19:
				{
					WordUnit word = new WordUnit();
					word.setValue( ""+content.charAt(i - 1));
					word.setRow(line);
					wordlist.add(word);
					i--;

					state = 0;
				}
					break;
				case 20:
				{
					state = 0;
					row = 1;
					i--;

					
				}
				break;
				case 21:
				{
					WordUnit word = new WordUnit();
					word.setValue( ""+content.charAt(i - 1));
					word.setRow(line);
					wordlist.add(word);
					i--;

					state = 0;
				}
					break;
				case 22:
				{
					WordUnit word = new WordUnit();
					word.setValue(".");
					word.setRow(line);
					wordlist.add(word);
					i--;
					state = 0;
				}
					break;
				case 23:
				{
					WordUnit word = new WordUnit();
					word.setValue(",");
					word.setRow(line);
					wordlist.add(word);
					i--;
					state = 0;
				}
					break;
				
			}
				
		}
				
				
	}
			
			
			
			
			
			catch(Exception e)
			{
				
			}
		}
		this.rownum=line;
		this.valideline=line-nouse;
		//WordUnit word=new WordUnit();("Have checked lexical! \n");
		return row;

		
	}

	@Override
	public void outputWord(String midname) {
		// TODO Auto-generated method stub
		CFile file=new CFile(midname);
		WordUnit word=null;
		for(int i=0; i<wordlist.size(); i++)
		{
			word=wordlist.get(i);
			try {
				//if(word!=null) file.writeRow(word.ID+"\t"+word.getValue()+"\t"+word.getRow());
				if(word!=null) file.writeRow(word.getRow()+"\t"+word.getValue());
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

	@Override
	public List<WordUnit> getWordlist() {
		// TODO Auto-generated method stub
		
		return this.wordlist;
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
		return this.valideline;
	}boolean isLetter(char c){
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_')
            return true;
    return false;
}
boolean isBool(char c)
{
	if(c=='('||c==')') return true;
	return false;
}
boolean isRange(char c)
{
if(c=='{'||c=='}') return true;
return false;
}
boolean isDigit(char c){
    if(c >= '0' && c <= '9') return true;
    return false;
}
    boolean isKey(String str){
        if(KeyValue.key.contains(str)) return true;
        return false;
}
boolean isFunctionKey(String str){
    if(KeyValue.refunTypeKey.contains(str)) return true;
    return false;
}
int find(int begin, String str){//寻找分隔符空格、括号、回车等。
    if(begin >= str.length())
            return str.length();
    for(int i = begin; i < str.length(); i++){
            char c = str.charAt(i);
            if( c == ',' || c == ' ' || c == '\t' || c == ';' || c == '=' || c == '+'|| c == '-' || c == '*' || c == '/')
            {
            	//if(c=='\n') line++;
                    return i - 1;
            }
    }
    return str.length();
}
public static void main(String args[]) throws IOException
{
	String file="E:\\软件代码\\pyhon代码";
	String wordpath="e:\\versionfunction\\wordlist13.txt";
	String funfile="e:\\versionfunction\\function13.txt";
	ReadFileStartLex startlex=new ReadFileStartLex(file);
	startlex.readFileContent();
	String content=startlex.getContent();
	LexInterface lex=new JavaAnayase();
	lex.wordAnalysis(content);
	lex.outputWord(wordpath);
	FunctionInterface fj=new FunctionJudge();
	fj.setWordlist(lex.getWordlist());
	fj.recganize();
	
	
	List<String> funlist=fj.getFunlist();
	fj.outPutFunctionList(funfile);
	
} 
}
