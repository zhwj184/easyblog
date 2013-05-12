package org.springweb.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void craw(String url) throws MalformedURLException,
			IOException {
		Document doc = Jsoup.parse(new URL(url), 2000);
		Elements links = doc
				.select("html body div.nav-wrap div.wrap ul.nav-index li a");
		Iterator<Element> iter = links.iterator();
		while(iter.hasNext()){
			Element elm = (Element) iter.next();
			System.out.println(elm.attr("href"));
		}
	}
	public static void main(String[] args) throws MalformedURLException, IOException {
		craw("http://sports.sina.com.cn/");
	}

}
