package org.springweb.pdfview;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Pdf2Swf {
	
	//SWFTools的环境安装路径
	public static String SWFTOOLS_PATH="E:"+File.separator+"sofware"+File.separator+"swftools"+File.separator;
	//播放器样式文件rfxview.swf的路径
	public static String RFXVIEW_SWF_PATH="E:"+File.separator+"sofware"+File.separator+"swftools"+File.separator + "rfxview.swf";
	
	public static int convertPDF2SWF(String sourcePath, String destPath, String fileName) throws IOException{

		File dest = new File(destPath);     
        if (!dest.exists()) {     
            dest.mkdirs();     
        }     
    
        // 源文件不存在则返回     
        File source = new File(sourcePath);     
        if (!source.exists()) {     
            return -1;     
        }     
        
        String[] envp = new String[1];     
        envp[0] = "PATH="+SWFTOOLS_PATH;     
        String command = "cmd /c \""+SWFTOOLS_PATH+"pdf2swf\" -z -s flashversion=9 " + sourcePath + " -o " + destPath + fileName ;     
        Process pro = Runtime.getRuntime().exec(command, envp);     
          
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(pro.getInputStream()));     
        while (bufferedReader.readLine() != null) {     
        }     
        try {     
            pro.waitFor();    
//        	pro.wait(2000);
        } catch (InterruptedException e) {     
            e.printStackTrace();     
        }     
        // 然后在套播放器     
        command = "cmd /c \""+SWFTOOLS_PATH+"swfcombine\" "+RFXVIEW_SWF_PATH+" viewport=" + destPath + fileName + " -o " + destPath +fileName;     
        pro = Runtime.getRuntime().exec(command, envp);     
        bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));     
        while (bufferedReader.readLine() != null) {     
        }     
        try {     
            pro.waitFor(); 
//        	 pro.wait(2000);
        } catch (InterruptedException e) {     
            e.printStackTrace();     
        }     
        return pro.exitValue();
	}
	
	public static void main(String[] args) throws IOException {
		
		
		String sourceFile = "E://tmp//1";
		String descPath = "e://tmp//1swf//";
		File[] files = new File(sourceFile).listFiles();
		for(File file: files){
			Pdf2Swf.convertPDF2SWF(file.getAbsolutePath(), descPath, file.getName().substring(0, file.getName().lastIndexOf(".")) + ".swf");
		}
	}
}