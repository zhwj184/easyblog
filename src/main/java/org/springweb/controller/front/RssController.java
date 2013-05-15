package org.springweb.controller.front;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springweb.dao.PostDao;
import org.springweb.rss.WriteRss;

@Controller
@RequestMapping("/")
public class RssController {

	@Autowired
	private PostDao postDao;

	@RequestMapping(value = "/rss", method = RequestMethod.GET)
	public void rss(HttpServletResponse response, ModelMap model) {
		response.setCharacterEncoding("gbk");
		response.setContentType("application/xml");

		try {
			OutputStream os = response.getOutputStream();
			WriteRss.general(postDao, os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
