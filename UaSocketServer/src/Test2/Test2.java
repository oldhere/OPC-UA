package Test2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test2 {
	public static void main(String[]args) throws FileNotFoundException, IOException, ClassNotFoundException{
		/*Timestamp timestamp  = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
		System.out.println(timestamp.toString().length());
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		Date time = new Date();
		f.format(time);
		System.out.println(time.);*/
		DataEntity de1 = new DataEntity();
		NodeId2 id2=new NodeId2();
		id2.setName("Test1");
		id2.setNs(2);
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		Date time = new Date();
		f.format(time);
		de1.setTime(time);
		de1.setId(id2);
		de1.setValue(5);
		List<DataEntity> array=new ArrayList<DataEntity>();
		for(int i=0;i<200000;i++){
			array.add(de1);
		}
		//获取开始时间
		long startTime = System.currentTimeMillis();    
		ObjectOutputStream os = new ObjectOutputStream(  
				new FileOutputStream("C:/Users/zhou/Desktop/a.txt"));
		
		os.writeObject(array);// 将array对象写进文件  
		os.close();
		 //获取结束时间
		long endTime = System.currentTimeMillis(); 
		System.out.print("运行时间：");
		System.out.println(endTime-startTime);
		
		
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(  
                "C:/Users/zhou/Desktop/a.txt"));  
		//获取开始时间
				long startTime1 = System.currentTimeMillis();
		List<DataEntity> tempList = (List<DataEntity>) is.readObject();// 从流中读取List的数据 
		//获取结束时间
				long endTime1 = System.currentTimeMillis(); 
				System.out.print("读取时运行时间：");
				System.out.println(endTime1-startTime1);	
		System.out.println(tempList.size());
		System.out.println(tempList.get(0).getValue());
	}
}
