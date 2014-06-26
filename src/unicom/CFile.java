package unicom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class CFile {
	File f=null;
	public CFile(String name) {
		super();
		this.f = new File(name);
		if(f.exists()) 
		{
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CFile() {
		super();
	}

	public void makeFile(String fname) throws IOException
	{
		if(f==null)
		f=new File(fname);
		f.createNewFile();
		
	}
	public void writeOneRow(String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str);
		bf.close();
	}
	public void writeRow(String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+""+"\r\n");
		bf.close();
	}
	public void writeCountRow(long i,String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+","+i+","+"\r\n");
		bf.close();
	}
	public void writeCountFile(long i,String str,String b) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+","+i+","+b+","+"\r\n");
		bf.close();
	}
	public void writeHead(String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+","+"\r\n");
		bf.close();
	}
	public void writeList(List<String> list)
	{
		for(String s:list)
		{
			try {
				writeRow(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//函数开始，结束，
	public void writeFunUnitList(List<FunUnit> list)
	{
		for(FunUnit s:list)
		{
			try {
				writeRow(s.getRow()+","+s.getEnd()+","+s.getFunstr());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void writeFileWithFun (String fname,List<FunUnit> list,String path)
	{
		StringBuffer sb=new StringBuffer();
		for(FunUnit s:list)
		{
			if(s.getFunstr()!=null)	sb.append(fname+","+s.getRow()+","+s.getEnd()+","+MD5.makeMD5(s.getFunstr())+","+path+"\r\n");
		}
		try {
			writeOneRow(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeCsv(FileInfo fi) 
	{
		FileWriter fw=null;
		try {
			fw = new FileWriter(f,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bf=new BufferedWriter(fw);
		try {
			bf.write(fi.getTotalrow()+","+fi.getValrow()+","+fi.getPattonline()+","+fi.getFprint()+","+fi.getFwprint()+","+fi.getFfprint()+","+fi.getName()+","+fi.getPath()+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initCsv() throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write("Total Lines"+","+"Valid Lines"+","+"Patts Lines"+","+"File Hash"+","+"Key Hash"+","+"FunHash"+","+"Filename"+","+"Path"+"\r\n");
		bf.close();
	}
	public void initFunCsv() 
	{
		FileWriter fw=null;
		try {
			fw = new FileWriter(f,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bf=new BufferedWriter(fw);
		try {
			bf.write("Filename"+","+"Start"+","+"End"+","+"FunMd5"+","+"Path"+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeMap( Map<String,List<String>> map)
	{
		long c=0;
		try {
			writeHead("path,row");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(map!=null) 
		{
			for ( String key : map.keySet()) 
			{
				List<String> lf=map.get(key);
				for(String s:lf)
				{
					try {
						c++;
						writeCountRow(c,s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
		public void writeFileMap( Map<String,List<String>> map)
		{
			long c=0;
			try {
				writeHead("path,row,txt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(map!=null) 
			{
				for ( String key : map.keySet()) 
				{
					List<String> lf=map.get(key);
					for(String s:lf)
					{
						try {
							c++;
							ReadFileStartLex startlex=new ReadFileStartLex(s);
							 startlex.readFileContent();
							 boolean b=startlex.isTxt();
							 String is="不是";
							 if(b) is="是";
							writeCountFile(c,s,is);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	
	
	public void writeCsvTotal(long all,long val) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(all+","+val+","+""+","+""+","+""+","+""+","+""+","+""+"\r\n");
		bf.close();
	}
}
