package org.springweb.controller.rootadmin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springweb.bean.Post;
import org.springweb.config.PageConstant;
import org.springweb.dao.CategoryDao;
import org.springweb.dao.PostDao;
 
@Controller
@RequestMapping("/rootadmin")
public class PostController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
 
	@RequestMapping(value="/postlist", method = RequestMethod.GET)
	public String categoryList(@RequestParam Long categoryId, @RequestParam(defaultValue="1") long index, ModelMap model) {
		long count = postDao.queryCount(null,categoryId);
		model.addAttribute("totalPage", count % PageConstant.PAGE_SIZE == 0 ? count / PageConstant.PAGE_SIZE : count / PageConstant.PAGE_SIZE + 1);
		model.addAttribute("index", index);
		model.addAttribute("category", categoryDao.query(categoryId, null).get(0));
		model.addAttribute("postlist",postDao.query(null, categoryId, null, PageConstant.PAGE_SIZE, (index-1) * PageConstant.PAGE_SIZE));
		return "rootadmin/postlist";
	}
	
	@RequestMapping(value="/post", method = RequestMethod.GET)
	public String post(@RequestParam long categoryId,  ModelMap model) {
		model.addAttribute("category", categoryDao.query(categoryId, null).get(0));
		return "rootadmin/addpost";
	}
	
	@RequestMapping(value="/postdetail", method = RequestMethod.GET)
	public String postdetail(@RequestParam long id,  ModelMap model) {
		model.addAttribute("post", postDao.queryById(id));
		return "rootadmin/postdetail";
	}
	
	@RequestMapping(value="/addpost", method = RequestMethod.POST)
	public String addPost(@ModelAttribute Post post, @RequestParam("file") MultipartFile file, ModelMap model) throws Exception, IOException {
		if(!file.isEmpty()){
			post.setContent(new String(file.getBytes(),"GBK"));
		}
		model.addAttribute("isSuccess",postDao.insert(post));
		model.addAttribute("categoryId", post.getCategoryId());
		return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME + "/postlist.htm?id="+ post.getCategoryId();
	}
	
	@RequestMapping(value="/deletePost", method = RequestMethod.GET)
	public String delete(@RequestParam Long id, @RequestParam Long categoryId, ModelMap model) {
		postDao.delete(id);
		return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME + "postlist.htm?categoryId=" + categoryId;  
	}
	
	@RequestMapping(value="/updatePost", method = RequestMethod.GET)
	public String updatePost(@RequestParam Long id, ModelMap model) {
		model.addAttribute("post", postDao.queryById(id));
		return "rootadmin/updatePost";
	}
	
	@RequestMapping(value="/updatePostAction", method = RequestMethod.POST)
	public String updatePostAction(@ModelAttribute Post post, ModelMap model) {
		model.addAttribute("isSuccess",postDao.update(post));
		return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME + "postlist.htm?categoryId="+postDao.queryById(post.getId()).getCategoryId();
	}
 
}
