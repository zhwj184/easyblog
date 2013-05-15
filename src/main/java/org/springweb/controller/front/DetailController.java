package org.springweb.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springweb.bean.Comment;
import org.springweb.config.PageConstant;
import org.springweb.dao.CommentDao;
import org.springweb.dao.PostDao;
import org.springweb.util.XssUtil;
import org.springweb.util.XssUtil.XssFilterTypeEnum;

@Controller
@RequestMapping("/")
public class DetailController {
	
	@Autowired
	private CommentDao commentDao;

	@Autowired
	private PostDao postDao;
	
	
	@RequestMapping(value="/addComment", method = RequestMethod.POST)
	public String addComment(@ModelAttribute Comment comment,ModelMap model){
		if(comment.getAuthor() == null || comment.getAuthor().isEmpty() 
				|| comment.getContent() == null || comment.getContent().isEmpty()){
			return "redirect:" + PageConstant.DOMAIN_NAME + comment.getPostId() + ".htm";
		}
		comment.setStatus(1);
		comment.setAuthor(XssUtil.xssFilter(comment.getAuthor(), XssFilterTypeEnum.ESCAPSE.getValue()));
		comment.setContent(XssUtil.xssFilter(comment.getContent(), XssFilterTypeEnum.ESCAPSE.getValue()));
		commentDao.insert(comment);
		postDao.updateComment(comment.getPostId());
		return "redirect:" + comment.getPostId() + ".htm";
	}	
}
