package org.springweb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class LbsInfoDao extends SqlMapClientDaoSupport{

	public List<Map<String,Object>> query(String username){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", username);
		List<Map<String,Object>> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-LBSINFO-BY-USERNAME", param);
		return list;
	}
	
	public long insert(String username,String lat, String lng, Date lbsTime, String lbsType){
		if(username==null || lat == null || lng==null || lbsTime==null){
			return 0;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", username);
		param.put("lat", lat);
		param.put("lng", lng);
		param.put("lbsTime", lbsTime);
		param.put("lbsType", lbsType);
		return (Long) this.getSqlMapClientTemplate().insert("jiagoushi.LBSINFO-INSERT", param);
	}
	
	public List<Map<String,Object>> queryAllUser(){
		List<Map<String,Object>> list = this.getSqlMapClientTemplate().queryForList("jiagoushi.SELECT-LBSINFO", null);
		return list;
	}
}
