package unicom;

public class FileInfo {
	int totalrow;
	int valrow;
	String pattonline;//已有库样本
	String fprint;//文件指纹
	String fwprint;//词的指纹
	String ffprint;//函数指纹
	String name;
	String safpath;
	String path;
	String proname;//项目名称
	public int getTotalrow() {
		return totalrow;
	}
	public String getPattonline() {
		return pattonline;
	}
	public void setPattonline(String pattonline) {
		this.pattonline = pattonline;
	}
	public void setTotalrow(int totalrow) {
		this.totalrow = totalrow;
	}
	public int getValrow() {
		return valrow;
	}
	public void setValrow(int valrow) {
		this.valrow = valrow;
	}
	public String getFprint() {
		return fprint;
	}
	public void setFprint(String fprint) {
		this.fprint = fprint;
	}
	public String getFwprint() {
		return fwprint;
	}
	public void setFwprint(String fwprint) {
		this.fwprint = fwprint;
	}
	public String getFfprint() {
		return ffprint;
	}
	public void setFfprint(String ffprint) {
		this.ffprint = ffprint;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSafpath() {
		return safpath;
	}
	public void setSafpath(String safpath) {
		this.safpath = safpath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	

}
