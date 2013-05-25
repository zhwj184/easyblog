package org.springweb.controller.front;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.bean.GeekLink;
import org.springweb.dao.GeekLinkDao;

@Controller
@RequestMapping("/")
public class GeekController {
	
	@Autowired
	private GeekLinkDao geekLinkDao;
	
	@RequestMapping(value="/outgeeklink")
	public String addgeek(ModelMap model) throws Exception, IOException {
		return "blog/outgeeklink";	
	}
	@RequestMapping(value="/outaddgeeklink")
	public String outaddgeek(@ModelAttribute GeekLink geekLink, @RequestParam String myauth, ModelMap model) throws Exception, IOException {
		if(myauth != null && myauth.equals("myzhongweijian") && geekLink.getUrl()!=null && !geekLink.getUrl().isEmpty()
				&& geekLink.getTitle()!=null && !geekLink.getTitle().isEmpty()&& geekLink.getAuthor()!=null && !geekLink.getAuthor().isEmpty()){
			model.addAttribute("isSuccess",geekLinkDao.insert(geekLink));
		}
		return "blog/outgeeklink";	
	}
}
