package org.springweb.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springweb.bean.Category;

public class CatTreeSortHelp {

	public static Map<Long,List<Category>> sort(List<Category> list, Long id, Long parentId){
		LinkedHashMap<Long,List<Category>> resMap = new LinkedHashMap<Long,List<Category>>();
		if(list == null || list.isEmpty()){
			return resMap;
		}
		Long tmpParentId = parentId; 
		for(Category cat: list){
			if(cat.getParentId() == 0){
				continue;
			}
			if(resMap.get(cat.getParentId()) == null){
				resMap.put(cat.getParentId(), new ArrayList<Category>());
			}
			List<Category> tmpList = resMap.get(cat.getParentId());
			tmpList.add(cat);
			if(id != null && cat.getId() == id){
				tmpParentId = cat.getParentId();
			}
		}
		if(tmpParentId != null ){
			List<Category> catList = resMap.remove(tmpParentId);
			LinkedHashMap<Long,List<Category>> nextResMap = new LinkedHashMap<Long,List<Category>>();
			nextResMap.put(tmpParentId, catList);
			nextResMap.putAll(resMap);
			return nextResMap;
		}
		return resMap;
	}
	
	public static Map<Long, Category> toMap(List<Category> list){
		Map<Long,Category> resMap = new HashMap<Long,Category>();
		if(list == null || list.isEmpty()){
			return resMap;
		}
		for(Category cat: list){
			resMap.put(cat.getId(), cat);
		}
		return resMap;
	}
}
