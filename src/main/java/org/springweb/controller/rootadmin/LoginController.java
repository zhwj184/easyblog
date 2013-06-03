package org.springweb.controller.rootadmin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.config.PageConstant;
import org.springweb.dao.UserDao;

@Controller
@RequestMapping("/rootadmin")
public class LoginController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, @RequestParam(defaultValue="") String username, @RequestParam(defaultValue="") String password, ModelMap model) {
		if(userDao.auth(username, password) > 0){
			request.getSession().setAttribute("login", 1);
			request.getSession().setAttribute("username", username);
			 return "redirect:" + PageConstant.DOMAIN_ADMIN_PATH_NAME + "categorylist.htm";  
		}
		return "rootadmin/mustlogin";
	}
	
	@RequestMapping(value="/mustlogin", method = RequestMethod.GET)
	public String login(){
		return "rootadmin/mustlogin";
	}
}
