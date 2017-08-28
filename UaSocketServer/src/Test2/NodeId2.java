package Test2;

import java.io.Serializable;

public class NodeId2 implements Serializable{
	public int getNs() {
		return ns;
	}
	public void setNs(int ns) {
		this.ns = ns;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int ns;
	private String name;
}
