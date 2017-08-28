package Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.json.JSONObject;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.NodeId;

public class MyThread2 extends Thread {

	public static final String IP_ADDR = "127.0.0.1";// 鏈嶅姟鍣ㄥ湴鍧� 杩欓噷瑕佹敼鎴愭湇鍔″櫒鐨刬p
	public static final int PORT = 12345;// 鏈嶅姟鍣ㄧ鍙ｅ彿
	public static int num1 = 0;
	public static int num2 = 10000;

	public static void register(List<Map<String, Object>> de) {
		// String imgStr = Base64Image.GetImageStr(imgPath);//鏄皢鍥剧墖鐨勪俊鎭浆鍖栦负base64缂栫爜
		int isRegSuccess = 0;
		while (true) {
			Socket socket = null;
			try {
				// 鍒涘缓涓�涓祦濂楁帴瀛楀苟灏嗗叾杩炴帴鍒版寚瀹氫富鏈轰笂鐨勬寚瀹氱鍙ｅ彿
				socket = new Socket(IP_ADDR, PORT);
				System.out.println("杩炴帴宸茬粡寤虹珛");
				// 灏唈son杞寲涓篠tring绫诲瀷
				JSONObject json = new JSONObject(de);
				JSONArray json1 = JSONArray.fromObject(de);
				String jsonString = "";
				jsonString = json.toString();
				// 灏哠tring杞寲涓篵yte[]
				// byte[] jsonByte = new byte[jsonString.length()+1];
				byte[] jsonByte = jsonString.getBytes();
				DataOutputStream outputStream = null;
				outputStream = new DataOutputStream(socket.getOutputStream());
				System.out.println("鍙戠殑鏁版嵁闀垮害涓�:" + jsonByte.length);
				outputStream.write(jsonByte);
				outputStream.flush();
				System.out.println("浼犺緭鏁版嵁瀹屾瘯");
				socket.shutdownOutput();

				// 璇诲彇鏈嶅姟鍣ㄧ鏁版嵁
				DataInputStream inputStream = null;
				String strInputstream = "";
				inputStream = new DataInputStream(new BufferedInputStream(
						socket.getInputStream()));
				strInputstream = inputStream.readUTF();
				System.out.println("杈撳叆淇℃伅涓猴細" + strInputstream);
				JSONObject js = new JSONObject(strInputstream);
				System.out.println(js.get("isSuccess"));
				isRegSuccess = Integer.parseInt((String) js.get("isSuccess"));
				// 濡傛帴鏀跺埌 "OK" 鍒欐柇寮�杩炴帴
				if (js != null) {
					System.out.println("瀹㈡埛绔皢鍏抽棴杩炴帴");
					Thread.sleep(500);
					break;
				}

			} catch (Exception e) {
				System.out.println("瀹㈡埛绔紓甯�:" + e.getMessage());
				break;
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket = null;
						System.out.println("瀹㈡埛绔� finally 寮傚父:" + e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void run() {
		List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 5; i++) {
			NodeId Id1 = new NodeId(i, "Test" + i);
			NodeId Id2 = new NodeId(i + 1, "Test" + (i + 1));

			for (int j = 0; j < 2; j++) {
				Map<String, Object> map1 = new HashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				// DataEntity de1 = new DataEntity();
				// DataEntity de2 = new DataEntity();

				/*
				 * Id1.setNamespaceIndex(2); Id2.setNamespaceIndex(2);
				 * Id1.setValue("Text1"); Id2.setValue("Text2");
				 */
				map1.put("NameSpace", Id1.getNamespaceIndex());
				map2.put("NameSpace", Id2.getNamespaceIndex());
				map1.put("IdValue", Id1.getValue());
				map2.put("IdValue", Id2.getValue());
				// SimpleDateFormat f = new
				// SimpleDateFormat("yyyy骞碝M鏈坉d鏃� hh:mm:ss");
				Date time = new Date();
				// f.format(time);
				// de1.setTime(time);
				// de2.setTime(time);
				map1.put("Time", time);
				map2.put("Time", time);

				map1.put("value", num1++);
				map2.put("value", num2--);
				arrayList.add(map1);
				arrayList.add(map2);
			}
		}// register(arrayList);

		Socket socket = null;
		try {
			// 鍒涘缓涓�涓祦濂楁帴瀛楀苟灏嗗叾杩炴帴鍒版寚瀹氫富鏈轰笂鐨勬寚瀹氱鍙ｅ彿
			socket = new Socket(IP_ADDR, PORT);
			System.out.println("杩炴帴宸茬粡寤虹珛");
			// 灏唈son杞寲涓篠tring绫诲瀷
			JSONArray json1 = JSONArray.fromObject(arrayList);
			String jsonString = "";
			jsonString = json1.toString();
			// 灏哠tring杞寲涓篵yte[]
			// byte[] jsonByte = new byte[jsonString.length()+1];
			byte[] jsonByte = jsonString.getBytes();
			DataOutputStream outputStream = null;
			outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("鍙戠殑鏁版嵁闀垮害涓�:" + jsonByte.length);
			outputStream.write(jsonByte);
			outputStream.flush();
			System.out.println("浼犺緭鏁版嵁瀹屾瘯");
			socket.shutdownOutput();

		} catch (Exception e) {
			System.out.println("瀹㈡埛绔紓甯�:" + e.getMessage());
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					System.out.println("瀹㈡埛绔� finally 寮傚父:" + e.getMessage());
				}
			}
		}
	}

}
