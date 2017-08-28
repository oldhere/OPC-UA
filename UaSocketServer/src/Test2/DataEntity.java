package Test2;

import java.io.Serializable;
import java.util.Date;

public class DataEntity implements Serializable {
	private NodeId2 Id;
	public NodeId2 getId() {
		return Id;
	}
	public void setId(NodeId2 Id) {
		this.Id = Id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	private int value;
	private Date time; 
}
