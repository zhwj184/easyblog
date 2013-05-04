package org.springweb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springweb.bean.Comment;


public class CommentDao extends SqlMapClientDaoSupport{

	public List<Comment> query(long postId, int pagesize, long index){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("postId", postId);
		param.put("pagesize", pagesize);
		param.put("index", index);
		param.put("status", 1);
		List<Comment> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-COMMENT", param);
		return list;
	}
	
	public long queryCount(long postId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("postId", postId);
		long cnt = (Long) this.getSqlMapClientTemplate().queryForObject("jiagoushi.SELECT-COMMENT-COUNT", param);
		return cnt;
	}
	
	public long insert(Comment comment){
		if(comment == null){
			return 0L;
		}
		return (Long) this.getSqlMapClientTemplate().insert("jiagoushi.COMMENT-INSERT", comment);
	}
	
	public int update(long id, int status){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", status);
		return this.getSqlMapClientTemplate().update("jiagoushi.UPDATE-Comment", param);
	}
	
	public int delete(long id){
		int cnt = this.getSqlMapClientTemplate().delete("jiagoushi.DELETE-COMMENT", id);
		return cnt;
	}

}
