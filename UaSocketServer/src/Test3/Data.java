package Test3;

import java.sql.*; 
import java.text.SimpleDateFormat;

import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;

import Pool.DBConnectionPool;
public class Data {
	static Connection connect = DBConnectionPool.getInstance().getConnection();
	public static boolean hasNode(NodeId nodeid) throws Exception{
		 Statement stmt = connect.createStatement(); 
		 ResultSet rs = stmt.executeQuery("select * from nodeid where namespace="+nodeid.getNamespaceIndex()+"&&value='"+nodeid.getValue().toString()+"';"); 
		 if(rs.next()) { 
		    return true; 
		 }else{
			 return false;
		 }		
	}

	public static void createTable(String s) throws Exception{
		String sql = "create table "+s+"(time datetime,value varchar(20));";
		PreparedStatement pstmt = connect.prepareStatement(sql);
		pstmt.executeUpdate();	
	}
	
	public static void saveNodeId(NodeId nodeid) throws Exception{
		PreparedStatement stmt = connect.prepareStatement("insert into nodeid values("+nodeid.getNamespaceIndex()+",'"+nodeid.getValue().toString()+"');");
		stmt.executeUpdate();	
	}
	public static void saveData(NodeId node,java.util.Date time,String value) throws Exception{
		PreparedStatement stmt = connect.prepareStatement("insert into "+node.getValue().toString()+node.getNamespaceIndex()+" values('"+new java.sql.Timestamp(time.getTime())+"','"+value+"');");
		stmt.executeUpdate();
	}
	
	 public static void run(NodeId node) throws Exception { 

			 if(!hasNode(node)){
				// createTable(node.getValue().toString()+node.getNamespaceIndex());
				 createTable(node.toString());
				 saveNodeId(node);
				 System.out.println(node);
				}else{
					System.out.println(node+"table existed!");
				} 	  
	 }
}
