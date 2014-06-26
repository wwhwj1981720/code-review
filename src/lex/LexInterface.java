package lex;

import java.util.List;

import unicom.WordUnit;

public interface LexInterface {
	public int wordAnalysis(String content);
	public void outputWord(String midname);
	public List<WordUnit> getWordlist() ;
	public int getRownum();
	public int getNouse() ;
	public int getValideline();

}
