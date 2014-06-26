package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangwh
 *   研究 java的正则表达式 
 *   默认情况下, .不会匹配换行符, 设置了Dotall模式, .会匹配所有字符包括换行符比如
 *
 */
public class testReg {

	/** 
	 * @param args
	 */
	
	public void testMatch(String content)
	{
		int rownum=0;
		int nouse=0;
		int valideline=0;
		String test="<!--7897"+"\n"+"7799-->";
		Pattern p=Pattern.compile("\\n",Pattern.CASE_INSENSITIVE);//匹配换行
		Pattern word=Pattern.compile("\\b\\w+\\b",Pattern.CASE_INSENSITIVE);
		Pattern d=Pattern.compile("\\b\\d+\\b",Pattern.CASE_INSENSITIVE);
		//Pattern nodes=Pattern.compile("<.*(\\r\\n)*?-->",Pattern.DOTALL);
		Pattern nodes=Pattern.compile("<!.*-->",Pattern.DOTALL);
		Matcher m=nodes.matcher(content);
		boolean b=false;
		while(b=m.find())
		{
			rownum++;
			System.out.println(m.start()+"| "+m.group()+" "+rownum);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content="<!htlltlt"+"\r\n"+"tttjkjkljljj-->";
		testReg test=new testReg();
		test.testMatch(content);

	}

}
