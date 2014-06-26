package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MyStatic {

	/**
	 * @param args
	 */
	public static Set fileset=new HashSet();
	public static Set<String> javaset=new HashSet();
	static {
		javaset.add("while");
		javaset.add("for");
		javaset.add("if");
		javaset.add("switch");
	}
	public static boolean readFileSet(String path) 
	{
		BufferedReader br=DicReader.getReader(path);
		try {
			for(String line; (line=br.readLine())!=null;)
			{
				fileset.add(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			MyStatic.readFileSet("binary/file.txt");
		 

	}

}
