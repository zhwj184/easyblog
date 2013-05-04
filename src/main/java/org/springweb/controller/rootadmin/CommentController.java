package org.springweb.controller.rootadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.config.PageConstant;
import org.springweb.dao.CommentDao;
import org.springweb.dao.PostDao;
 
@Controller
@RequestMapping("/rootadmin")
public class CommentController {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private PostDao postDao;
 
	@RequestMapping(value="/commentlist", method = RequestMethod.GET)
	public String categoryList(@RequestParam long postId,@RequestParam(defaultValue="1") long index,  ModelMap model) {
		long count = commentDao.queryCount(postId);
		model.addAttribute("totalPage", count % PageConstant.PAGE_SIZE == 0 ? count / PageConstant.PAGE_SIZE : count / PageConstant.PAGE_SIZE + 1);
		model.addAttribute("index", index);
		model.addAttribute("commentList",commentDao.query(postId, PageConstant.PAGE_SIZE, (index-1) * PageConstant.PAGE_SIZE));
		return "rootadmin/commentlist";
	}
	
	@RequestMapping(value="/updateComment", method = RequestMethod.GET)
	public String update(@RequestParam Long id,@RequestParam Long postId,ModelMap model) {
		commentDao.update(id, 1);
		return "redirect:commentlist.htm?postId=" + postId;  
	}
	
	@RequestMapping(value="/deleteComment", method = RequestMethod.GET)
	public String delete(@RequestParam Long id,@RequestParam Long postId,ModelMap model) {
		commentDao.delete(id);
		return "redirect:commentlist.htm?postId=" + postId;  
	}
 
}
