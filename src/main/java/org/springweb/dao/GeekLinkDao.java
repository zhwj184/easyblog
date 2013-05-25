package org.springweb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springweb.bean.GeekLink;


public class GeekLinkDao extends SqlMapClientDaoSupport{

	
	public GeekLink queryById(long id){
		return (GeekLink) this.getSqlMapClientTemplate().queryForObject("jiagoushi.SELECT-GEEKLINK-BYID", id);
	}
	
	public List<GeekLink> query(int pagesize, long index){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pagesize", pagesize);
		param.put("offset", index);
		List<GeekLink> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-GEEKLINK", param);
		return list;
	}
	
	public long queryCount(){
		long cnt = (Long) this.getSqlMapClientTemplate().queryForObject("jiagoushi.SELECT-GEEKLINK-COUNT");
		return cnt;
	}
	
	
	public long insert(GeekLink GeekLink){
		if(GeekLink == null){
			return 0L;
		}
		return (Long) this.getSqlMapClientTemplate().insert("jiagoushi.GEEKLINK-INSERT", GeekLink);
	}

	
	public int delete(long id){
		int cnt = this.getSqlMapClientTemplate().delete("jiagoushi.DELETE-GEEKLINK", id);
		return cnt;
	}

}
