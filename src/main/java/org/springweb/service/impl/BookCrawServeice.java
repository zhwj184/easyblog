package org.springweb.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookCrawServeice {

	/**
	 * 抓取http://www.icili.com/emule/book/computer/1 网站电子书
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static Map<String, String> craw(String url)
			throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL(url), 2000);
		Elements links = doc
				.select("html body div#content div#main ul li div.list_info h4 a[href]");
		System.out.println(links.text());
		Iterator<Element> iter = links.iterator();
		Map<String, String> map = new HashMap<String, String>();
		while (iter.hasNext()) {
			Element elm = (Element) iter.next();
			// System.out.println("http://www.icili.com" + elm.attr("href"));
			// System.out.println(elm.text());
			String nextUrl = "http://www.icili.com" + elm.attr("href");
			Document nextdoc = Jsoup.parse(new URL(nextUrl), 2000);
			Elements nextlinks = nextdoc
					.select("html body div#content div#main table#emuleFile tr td a");
			Iterator<Element> nextiter = nextlinks.iterator();
			StringBuilder alink=new StringBuilder();
			while (nextiter.hasNext()) {
				Element nextelm = (Element) nextiter.next();
				alink.append("<a href=\"" + nextelm.attr("href") + "\">"
						+ nextelm.text() + "</a></br>");
			}
			if(!alink.toString().isEmpty()){
				map.put(nextUrl, alink.toString());	
			}
		}
		return map;
	}

	public static List<String> crawOne(String url) throws MalformedURLException,
			IOException {
		Document doc = Jsoup.parse(new URL(url), 2000);
		Elements titlelinks = doc
				.select("html body div#content div#main h1.elite");
		List<String>res = new ArrayList<String>();
		res.add(titlelinks.text());
		
		Elements links = doc
				.select("html body div#content div#main table#emuleFile tr td a");
		Iterator<Element> iter = links.iterator();
		StringBuffer alink = new StringBuffer();
		while (iter.hasNext()) {
			Element elm = (Element) iter.next();
			alink.append("<a href=\"" + elm.attr("href") + "\">" + elm.text()
					+ "</a></br>");
		}
		res.add(alink.toString());
		return res;
	}

	public static void main(String[] args) throws MalformedURLException,
			IOException {
//		System.out.println(craw("http://www.icili.com/emule/book/computer/1"));
		System.out.println(craw("http://www.icili.com/emule/edu/computer/1"));
	}

}
