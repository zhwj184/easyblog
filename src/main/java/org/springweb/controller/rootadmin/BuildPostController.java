package org.springweb.controller.rootadmin;

import java.io.File;
import java.io.IOException;

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

//	private static final String SWFDIR = "E://tmp//";
	private static final String SWFDIR = "/root/tmp";

	@RequestMapping(value = "/buildswf", method = RequestMethod.GET)
	public String buildswf(@RequestParam long id,
			@RequestParam long categoryId, ModelMap model) {
		if (id < 1 || categoryId <= 0) {
			return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME
					+ "categorylist.htm";
		}
		File[] files = new File(SWFDIR + id).listFiles();
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
							+ "\" height=\"500\" width=\"600\"></embed>" + PDFContent.inspect(file.getAbsolutePath()));
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
}
