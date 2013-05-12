package org.springweb.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.bean.Category;
import org.springweb.bean.Post;
import org.springweb.config.PageConstant;
import org.springweb.dao.CategoryDao;
import org.springweb.dao.CommentDao;
import org.springweb.dao.PostDao;
import org.springweb.helper.CatTreeSortHelp;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CommentDao commentDao;

	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(@RequestParam(required=false) Long id, @RequestParam(required=false) Long parentId, @RequestParam(defaultValue="1") long index,ModelMap model){
		getLeftCat(id, parentId, model);
		
		long count = postDao.queryCount(parentId,id);
		model.addAttribute("count", count);
		long totalPage = count % PageConstant.PAGE_SIZE == 0 ? count / PageConstant.PAGE_SIZE : count / PageConstant.PAGE_SIZE + 1;
		if(index <=1  || totalPage < index){
			index = 1;
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("index", index);
		model.addAttribute("postlist",postDao.query(parentId, id, 1, PageConstant.PAGE_SIZE, (index-1) * PageConstant.PAGE_SIZE));
		
		return "blog/index";
	}

	private void getLeftCat(Long id, Long parentId, ModelMap model) {
		List<Category> rootCatList = categoryDao.query(null, 0L);
		List<Category> catList = categoryDao.query(null, null);
		model.addAttribute("rootCatList", rootCatList);
		model.addAttribute("catList", catList);
		model.addAttribute("parentId", parentId);
		model.addAttribute("id", id);
		model.put("catTree", CatTreeSortHelp.sort(catList, id, parentId));
		model.put("catMap", CatTreeSortHelp.toMap(catList));
		
		model.put("catCntMap", postDao.queryGroupByCatId());
		
		model.addAttribute("hotPostlist", postDao.query(null, null, 2, 5, 0));
		
		model.addAttribute("hotCommentList", commentDao.query(null, 5, 0));
	}
	
	@RequestMapping(value="/{id}.htm", method = RequestMethod.GET)
	public String detail(@PathVariable  long id, ModelMap model){
		getLeftCat(null, null, model);
		Post post = postDao.queryById(id);
		model.addAttribute("post", post);
		postDao.update(id);
		model.addAttribute("commentList", commentDao.query(id, 100, 0));
		if(post.getUrl() != null && !post.getUrl().isEmpty()){
			return "redirect:" + post.getUrl();
		}
		return "blog/detail";
	}
	
}