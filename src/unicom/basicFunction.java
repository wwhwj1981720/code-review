package unicom;
/**
 * 进行简单的词法分析
 * @author lenovo
 *
 */
public class basicFunction {
	   public String input="";    //输入的源程序
	   public char ch;         //存放最新读进的源程序的字符
	   public char nextch;
	   int len=0;
	   public String strToken=""; //存放构成单词符号的字符串
	   public int index=0;       //存放此时搜索指示器指向的字符位置
	   public int index_buf;   //buffer中搜索指示器指向的字符位置   
	   public int index_save=0;
	   public basicFunction(String input){
		   this.input = input;
		   len=input.length();
	   }
	   public char getCh() {
		   return ch;
	   }
	   public void setCh(char ch) {
		   this.ch = ch;
	   }
	   public String getInput() {
		    return input;
	   }
	   public void setInput(String input) {
		   this.input = input;
	   }
	   public String getStrToken() {
		   return strToken;
	   }
	   public void setStrToken(String strToken) {
		   this.strToken = strToken;
	   }
	//将下一输入字符读到ch中，搜索知识器前移一个字符位置
	   public int GetChar(){
		  int result=0; 
		  this.ch = this.input.charAt(index);
		  index++;	
		  if(index<len)
		  {
			  this.nextch=this.input.charAt(index);
		  }
		  		  return 0;
	   } 
	   public int GetNextChar(){
			  
		 
		   
		   if((index+1)<len)
		   {
			   this.ch = this.input.charAt(index+1);
			   return 0;
		   }
			  //index++;	   
			  return 1;
		   } 
	   
	   //检查ch中的字符是否为空白。若是，则调用GetChar直至不是字符为止
	   public char GetBC(){
		   while(ch==' '||ch=='\n'||ch=='\r'){
			   GetChar();
		   }
		   return ch;
	   }   
	   //将ch中的字符连接到strToken之后
	   public String Concat(){
		   strToken=strToken.concat(String.valueOf(ch));
		   return strToken;
	   }   
	   //判断ch中的字符是否为字母
	   public boolean IsLetter(){
		   boolean flag=false;
		   if(ch>='a' && ch<='z' || ch>='A' && ch<='Z' ){
			   flag=true;
		   }
		   return flag;
	   }   
	   //判断ch中的字符是否为数字
	   public boolean IsDigit(){
		   boolean flag=false;
		   if(ch>='0' && ch<='9' ){
			   flag=true;
		   }
		   return flag;
	   }
	   //对strToken中的字符创查找保留字表，若是则返回它的编码，否则返回0
	   //注：在编写保留字表的时候要从1开始编号，不能从0开始编号！
	   public int Reserve(){
		   WordList wl = new WordList();
		   int f = wl.Reserve(strToken);
		   return f;   //返回0表示不在保留字之中。
	   }   
	   //将搜索指示器回调一个字符位置
	   public void Retract(){
		   ch=' ';
		   int l = strToken.length();
		   if(l>1){
			  strToken = strToken.substring(0,l-1);
		   }
		   index--;
	   }   
	   //将strToken置空
	   public void RetractStr(){
		   strToken="";
	   }
	   public void savePos()
	   {
		   index_save=index;
	   }
	   public void backPos()
	   {
		   index=index_save;
	   }
	}