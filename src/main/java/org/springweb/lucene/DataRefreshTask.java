package org.springweb.lucene;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.lucene.index.CorruptIndexException;

public class DataRefreshTask {

	@Resource
	private DataIndex dataIndex;
	
	@Resource
	private DocSearch docSearch;
	
	private static final Logger logger = Logger.getLogger(DataRefreshTask.class);
	
	public void init(){
		try {
			dataIndex.index();
		} catch (CorruptIndexException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		docSearch.init();
	}
	
	public void refresh(){
		try {
			dataIndex.index();
		} catch (CorruptIndexException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		docSearch.refresh();
	}
	
	public void destroy(){
		try {
			docSearch.destroy();
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
