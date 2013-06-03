package org.springweb.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class DocSearch {

	private static IndexSearcher isearcher = null;

	private String indexDir;
	
	private static final Logger logger = Logger.getLogger(DocSearch.class);

	public void init()  {
		Directory directory;
		try {
			if(isearcher != null){
				isearcher.getIndexReader().close();
				isearcher.close();
			}
			directory = FSDirectory.open(new File(indexDir));
			// Now search the index:
			IndexReader ireader = IndexReader.open(directory); // read-only=true
			isearcher = new IndexSearcher(ireader);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public synchronized void refresh() {
		Directory directory;
		try {
//			isearcher.getIndexReader().close();
			isearcher.close();
			directory = FSDirectory.open(new File(indexDir));
			IndexReader ireader = IndexReader.open(directory); // read-only=true
			isearcher = new IndexSearcher(ireader);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public List<Map<String,String>> search(String key, int size) throws IOException, ParseException {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
//		QueryParser parser = new QueryParser(Version.LUCENE_36, "context",
//				analyzer);
//		Query query = parser.parse(key);
//		PhraseQuery query = new PhraseQuery();
//		query.setSlop(2);
//		query.add(new Term("title", key));
//		query.add(new Term("content", key));
		
		BooleanQuery booleanQuery = new BooleanQuery(); 

//		QueryParser parser = new QueryParser(Version.LUCENE_36,"title",analyzer); 
//		Query titleQuery = parser.parse(key);
//		booleanQuery.add(titleQuery,BooleanClause.Occur.SHOULD);

		QueryParser parser1 = new QueryParser(Version.LUCENE_36,"content",analyzer); 
		Query contentQuery = parser1.parse(key);
		booleanQuery.add(contentQuery ,BooleanClause.Occur.MUST); 

		ScoreDoc[] hits = isearcher.search(booleanQuery, null, size).scoreDocs;
		List<Map<String,String>> postlist = new ArrayList<Map<String,String>>();
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
//			System.out.println(hitDoc.getValues("id")[0] + "\t"
//					+ hitDoc.getValues("content")[0] + "\t" + hits[i].score);
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", hitDoc.getValues("id")[0]);
			map.put("title", hitDoc.getValues("title")[0]);
			map.put("url", hitDoc.getValues("url")[0]);
			map.put("content", hitDoc.getValues("content")[0]);
			map.put("author", hitDoc.getValues("author")[0]);
			map.put("gmtCreate", hitDoc.getValues("gmtCreate")[0]);
			map.put("view", hitDoc.getValues("view")[0]);
			map.put("comment", hitDoc.getValues("comment")[0]);
			postlist.add(map);
		}
		return postlist;
	}

	public void destroy() throws IOException {
		isearcher.close();
	}

	public String getIndexDir() {
		return indexDir;
	}

	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}

	public static void main(String[] args) throws IOException, ParseException {

	}

}
