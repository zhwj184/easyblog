package org.springweb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springweb.bean.Post;


public class PostDao extends SqlMapClientDaoSupport{

	
	public Post queryById(long id){
		return (Post) this.getSqlMapClientTemplate().queryForObject("jiagoushi.SELECT-POST-BYID", id);
	}
	
	public List<Post> query(Long parentCategoryId, Long categoryId, Integer type, int pagesize, long index){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("parentCategoryId", parentCategoryId);
		param.put("categoryId", categoryId);
		param.put("type", type);
		param.put("pagesize", pagesize);
		param.put("offset", index);
		List<Post> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-POST", param);
		return list;
	}
	
	public long queryCount(Long parentCategoryId,Long categoryId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("categoryId", categoryId);
		param.put("parentCategoryId", parentCategoryId);
		long cnt = (Long) this.getSqlMapClientTemplate().queryForObject("jiagoushi.SELECT-POST-COUNT", param);
		return cnt;
	}
	
	public Map<Long,Long> queryGroupByCatId(){
		List<Map<String,Object>> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-POST-GROUP");
		Map<Long,Long> resMap = new HashMap<Long,Long>();
		if(list == null || list.isEmpty()){
			return resMap;
		}
		for(Map<String,Object> map : list){
			resMap.put(((Integer)map.get("category_id")).longValue(),(Long)map.get("cnt"));
		}
		return resMap;
	}
	
	public long insert(Post post){
		if(post == null){
			return 0L;
		}
		return (Long) this.getSqlMapClientTemplate().insert("jiagoushi.POST-INSERT", post);
	}
	
	public int update(long id){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		int cnt = this.getSqlMapClientTemplate().update("jiagoushi.UPDATE-POST", param);
		return cnt;
	}
	public int updateComment(long id){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		int cnt = this.getSqlMapClientTemplate().update("jiagoushi.UPDATE-ADD-COMMENT", param);
		return cnt;
	}
	
	public int delete(long id){
		int cnt = this.getSqlMapClientTemplate().delete("jiagoushi.DELETE-POST", id);
		return cnt;
	}

	public int update(Post post) {
		int cnt = this.getSqlMapClientTemplate().update("jiagoushi.UPDATE-POST-INFO", post);
		return cnt;
	}

}
