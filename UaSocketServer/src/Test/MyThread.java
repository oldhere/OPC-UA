package Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.opcfoundation.ua.builtintypes.NodeId;


public class MyThread extends Thread {

	static int num = 0;
	public static final String IP_ADDR = "202.117.15.54";// 服务器地址 这里要改成服务器的ip
	public static final int PORT = 12345;// 服务器端口号

	public static void register(DataEntity de) {
		// String imgStr = Base64Image.GetImageStr(imgPath);//是将图片的信息转化为base64编码
		int isRegSuccess = 0;
		while (true) {
			Socket socket = null;
			try {
				// 创建一个流套接字并将其连接到指定主机上的指定端口号
				socket = new Socket(IP_ADDR, PORT);
				System.out.println("连接已经建立");
				// 向服务器端发送数据
				/*
				 * Map<String, String> map = new HashMap<String, String>();
				 * map.put("num", num + "");
				 */
				// 将json转化为String类型
				JSONObject json = new JSONObject(de);
				String jsonString = "";
				jsonString = json.toString();
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

				// 读取服务器端数据
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
				}

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
		while (true) {
			try {
				new Thread();
				Thread.sleep(500);
				if (num > 100) {
					num = 0;
				}
				DataEntity de = new DataEntity();
				NodeId Id = new NodeId(2, "MyLevel");
				//.setId(Id);
				SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
				Date time = new Date();
				f.format(time);
				de.setTime(time);
				de.setValue(num);
				register(de);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
