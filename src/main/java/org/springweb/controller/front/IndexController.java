package org.springweb.controller.front;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
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
import org.springweb.lucene.DataIndex;
import org.springweb.lucene.DocSearch;
import org.springweb.util.XssUtil;
import org.springweb.util.XssUtil.XssFilterTypeEnum;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CommentDao commentDao;
	
	private static final Logger logger = Logger.getLogger(IndexController.class);

	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(@RequestParam(required=false) Long id, @RequestParam(required=false) Long parentId, @RequestParam(defaultValue="1") Integer type, @RequestParam(defaultValue="1") long index,ModelMap model){
		getLeftCat(id, parentId, model);
		
		long count = postDao.queryCount(parentId,id);
		model.addAttribute("count", count);
		long totalPage = count % PageConstant.PAGE_SIZE == 0 ? count / PageConstant.PAGE_SIZE : count / PageConstant.PAGE_SIZE + 1;
		if(index <=1  || totalPage < index){
			index = 1;
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("index", index);
		model.addAttribute("type", type);
		model.addAttribute("postlist",postDao.query(parentId, id, type, PageConstant.PAGE_SIZE, (index-1) * PageConstant.PAGE_SIZE));
		
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
//		if(post.getUrl() != null && !post.getUrl().isEmpty()){
//			return "redirect:" + post.getUrl();
//		}
		
		//Ïà¹ØÍÆ¼ö
		if(post != null && post.getTitle() != null){
			List<Map<String, String>> res = null;
			try {
				res = docSearch.search(post.getTitle(), 7);
			} catch (IOException e) {
				logger.error(e);
			} catch (ParseException e) {
				logger.error(e);
			}
			model.addAttribute("recomPostlist", res);	
		}
		
		return "blog/detail";
	}
	
	@Autowired
	private DocSearch docSearch;
	
	@Autowired
	private DataIndex dataIndex;
	
	@RequestMapping(value="/search")
	public String search(@RequestParam String k,ModelMap model){
		if(k == null || k.isEmpty()){
			return "redirect:" + PageConstant.DOMAIN_NAME + "index.htm";
		}
		k = XssUtil.xssFilter(k, XssFilterTypeEnum.DELETE.getValue());
		getLeftCat(null, null, model);
		try {
//			dataIndex.index();
			List<Map<String,String>> res = docSearch.search(k, 50);
			model.addAttribute("postlist", res);
		} catch (IOException e) {
			logger.error(e);
		} catch (ParseException e) {
			logger.error(e);
		}
		model.addAttribute("k", k);
		return "blog/search";
	}
	
}
