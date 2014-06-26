package unicom;

import java.io.*;
import java.util.*;

//import org.apache.log4j.Logger;  

import tool.MyStatic;

/**
 * 读取目录及子目录下指定文件名的路径, 返回一个List
 */
/**
 * @param path
 *            文件路径
 * @param suffix
 *            后缀名, 为空则表示所有文件
 * @param isdepth
 *            是否遍历子目录
 * @return list
 */
public class FileViewer {
	// private static Logger logger = Logger.getLogger(FileViewer.class);
	
	public static Map<String, List<String>> getListFiles(String path,boolean isdepth) 
	{
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		File file = new File(path);
		return FileViewer.mapFile(map, file, isdepth);
	}
	// 若是目录, 采用递归的方法遍历子目录  若文件无后缀名则不处理
	private static Map<String, List<String>> mapFile(Map<String, List<String>> map, File f, boolean isdepth) 
	{
		
		MyStatic.readFileSet("binary/file.txt");
		if (f.isDirectory()) 
		{
			File[] t = f.listFiles();

			for (int i = 0; i < t.length; i++) {
				if (isdepth || t[i].isFile()) {
					mapFile(map, t[i], isdepth);
				}
			}
		} 
		else 
		{
			String filePath = f.getAbsolutePath();
			int begIndex = filePath.indexOf("."); // 最后一个.(即后缀名前面的.)的索引
			String tempsuffix = "";
			if (begIndex != -1) {
				tempsuffix = filePath.substring(begIndex + 1, filePath.length());
				if (txtFilter(tempsuffix)) {
					if (map.containsKey(tempsuffix)) {
						List vl = map.get(tempsuffix);
						if (vl != null)
							vl.add(filePath);
					} else {
						List<String> newtype = new LinkedList<String>();
						if (newtype != null)
							newtype.add(filePath);
						map.put(tempsuffix, newtype);
					}
				}
			}

			else {
//				if (!map.containsKey("")) {
//					List<String> emptytype = new LinkedList<String>();
//					map.put("", emptytype);
//					emptytype.add(filePath);
//				} else {
//					map.get("").add(filePath);
//				}
			}
		}
		return map;
	}
	//文件后缀名名中不能包含图片、以及. \等
	public static boolean txtFilter(String fname) {
		boolean flag = true;

		if (MyStatic.fileset.contains(fname)||fname.contains(".")||fname.contains("\\")||fname.equals("")) 
		{
			flag = false;
		}
		return flag;

	}

	public static void main(String args[]) throws IOException {
		String path = "E:\\versionfunction";
		String suffix = "java";
		Map<String, List<String>> pathlist = FileViewer
				.getListFiles(path, true);
		CFile out = new CFile("e:\\outfilter.txt");
		out.writeMap(pathlist);

		// System.out.
	}
}
