package org.springweb.bean;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6813910448840753393L;

	private  long id;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private String name;
	
	private long parentId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
