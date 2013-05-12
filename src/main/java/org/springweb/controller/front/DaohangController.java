package org.springweb.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springweb.bean.Category;
import org.springweb.dao.CategoryDao;

@Controller
@RequestMapping("/")
public class DaohangController {
	
	@Autowired
	private CategoryDao categoryDao;
	
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
	
	private void getLeftCat(ModelMap model) {
		List<Category> rootCatList = categoryDao.query(null, 0L);
		model.addAttribute("rootCatList", rootCatList);
	}
}
