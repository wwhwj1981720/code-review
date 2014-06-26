package unicom;

public class Pre {
	public int ipr;
	public boolean b;
	
	
	public boolean isB() {
		return b;
	}
	public int getIpr() {
		return ipr;
	}
	public void setIpr(int ipr) {
		this.ipr = ipr;
	}
	public void setB(boolean b) {
		this.b = b;
	}
	public Pre(int iend, boolean b) {
		super();
		this.ipr = iend;
		this.b = b;
	}

}
