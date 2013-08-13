package org.springweb.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/")
public class BaiduMapController {
	
//	@Autowired
//	private HelloService helloService;
//	
//	@Autowired
//	private MessageSource messageSource;
 
	private List<Map<String,String>> pointList = new ArrayList<Map<String,String>>();
	private List<Map<String,String>> point2List = new ArrayList<Map<String,String>>();
	
	private static final int MAX_SIZE = 200;
	
	@RequestMapping(value="/api/report") // url: /aaa/welcome
	public String welcome(HttpServletRequest request, ModelMap model) {
 
		
		System.out.println(request.getParameterMap());
//		System.out.println(request.getParameter("content"));
//		try {
//			System.out.println(URLDecoder.decode(request.getParameter("content"),"GBK"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(content);
//		helloService.sayHello();
		
//		Map<String,Object> point = new HashMap<String,Object>();
//		point.put("lat", Double.parseDouble(request.getParameter("lat")));
//		point.put("lng",  Double.parseDouble(request.getParameter("lng")));
//		point.put("phone", request.getParameter("phone"));
//		point.put("date", request.getParameter("date"));
//		point.put("address", request.getParameter("address"));
//		pointList.add(point);
//		Map<String,Object> point2 = new HashMap<String,Object>();
//		point2.put("lat", Double.parseDouble(request.getParameter("lat")));
//		point2.put("lng",  Double.parseDouble(request.getParameter("lng")));
//		point2.put("phone", request.getParameter("phone"));
//		point2.put("date", request.getParameter("date"));
//		point2.put("address", request.getParameter("address"));
//		point2List.add(point2);
		
		Map<String,String> map = new HashMap<String,String>();
		java.util.Iterator<Entry<String,Object>> iter = request.getParameterMap().entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,Object> entry = iter.next();
			map.put(entry.getKey(),request.getParameter(entry.getKey()));	
		}
		pointList.add(map);
		point2List.add(map);
		
		model.addAttribute("pointList", pointList);
		
		if(pointList.size() > MAX_SIZE){
			pointList.remove(0);
		}
		if(point2List.size() > MAX_SIZE){
			point2List.remove(0);
		}
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "blog/index";
 
	}
 
	@RequestMapping(value="/welcome/route", method = RequestMethod.GET)//welcome/john
	public String welcomeName(HttpServletResponse response, ModelMap model) {
 
//		response.setContentType("text/html;charset=UTF-8");
		
		model.addAttribute("pointList", pointList);
		
		 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "rootadmin/private/baidu";
 
	}
	
	@RequestMapping(value="/welcome/marker", method = RequestMethod.GET)//welcome/john
	public String marker(HttpServletResponse response, ModelMap model) {
		
//		response.setContentType("text/html;charset=UTF-8");
 
		model.addAttribute("pointList", point2List);
		 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "rootadmin/private/baidumark";
 
	}
}
