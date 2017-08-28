package Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import org.json.JSONObject;
import org.opcfoundation.ua.builtintypes.NodeId;


public class MyThread extends Thread {
 
	public static final String IP_ADDR = "202.117.15.54";// 服务器地址 这里要改成服务器的ip
	public static final int PORT = 12345;// 服务器端口号+++
	public static int num1=0;
	public static int num2=100;
	public static void register(List<DataEntity> de) {
		int isRegSuccess = 0;
		while (true) {
			Socket socket = null;
			try {
				// 创建一个流套接字并将其连接到指定主机上的指定端口号
				socket = new Socket(IP_ADDR, PORT);
				System.out.println("连接已经建立");
				// 将json转化为String类型
				//JSONObject json = new JSONObject(de);
				JSONArray json1 = JSONArray.fromObject(de);
				String jsonString = "";
				jsonString = json1.toString();
				// 将String转化为byte[]
				// byte[] jsonByte = new byte[jsonString.length()+1];
				byte[] jsonByte = jsonString.getBytes();
				DataOutputStream outputStream = null;
				outputStream = new DataOutputStream(socket.getOutputStream());
				System.out.println("发的数据长度为:" + jsonByte.length);
				outputStream.write(jsonByte);
				outputStream.flush();
				System.out.println("传输数据完毕");
				socket.shutdownOutput();

				/*// 读取服务器端数据
				DataInputStream inputStream = null;
				String strInputstream = "";
				inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				strInputstream = inputStream.readUTF();
				System.out.println("输入信息为：" + strInputstream);
				JSONObject js = new JSONObject(strInputstream);
				System.out.println(js.get("isSuccess"));
				isRegSuccess = Integer.parseInt((String) js.get("isSuccess"));
				// 如接收到 "OK" 则断开连接
				if (js != null) {
					System.out.println("客户端将关闭连接");
					Thread.sleep(500);
					break;
				}*/

			} catch (Exception e) {
				System.out.println("客户端异常:" + e.getMessage());
				break;
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket = null;
						System.out.println("客户端 finally 异常:" + e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void run() {
			List<DataEntity>array=new ArrayList<DataEntity>();
			DataEntity de1 = new DataEntity();
			DataEntity de2 = new DataEntity();	
			NodeId Id1 = new NodeId(2, "Test1");
			NodeId Id2 = new NodeId(2, "Test2");
			de1.setId(Id1);
			de2.setId(Id2);
			SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
			Date time = new Date();
			f.format(time);
			de1.setTime(time);
			de2.setTime(time);
			de1.setValue(num1++);
			de2.setValue(num2--);
			array.add(de1);
			array.add(de2);
			register(array);

	}
}
