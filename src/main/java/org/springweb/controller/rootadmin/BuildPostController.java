package org.springweb.controller.rootadmin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.bean.Post;
import org.springweb.config.PageConstant;
import org.springweb.dao.PostDao;
import org.springweb.pdfview.PDFContent;

@Controller
@RequestMapping("/rootadmin")
public class BuildPostController {

	@Autowired
	private PostDao postDao;

//	private static final String TempDir = "E://tmp//";
	private static final String TempDir = "/root/tmp/";

	@RequestMapping(value = "/buildswf", method = RequestMethod.GET)
	public String buildswf(@RequestParam long id,
			@RequestParam long categoryId, ModelMap model) {
		if (id < 1 || categoryId <= 0) {
			return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME
					+ "categorylist.htm";
		}
		File[] files = new File(TempDir + id).listFiles();
		for (File file : files) {
			try {
				Post post = new Post();
				post.setCategoryId(categoryId);
				post.setTitle(file.getName());
				post.setAuthor("admin");
				try {
					post.setContent("<embed type=\"application/x-shockwave-flash\" src=\"http://www.javaarch.net/swf/" + id + "/"
							+ file.getName().substring(0,
									file.getName().lastIndexOf(".")) + ".swf"
							+ "\" height=\"600\" width=\"750\"></embed>" + PDFContent.inspect(file.getAbsolutePath()));
					System.out.println(post.getContent());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				postDao.insert(post);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME
				+ "categorylist.htm";
	}
	
	@RequestMapping(value = "/buildsitemap", method = RequestMethod.GET)
	public void buildSiteMap() throws IOException{
		List<Post> postList = postDao.query(null, null, null, 1000, 0);
		String fileName = TempDir + "sitemap.xml";
		FileWriterWithEncoding fw = new FileWriterWithEncoding(new File(fileName), "UTF-8");
		fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		fw.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" 
		        + " xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">\n");
		
		fw.write("<url>\n");
		fw.write("<loc>http://www.javaarch.net/jiagoushi/index.htm</loc>\n");
		fw.write("<lastmod>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " </lastmod>\n");
		fw.write("<changefreq>daily</changefreq>\n");
		fw.write("<priority>1</priority>\n");
		fw.write("</url>\n");
		
		fw.write("<url>\n");
		fw.write("<loc>http://www.javaarch.net/jiagoushi/daohang.htm</loc>\n");
		fw.write("<lastmod>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " </lastmod>\n");
		fw.write("<changefreq>daily</changefreq>\n");
		fw.write("<priority>0.8</priority>\n");
		fw.write("</url>\n");
		
		fw.write("<url>\n");
		fw.write("<loc>http://www.javaarch.net/jiagoushi/geek.htm</loc>\n");
		fw.write("<lastmod>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " </lastmod>\n");
		fw.write("<changefreq>daily</changefreq>\n");
		fw.write("<priority>0.8</priority>\n");
		fw.write("</url>\n");
		
		for(Post post : postList){
			fw.write("<url>\n");
			fw.write("<loc>http://www.javaarch.net/jiagoushi/" + post.getId() +  ".htm</loc>\n");
			fw.write("<lastmod>" + new SimpleDateFormat("yyyy-MM-dd").format(post.getGmtModified()) + " </lastmod>\n");
			fw.write("<changefreq>monthly</changefreq>\n");
			fw.write("<priority>0.8</priority>\n");
			fw.write("</url>\n");
		}
		fw.write("</urlset>\n");
		fw.close();
	}
}
