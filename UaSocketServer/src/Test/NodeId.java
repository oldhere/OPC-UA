package Test;

public class NodeId {
	private String idType;
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public int getNamespaceIndex() {
		return namespaceIndex;
	}
	public void setNamespaceIndex(int namespaceIndex) {
		this.namespaceIndex = namespaceIndex;
	}
	public boolean isNullNodeId() {
		return nullNodeId;
	}
	public void setNullNodeId(boolean nullNodeId) {
		this.nullNodeId = nullNodeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private int namespaceIndex;
	private boolean nullNodeId;
	private String value;
}
