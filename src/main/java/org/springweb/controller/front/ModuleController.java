package org.springweb.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.bean.Category;
import org.springweb.config.PageConstant;
import org.springweb.dao.CategoryDao;
import org.springweb.dao.GeekLinkDao;

@Controller
@RequestMapping("/")
public class ModuleController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private GeekLinkDao geekLinkDao;
	
	@RequestMapping(value="/daohang", method = RequestMethod.GET)
	public String daohang(ModelMap model){
		getLeftCat(model);
		return "blog/daohang";
	}	
	@RequestMapping(value="/liuyan", method = RequestMethod.GET)
	public String liuyan(ModelMap model){
		getLeftCat(model);
		return "blog/liuyan";
	}	
	@RequestMapping(value="/geek", method = RequestMethod.GET)
	public String geekLink(@RequestParam(defaultValue="1") long index,ModelMap model){
		int size = 20;
		getLeftCat(model);
		long count = geekLinkDao.queryCount();
		model.addAttribute("count", count);
		long totalPage = count % size == 0 ? count / size : count / size + 1;
		if(index <=1  || totalPage < index){
			index = 1;
		}
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("index", index);
		model.addAttribute("geeklinklist",geekLinkDao.query(size, (index-1) * size));
		return "blog/geeklink";
	}
	
	private void getLeftCat(ModelMap model) {
		List<Category> rootCatList = categoryDao.query(null, 0L);
		model.addAttribute("rootCatList", rootCatList);
	}
}
