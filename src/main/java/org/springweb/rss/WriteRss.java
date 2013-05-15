package org.springweb.rss;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.springweb.bean.Post;
import org.springweb.dao.PostDao;

public class WriteRss {
	public static void general(PostDao postDao, OutputStream out) {
		// Create the rss feed
		String copyright = "Copyright hold by weijian.zhongwj";
		String title = "java�ܹ�ʦ֮·";
		String description = "�ṩjava���ԭ�����£��������м�ֵ���£��ṩ����������ص��������Ƶ���أ�IT��ҵ����²ۣ�����Աר�õ���";
		String language = "zh-CN";
		String link = "http://www.javaarch.net";
		Calendar cal = new GregorianCalendar();
		Date creationDate = cal.getTime();
		SimpleDateFormat date_format = new SimpleDateFormat(
				"EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.CHINA);
		String pubdate = date_format.format(creationDate);
		Feed rssFeeder = new Feed(title, link, description, language,
				copyright, pubdate);

		List<Post> postlist = postDao.query(null, null, null, 200, 0);
		for(Post post : postlist){
			// Now add one example entry
			FeedMessage feed = new FeedMessage();
			feed.setTitle(post.getTitle());
			feed.setDescription(post.getContent());
			feed.setAuthor("zhongweijian");
			feed.setGuid("http://www.javaarch.net/jiagoushi/" + post.getId() +".htm");
			feed.setLink("http://www.javaarch.net/jiagoushi/" + post.getId() +".htm");
			feed.setPubDate(post.getGmtModified().toGMTString());
			rssFeeder.getMessages().add(feed);	
		}

		// Now write the file
		RSSFeedWriter writer = new RSSFeedWriter(rssFeeder, out);
		try {
			writer.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
