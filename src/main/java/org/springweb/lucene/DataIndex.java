package org.springweb.lucene;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springweb.bean.Post;
import org.springweb.dao.PostDao;
import org.springweb.util.DateUtil;

public class DataIndex {
	
	private String indexDir;
	
	@Autowired
	private PostDao postDao;
	
	private static final Logger logger = Logger.getLogger(DataIndex.class);

	public  void index() throws CorruptIndexException, IOException {
		IndexWriter indexwrite = null;
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		try {
			Directory directory = FSDirectory.open(new File(indexDir));
			indexwrite = new IndexWriter(directory, analyzer, true,
					new IndexWriter.MaxFieldLength(25000));
//			indexwrite.deleteAll();
		} catch (Exception e) {
			logger.error("DataIndex index got error " + e.getMessage(), e);
		}

		Long count = postDao.queryCount(null,null);
		List<Post> postList = postDao.query(null, null, 1, count.intValue(), 0L);
		
		
		for (Post post : postList) {
			Document doc = new Document();
			doc.add(new Field("title", post.getTitle(), Field.Store.YES,
					Field.Index.ANALYZED));
			doc.add(new Field("content", post.getContent() == null ? "" :post.getContent(), Field.Store.YES,
					Field.Index.ANALYZED));
			doc.add(new Field("url", post.getUrl() == null ? "" : post.getUrl(), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("author", post.getAuthor(), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("gmtCreate", DateUtil.format(post.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("id", String.valueOf(post.getId()), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("view", String.valueOf(post.getView()), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("comment", String.valueOf(post.getComment()), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			indexwrite.addDocument(doc);
		}
		indexwrite.commit();
		indexwrite.close();
	}

	public String getIndexDir() {
		return indexDir;
	}

	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}

}