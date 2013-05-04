package org.springweb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springweb.bean.Category;


public class CategoryDao extends SqlMapClientDaoSupport{

	public List<Category> query(Long id, Long parentId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("parentId", parentId);
		List<Category> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-CATEGORY-ALL", param);
		return list;
	}
	
	public long insert(Category category){
		if(category == null){
			return 0L;
		}
		return (Long) this.getSqlMapClientTemplate().insert("jiagoushi.CATEGORY-INSERT", category);
	}
	
	public int update(long id, String name, long parentId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("name", name);
		param.put("parentId", parentId);
		int cnt = this.getSqlMapClientTemplate().update("jiagoushi.UPDATE-CATEGORY", param);
		return cnt;
	}
	
	public int delete(long id){
		int cnt = this.getSqlMapClientTemplate().delete("jiagoushi.DELETE-CATEGORY", id);
		return cnt;
	}

}
