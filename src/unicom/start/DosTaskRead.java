package unicom.start;


import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import task.DoTask;
import task.DoTaskFun;
import tool.MyStatic;
import unicom.CFile;
import unicom.CountTotal;
import unicom.Dir;
import unicom.FileViewer;

/**
 * @author wangwh   
 * param  filetypelist 文件的类型目录
 * param  threadPool   线程池
 * param  totalresult  汇总文件的输出  
 *
 */
public class DosTaskRead {
	
	long totalrow=0;
	long totalvalrow=0;
	Map<String,List<String>>  filetypelist=null;
	CFile totalresult=null;
	ExecutorService threadPool = Executors.newFixedThreadPool(Const.threadnum);
	public CFile getTotalresult() {
		return totalresult;
	}
	public void setTotalresult(CFile totalresult) {
		this.totalresult = totalresult;
	}

	/**
	 * @author wangwh
	 * param  mdir 测试代码目录
	 * param  threadPool   线程池
	 * param  totalresult  汇总文件的输出  
	 *
	 */
	public DosTaskRead(String mdir) {
		super();
		Dir dir=new Dir(mdir);
		
		Const.resut=dir.getPath()+Const.resutpr;
		Const.funDir=dir.getPath()+Const.funDirpr;
		Const.wordDir=dir.getPath()+Const.wordDirpr;
		Const.allfile=dir.getPath()+Const.allfilepr;
		Dir result=new Dir(dir.getPath()+Const.resutpr);
		totalresult=new CFile(Const.resut+Const.allstaticpr);
	}

	
	
	

	 

	/**
	 * 扫描一个目录下面的文件，并且按照类型划分存在 Map<String,List<String>>
	 * key 是 文件类型 
	 * value 是文件列表
	 */
	public void scanDirectry(String path)
	{

		
		 filetypelist=FileViewer.getListFiles(path,true);
		 CFile out=new CFile(Const.allfile);
		 out.writeMap(filetypelist);
		 try {
			totalresult.initCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @author wangwh 
	 *   suffix 是文件的后缀名
	 *   all表示所有的都统计 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 *
	 */
	public void travelMap(String suffix) throws NoSuchAlgorithmException, IOException, InterruptedException, ExecutionException
	{
		List lstFuture = new ArrayList(); 
		Future f;
		if(suffix.equals("all"))
		{ 
			if(filetypelist!=null) 
			{
					
				for (String key:filetypelist.keySet()) 
				{
					List<String> lf=filetypelist.get(key);//每个一个大类
					java.util.concurrent.Callable doTask = new DoTaskFun(lf,key,totalresult);
			        f = threadPool.submit(doTask);
			        lstFuture.add(f);
						
				}
					//
				 int lstSize = lstFuture.size();
			     for(int i = 0; i < lstSize; i++)
			     {
			         CountTotal total=(CountTotal)((Future)lstFuture.get(i)).get();
			         this.totalrow+=total.getTotalrow();
			         this.totalvalrow+=total.getTotalvalrow();
			         
			     }
			     totalresult.writeCsvTotal(totalrow,totalvalrow);
					
				}
		}
		else 
		{
			//只统计特定后缀的
			if(suffix!=null)
			{
				List<String> lf=filetypelist.get(suffix);//每个一个大类
				java.util.concurrent.Callable doTask = new DoTask(lf,suffix,totalresult);
				f = threadPool.submit(doTask);
				lstFuture.add(f);
			}
			 int lstSize = lstFuture.size();
		     for(int i = 0; i < lstSize; i++)
		     {
		         CountTotal total=(CountTotal)((Future)lstFuture.get(i)).get();
		         this.totalrow+=total.getTotalrow();
		         this.totalvalrow+=total.getTotalvalrow();
		         
		     }
		     totalresult.writeCsvTotal(totalrow,totalvalrow);
		}
		 
	}
	public boolean shutDown()
	{
		this.threadPool.shutdown();
		return true;
	}
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException
	{
		String mdr = "E:\\versionfunction\\cpp";
		mdr=args[0];
		String suffix = "all";
		suffix=args[1];
		System.out.println(mdr+"|"+suffix);
		MyStatic.readFileSet("binary/file.txt");
		
		
		DosTaskRead rd = new DosTaskRead(mdr);
		rd.scanDirectry(mdr);
		try {
			rd.travelMap(suffix);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
		//rd.getTotalresult().close();
		rd.shutDown();
		
		
	}

}
