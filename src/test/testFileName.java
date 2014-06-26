package test;

import java.io.File;

public class testFileName {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname="";
		String path="e:\\versionfunction\\wordlist12.txt";
		File fin=new File(path);
		if(fin!=null) fname=fin.getName(); 
		System.out.println(fname);

	}

}
