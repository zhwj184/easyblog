package org.springweb.controller.rootadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springweb.dao.CategoryDao;
import org.springweb.dao.PostDao;
 
@Controller
@RequestMapping("/rootadmin")
public class MarkdownController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
 
	@RequestMapping(value="/testmarkdown", method = RequestMethod.GET)
	public String testmardown( ModelMap model) {
		
		return "rootadmin/testmarkdown";
	}
	
 
}
