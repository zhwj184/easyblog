package org.springweb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springweb.bean.Category;


public class UserDao extends SqlMapClientDaoSupport{

	public long auth(String username, String password){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", username);
		param.put("password", password);
		long cnt = (Long) this.getSqlMapClientTemplate().queryForObject("jiagoushi.SELECT-USER", param);
		return cnt;
	}

}
