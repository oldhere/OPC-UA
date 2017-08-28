package Pool;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBConnectionPool {
	private int minConn=1;
	private int maxConn=3;
	
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/opc";
	private String user="root";
	private String password="root";
	
	private int connAmount=0;
	private Stack<Connection> connStack=new Stack<Connection>(); //使用stack来保存
	private static DBConnectionPool instance=null;
	
	//返回唯一实例，如果是第一次调用此方法，则创建该实例
	public static synchronized DBConnectionPool getInstance(){
		if(instance==null){
			instance=new DBConnectionPool();
		}
		return instance;
	}

	//从连接池申请一个可用连接。如果没有空闲连接且当前连接数小于最大连接数，则创建新的连接
	public synchronized Connection getConnection(){
		Connection con=null;
		
		if(!connStack.empty()){
			con=(Connection)connStack.pop();
		}else if(connAmount<maxConn){
			con=newConnection();
		}else{
			
			try {
				wait(10000);
				return getConnection();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return con;
	}
	
	//将不再使用的连接返回给连接池
	public synchronized void freeConnection(Connection con){
		connStack.push(con);
		notifyAll();
	}
	
	//创建新连接
	private Connection newConnection(){
		Connection con=null;
		try {
			con=DriverManager.getConnection(url,user,password);
			connAmount++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return con;
	}
	
	
	

}
