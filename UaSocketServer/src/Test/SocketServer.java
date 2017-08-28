package Test;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;

import Test3.Data;
public class SocketServer {
	public static final int PORT = 12345;// 监听的端口号

	public static void main(String[] args) {
		System.out.println("服务器启动...\n");
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		SocketServer server = new SocketServer();
		server.init();
	}

	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				// 一旦有堵塞, 则表示服务器与客户端获得了连接
				Socket client = serverSocket.accept();
				// 处理这次连接
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("服务器异常: " + e.getMessage());
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {

			try {
				// 读取客户端数据
				System.out.println("客户端数据已经连接");
				DataInputStream inputStream = null;
				DataOutputStream outputStream = null;
				String strInputstream = "";
				inputStream = new DataInputStream(socket.getInputStream());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] by = new byte[2048];
				int n;
				while ((n = inputStream.read(by)) != -1) {
					baos.write(by, 0, n);
				}
				strInputstream = new String(baos.toByteArray());
				System.out.println("接受到的数据长度为：" + strInputstream);

				JSONArray toArray = JSONArray.fromObject(strInputstream.toString());
				List<Map> list = (List<Map>) JSONArray.toCollection(toArray, Map.class);
			//	Map map2 = list.get(0);
			//	System.out.println("list: " + map2.get("NodeId"));
			//	NodeId node1= (NodeId) JSONObject.toBean(JSONObject.fromObject(map2.get("NodeId")), NodeId.class);
				
				Map map3 = list.get(0);				
				for(int i=0;i<list.size();i++){
					Map map2 = list.get(i);
				//	System.out.println(map2);
					NodeId node= new NodeId((int)map2.get("NameSpace"),(String)map2.get("IdValue"));
					//判断是否存在表
					Data.run(node);
					//Date date1=(Date) map2.get("Time");
					JSONObject jobj = JSONObject.fromObject(map2.get("Time"));
					//JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"MM/dd/yyyy HH:mm:ss"}) );
					Date time = new Date();
					//(Date) JSONObject.toBean(jobj,java.util.Date.class);  
					System.out.println(time);
					//往表中存入数据
					Data.saveData(node,time,map2.get("value").toString());
					
				}
				
				
			//	System.out.println("value:::"+node.getValue());
				socket.shutdownInput();
				// inputStream.close();
				baos.close();

				// 处理客户端数据
				// 将socket接受到的数据还原为JSONObject
				// JSONArray json = new JSONArray(strInputstream);

				/*
				 * ArrayList<DataEntity>array=toArray. JSONObject
				 * json1=json.getJSONObject("id"); //生成节点ID int
				 * namespaceIndex=(int) json1.get("namespaceIndex"); String
				 * IdValue=(String) json1.get("value"); NodeId ni=new
				 * NodeId(namespaceIndex,IdValue);
				 * 
				 * int value= (int) json.get("value"); handleData(ni,value);
				 */

				/*
				 * int op = Integer.parseInt((String) json.get("num"));
				 * System.out.println(op);
				 */
				/*
				 * switch (op) {
				 * 
				 * // op为1 表示收到的客户端的数据为注册信息 op为2表示收到客户端的数据为检索信息
				 * 
				 * // 当用户进行的操作是注册时 case 1: String imgStr =
				 * json.getString("img"); String name = json.getString("name");
				 * // isSuccess 表示是否注册成功 String isSuccess = "1"; //
				 * System.out.println("imgStr:"+imgStr); // 用系统时间作为生成图片的名字
				 * 格式为yyyy-MM-dd-HH-mm-ss SimpleDateFormat df = new
				 * SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); String imgName =
				 * df.format(new Date()); //Base64Image.GenerateImage(imgStr,
				 * "D:\\fromjia\\imageDB\\primary\\" + imgName + ".jpg"); // do
				 * something to process this image // if success, return set
				 * isSuccess "1" // else set "0" System.out.println(name);
				 * System.out.println("服务器接受数据完毕");
				 * 
				 * // 向客户端回复信息 --json对象//to be continued; Map<String, String>
				 * map = new HashMap<String, String>(); map.put("isSuccess",
				 * isSuccess); json = new JSONObject(map); String jsonString =
				 * json.toString(); outputStream = new DataOutputStream(new
				 * BufferedOutputStream(socket.getOutputStream()));
				 * outputStream.writeUTF(jsonString); outputStream.flush();
				 * outputStream.close(); System.out.println("注册完成"); break; }
				 */

				// outputStream.close();
			} catch (Exception e) {
				System.out.println("服务器 run 异常: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("服务端 finally 异常:" + e.getMessage());
					}
				}
			}
		}
	}
}