package org.springweb.cache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CommentCountManager {
	
	private static Map<String,Integer> ipCommentCountDayMap = new LRUCache<String,Integer>(200);
	
	public synchronized static int add(String ip){
		if(ip == null || ip.isEmpty()){
			return 0;
		}
		int count = get(ip);
		ipCommentCountDayMap.put(ip + new SimpleDateFormat(";yyyy-MM-dd").format(new Date()), count+1);
		return count+1;
	}
	
	public synchronized static int get(String ip){
		if(ip == null || ip.isEmpty()){
			return -1;
		}
		String date = new SimpleDateFormat(";yyyy-MM-dd").format(new Date());
		Integer count = ipCommentCountDayMap.get(ip + date);
		if(count == null){
			return 0;
		}
		return count;
	}

}
