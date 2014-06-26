package task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import unicom.CFile;
import unicom.FileInfo;
import unicom.FunUnit;
import unicom.MD5;
import unicom.ReadFileStartLex;
import unicom.WordUnit;
import unicom.start.Const;

/**
 * @author wangwh 
 *  DosTaskFun 继承 DoTask
 *  实现了文件内部函数识别输出，每个类型的文件对应一个 typeFun.csv文件
 *  param funresult  typeFun.csv文件
 */
public class DoTaskFun extends DoTask {
	
	CFile funresult=null;//每个结果分别输出

	public DoTaskFun(List<String> lf, String suffix, CFile totalresult) {
		super(lf, suffix, totalresult);
		// TODO Auto-generated constructor stub
		funresult=new CFile(Const.resut+type+"fun.csv");//每种类型汇总文件分别输出结果
		funresult.initFunCsv();
		
	}
	 public void reverseMd(String path) 
		{
			
			System.out.println(path);
			//CountTotal ct=new CountTotal();
			FileInfo fi=new FileInfo();
			String fname="";
			fi.setPath(path);
			File fin=new File(path);
			if(fin!=null) fname=fin.getName(); 
			ReadFileStartLex startlex=new ReadFileStartLex(path);
			startlex.readFileContent();
			if(!startlex.isTxt()) return ;//不是文本文件直接退出
			String content=startlex.getContent();
			lex.wordAnalysis(content);
			List<WordUnit> wordlist=lex.getWordlist();
			fj.setWordlist(wordlist);
			fj.recganize();
			List<String> funlist=fj.getFunlist();
			fi.setTotalrow(lex.getRownum());
			fi.setValrow(lex.getValideline());
			fi.setName(fname);
			fi.setFprint(MD5.makeMD5(content));
			fi.setFwprint(MD5.makeMD5Word(wordlist));
			fi.setFfprint(MD5.makeListMD5(funlist));//一页代码抽出的所有函数计算hash
			result.writeCsv(fi);
			List<FunUnit> fununitlist=fj.getFununitlist();
			funresult.writeFileWithFun(fname,fununitlist,path);
			synchronized(totalresult)
			{
				totalresult.writeCsv(fi);
			}
			totalrow+=lex.getRownum();
			totalvalrow+=lex.getValideline();
			//System.out.println("--------------");
			return ;
		
		}

}
