package org.springweb.controller.rootadmin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springweb.bean.GeekLink;
import org.springweb.dao.GeekLinkDao;

@Controller
@RequestMapping("/rootadmin")
public class GeekLinkController {
	
	@Autowired
	private GeekLinkDao geekLinkDao;
	
	@RequestMapping(value="/addgeek")
	public String addgeek(@ModelAttribute GeekLink geekLink, ModelMap model) throws Exception, IOException {
		model.addAttribute("isSuccess",geekLinkDao.insert(geekLink));
		return "rootadmin/addgeeklink";	
	}
	@RequestMapping(value="/addgeeklink")
	public String addgeeklink(ModelMap model) throws Exception, IOException {
		return "rootadmin/addgeeklink";	
	}
}
