package org.springweb.controller.rootadmin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.bean.Post;
import org.springweb.config.PageConstant;
import org.springweb.dao.CategoryDao;
import org.springweb.dao.PostDao;
import org.springweb.service.impl.BookCrawServeice;

@Controller
@RequestMapping("/rootadmin")
public class BookCrawController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;

	@RequestMapping(value="/booklist", method = RequestMethod.GET)
	public String booklist(@RequestParam long id,@RequestParam long categoryId, ModelMap model) {
		try {
			Map<String,String> map = BookCrawServeice.craw("http://www.icili.com/emule/book/computer/" + id);
			model.addAttribute("booklist",map);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("id", (id+1));
		model.addAttribute("category", categoryDao.query(categoryId, null).get(0));
		return "rootadmin/booklist";
	}
	@RequestMapping(value="/shipinlist", method = RequestMethod.GET)
	public String shipinlist(@RequestParam long id,@RequestParam long categoryId, ModelMap model) {
		try {
			Map<String,String> map = BookCrawServeice.craw("http://www.icili.com/emule/edu/computer/" + id);
			model.addAttribute("booklist",map);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("id", (id+1));
		model.addAttribute("category", categoryDao.query(categoryId, null).get(0));
		return "rootadmin/booklist";
	}

	@RequestMapping(value="/addbooklist", method = RequestMethod.POST)
	public String addbooklist(@RequestParam long categoryId,@RequestParam long id, @RequestParam long  parentCategoryId,@RequestParam String[] booklist, ModelMap model) throws MalformedURLException, IOException {
		 if(booklist != null && booklist.length > 0){
			 for(String book : booklist){
				 Post post = new Post();
				 post.setCategoryId(categoryId);
				 List<String> res = BookCrawServeice.crawOne(book);
				 post.setTitle(res.get(0));
				 post.setContent(res.get(1));
				 post.setAuthor("admin");
				 post.setParentCategoryId(parentCategoryId);
				 postDao.insert(post);
			 }
		 }
		 return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME + "booklist.htm?id=" + id + "&categoryId="+ categoryId;  
	}
}
