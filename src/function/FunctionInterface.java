package function;

import java.util.List;

import unicom.FunUnit;
import unicom.WordUnit;

public interface FunctionInterface {
	public void recganize();
	public List<String> getFunlist();
	public List<FunUnit> getFununitlist();
	public void setWordlist(List<WordUnit> wordlist);
	public void outPutFunctionList(String path);

}
