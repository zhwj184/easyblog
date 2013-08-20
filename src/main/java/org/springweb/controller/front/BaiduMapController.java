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
import org.springweb.config.PageConstant;
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
	
	@RequestMapping(value="/welcome/login") // url: /aaa/welcome
	public String lbslogin(@RequestParam(required=false) String username,@RequestParam(required=false) String password,HttpServletRequest request, ModelMap model) throws ParseException {
		
		if(username != null && username.equals("admin") && password != null && password.equals("admin")){
			request.getSession().setAttribute("lbslogin", 1);
			return "redirect:http://www.javaarch.net/jiagoushi/welcome/marker.htm"; 
		}
		return "rootadmin/private/mustlogin";
 
	}
 
	@RequestMapping(value="/welcome/route", method = RequestMethod.GET)//welcome/john
	public String welcomeName(@RequestParam(required=false) String username,HttpServletRequest request, ModelMap model) {
		
		if(((Integer)request.getSession().getAttribute("lbslogin")) == null || ((Integer)request.getSession().getAttribute("lbslogin"))!=1){
			return "rootadmin/private/mustlogin";
		}
		
		if(username == null || username.isEmpty()){
			return "redirect:http://www.javaarch.net/jiagoushi/welcome/marker.htm"; 
		}
 
//		response.setContentType("text/html;charset=UTF-8");
		
		model.addAttribute("mainpointList", lbsInfoDao.queryAllUserName());
		
		List<Map<String,Object>> list = lbsInfoDao.query(username);
		
		model.addAttribute("pointList", list);
		
		if(list == null ||list.isEmpty()){
			model.addAttribute("pointList", lbsInfoDao.queryLast(username));
		}
		
		 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "rootadmin/private/baidu";
 
	}
	
	@RequestMapping(value="/welcome/marker", method = RequestMethod.GET)//welcome/john
	public String marker(@RequestParam(required=false) String username,HttpServletRequest request, ModelMap model) {
		
		if(((Integer)request.getSession().getAttribute("lbslogin")) == null || ((Integer)request.getSession().getAttribute("lbslogin"))!=1){
			return "rootadmin/private/mustlogin";
		}
		
//		response.setContentType("text/html;charset=UTF-8");
 
		model.addAttribute("mainpointList", lbsInfoDao.queryAllUserName());
		
		if(username !=null && !username.isEmpty()){
			model.addAttribute("pointList", lbsInfoDao.queryLast(username));
		}else{
			model.addAttribute("pointList", lbsInfoDao.queryAllUser());
		}
		 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "rootadmin/private/baidumark";
 
	}
}
