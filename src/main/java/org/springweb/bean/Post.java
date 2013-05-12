package org.springweb.bean;

import java.io.Serializable;
import java.util.Date;

import org.springweb.util.HtmlUtil;

public class Post implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6813910448840753393L;

	private  long id;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private String title;
	
	private long categoryId;
	
	private long parentCategoryId;
	
	private String content;
	
	private String url;
	
	private long view;
	
	private long comment;
	
	private String author;
	
	public String getFilterContent(){
		if(this.content != null && !this.content.isEmpty() && this.content.length() < 500){
			return this.getContent();
		}
		return HtmlUtil.splitAndFilterString(this.getContent(), 500);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getView() {
		return view;
	}

	public void setView(long view) {
		this.view = view;
	}

	public long getComment() {
		return comment;
	}

	public void setComment(long comment) {
		this.comment = comment;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}


}
