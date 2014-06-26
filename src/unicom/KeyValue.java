package unicom;

import java.util.HashSet;
import java.util.TreeSet;
/*
*/
import java.util.Set;

public class KeyValue {
	public static Set<String> key=new HashSet<String>();
	public static Set<String> refunTypeKey=new TreeSet<String>();
	static 
	{
  

		String keyword="abstract   boolean   break   byte   case   catch   char   class "

+"const   continue   default   do   double   else   extends   false "

+"final   finally   float   for   goto   if   implements   import "

+"instanceof   int   interface   long   native   new   null   package "

+"private   protected   public   return   short   static   super   switch "

+"synchronized   this   throw   throws   transient   true   try   void "

+"volatile   while";
		//String []returnfunction={"void","int"};
		
		String keyarr[]=keyword.split("\\s{1,}");
		for(String s:keyarr)
		{
			key.add(s);
			if(s.equals("void")||s.equals("boolean")||s.equals("int")||s.equals("float")||s.equals("double"))
				refunTypeKey.add(s);	
		}
	}
	

}
