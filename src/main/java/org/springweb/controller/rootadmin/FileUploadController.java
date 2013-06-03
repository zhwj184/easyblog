package org.springweb.controller.rootadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springweb.config.PageConstant;
import org.springweb.core.MutilPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("/rootadmin")
public class FileUploadController{
	
	@Resource
	private MutilPropertyPlaceholderConfigurer placeholder;

	@RequestMapping(value="/uploadfile",method=RequestMethod.POST)   
    public @ResponseBody String uploadFile(MultipartHttpServletRequest request, @RequestParam long categoryId) throws Exception{          
        List<MultipartFile> files=request.getFiles("mypic");   
        for (MultipartFile file : files) {   
            if (file.isEmpty()) continue;   
            boolean isPic = isPicture(file.getOriginalFilename());
            String path =  null;
            String fileId =  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+ "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            if(isPic){
            	path = placeholder.getProperty("pic_upload_dir") + File.separator + categoryId;
            }else{
            	path = placeholder.getProperty("swf_upload_dir");
            	fileId = file.getOriginalFilename();
            }
            if(!new File(path).exists()){
            	new File(path).mkdir();
            }
            FileOutputStream fileOS=new FileOutputStream(path+File.separator + fileId);   
            fileOS.write(file.getBytes());   
            fileOS.close();   
            if(isPic){
            	return PageConstant.DOMAIN_PIC_NAME + "/" + categoryId + "/" + fileId;	
            }else{
            	return PageConstant.DOMAIN_SWF_NAME + "/" + fileId;
            }
            
        }   
        return "";
    }  
	
	private boolean isPicture(String fileName){
		if(fileName == null || fileName.isEmpty()){
			return false;
		}
		if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".png") 
		   || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".bmp")){
			return true;
		}
		return false;
	}
}
