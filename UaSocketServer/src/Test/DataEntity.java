package Test;

import java.util.Date;
import org.opcfoundation.ua.builtintypes.NodeId;

public class DataEntity {
	private NodeId Id;
	public NodeId getId() {
		return Id;
	}
	public void setId(NodeId Id) {
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
