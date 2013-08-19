package org.springweb.controller.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springweb.dao.LbsInfoDao;
 
@Controller
@RequestMapping("/")
public class BaiduMapController {
	
	
	@Autowired
	private LbsInfoDao lbsInfoDao;
 
	
	
	private List<Map<String,String>> pointList = new ArrayList<Map<String,String>>();
	private List<Map<String,String>> point2List = new ArrayList<Map<String,String>>();
	
	private static final int MAX_SIZE = 200;
	
	@RequestMapping(value="/api/report") // url: /aaa/welcome
	public String welcome(HttpServletRequest request, ModelMap model) throws ParseException {
		
		Map<String,String> map = new HashMap<String,String>();
		java.util.Iterator<Entry<String,Object>> iter = request.getParameterMap().entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,Object> entry = iter.next();
			map.put(entry.getKey(),request.getParameter(entry.getKey()));	
			System.out.print(entry.getKey() + ":" + request.getParameter(entry.getKey()) + "\t");
		}
		System.out.println();
		lbsInfoDao.insert(map.get("phone"), map.get("lat"), map.get("lng"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("date")),map.get("type"));
		
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "blog/index";
 
	}
 
	@RequestMapping(value="/welcome/route", method = RequestMethod.GET)//welcome/john
	public String welcomeName(@RequestParam String username,HttpServletResponse response, ModelMap model) {
 
//		response.setContentType("text/html;charset=UTF-8");
		
		model.addAttribute("pointList", lbsInfoDao.query(username));
		
		 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "rootadmin/private/baidu";
 
	}
	
	@RequestMapping(value="/welcome/marker", method = RequestMethod.GET)//welcome/john
	public String marker(HttpServletResponse response, ModelMap model) {
		
//		response.setContentType("text/html;charset=UTF-8");
 
		model.addAttribute("pointList", lbsInfoDao.queryAllUser());
		 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "rootadmin/private/baidumark";
 
	}
}
