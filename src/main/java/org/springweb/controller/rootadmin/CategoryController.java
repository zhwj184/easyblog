package org.springweb.controller.rootadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.bean.Category;
import org.springweb.config.PageConstant;
import org.springweb.dao.CategoryDao;
 
@Controller
@RequestMapping("/rootadmin")
public class CategoryController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@RequestMapping(value="/categorylist", method = RequestMethod.GET)
	public String categoryList(ModelMap model) {
		model.addAttribute("categorylist",categoryDao.query(null, null));
		return "rootadmin/categorylist";
	}
 
	@RequestMapping(value="/category", method = RequestMethod.GET)
	public String category(ModelMap model) {
		model.addAttribute("category",categoryDao.query(null, null));
		return "rootadmin/addcategory";
	}
 
	@RequestMapping(value="/addCategory", method = RequestMethod.POST)
	public String addCategory(@RequestParam String name, @RequestParam long parentId, ModelMap model) {
 
		Category category = new Category();
		category.setName(name);
		category.setParentId(parentId);
		model.addAttribute("isSuccess",categoryDao.insert(category));
		model.addAttribute("category",categoryDao.query(null,null));
		return "rootadmin/addcategory";
	}
 
	@RequestMapping(value="/updateCategory", method = RequestMethod.GET)
	public String updateCategory(@RequestParam Long id, ModelMap model) {
		model.addAttribute("category",categoryDao.query(id, null).get(0));
		model.addAttribute("allcategory",categoryDao.query(null, null));
		return "rootadmin/updatecategory";
	}
	
	@RequestMapping(value="/updateCategoryAction", method = RequestMethod.POST)
	public String updateCategory(@RequestParam Long id, @RequestParam String name, @RequestParam Long parentId, ModelMap model) {
		model.addAttribute("isSuccess",categoryDao.update(id,name,parentId));
		return "rootadmin/updatecategory";
	}
	
	@RequestMapping(value="/deleteCategory", method = RequestMethod.GET)
	public String delete(@RequestParam Long id, ModelMap model) {
		categoryDao.delete(id);
//		model.addAttribute("category",categoryDao.query(null, null));
//		return "rootadmin/categorylist";
		 return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME + "categorylist.htm";  
	}
	
}
