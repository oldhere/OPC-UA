package Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class test {
	public static void main(String[]args){
		List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>> ();

		for(int i = 0; i < 5; i++){
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put(String.valueOf(i), i);
		    arrayList.add(map);
		}

		JSONArray jsonArray = JSONArray.fromObject(arrayList);
		System.out.print(jsonArray);

	}
}
