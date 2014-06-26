package unicom;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MD5 {
	String funstr;
	//String fp;
	static char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static String makeMD5(String in) 
	{
		//funstr=in;
		String fp;
		byte []btinput=in.getBytes();
		
		MessageDigest mdinst=null;
		try {
			mdinst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mdinst.update(btinput);
		byte[]md=mdinst.digest();
		int j=md.length;
		char str[]=new char [j*2];
		int k=0;
		for(int i=0; i<j;i++)
		{
			byte byte0=md[i];
			str[k++]=hexDigits[byte0>>>4&0xf];
			str[k++]=hexDigits[byte0&0xf];
		}
		fp=new String(str);
		return fp;
		
	}
	public static  String makeListMD5(List<String> in)
	{
		String mdfp=null;
		String ls=null;
		StringBuffer bf=new StringBuffer("");
		for(String s:in)
		{
			bf.append(s);
			//
		}
		ls=bf.toString();
		mdfp=makeMD5(ls);
		ls=null;
		return mdfp;
	}
	public static  String makeMD5Word(List<WordUnit> in)
	{
		String mdfp=null;
		String ls=null;
		StringBuffer bf=new StringBuffer("");
		
		for(WordUnit s:in)
		{
			bf.append(s.getValue());
			//
		}
		ls=bf.toString();
		mdfp=makeMD5(ls);
		ls=null;
		return mdfp;
	}
	public static void main(String args[]) throws NoSuchAlgorithmException
	{
		String s=MD5.makeMD5("sssss");
		System.out.println(s);
	}
	

}
