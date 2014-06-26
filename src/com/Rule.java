package com;

public class Rule {
	//s->C
	String r;
	String l;
	public Rule(String l, String r) {
		super();
		this.r = r;
		this.l = l;
	}
	public void printRule()
	{
		System.out.println(l+"->"+r);
	}
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r = r;
	}
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	

}
