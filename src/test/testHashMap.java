package test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class testHashMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,List<String>> map=new HashMap<String,List<String>>();
		List<String> l=new  LinkedList<String>();
		map.put("cpp", l);
		l.add("e");
		l.add("e");

	}

}
