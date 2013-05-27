package org.springweb.pdfview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 
 * 
 * 最终的解决方案，放弃一切其他工具和软件，只用office自带的功能实现word,ppt,excel到pdf的转变
PS.你的office必须是07及以上，并且需要在文件另存为选项中有，“另存为pdf或XPS” 的一项
如果没有，你需要下载office相应插件SaveAsPDFandXPS
 * 
 * 需要jar包
	jacob.jar
	 
	需要dll文件（放在system32下）
	jacob-1.15-M4-x86.dll
 * @author weijian.zhongwj
 *
 */
public class OfficeToPdf {

	private ActiveXComponent wordCom = null;

	public boolean wordToPdf(String filePath, String outFile) {

		ComThread.InitSTA();
		wordCom = new ActiveXComponent("Word.Application");
		try {
			Dispatch wrdDocs = Dispatch.get(wordCom, "Documents").toDispatch();
			Dispatch wordDoc = Dispatch.call(wrdDocs, "Open", filePath,
			new Variant(true), new Variant(false)).toDispatch();
			Dispatch.invoke(wordDoc, "ExportAsFixedFormat", Dispatch.Method,
					new Object[] {
					outFile, new Variant(17), new Variant(false),
							new Variant(0), new Variant(0),
							new Variant(0), new Variant(0), new Variant(false),
							new Variant(true),
							new Variant(0), new Variant(false),
							new Variant(true), new Variant(false) }, new int[0]);
			return true;
		} catch (Exception es) {
			es.printStackTrace();
			return false;
		} finally {
			if (wordCom != null) {
				wordCom.invoke("Quit", new Variant[] {});
				wordCom = null;
				ComThread.Release();
			}
		}
	}

	static final int wdFormatPDF = 17;// word转PDF 格式
	static final int ppSaveAsPDF = 32;// ppt 转PDF 格式

	public void ppt2pdf(String source, String target) {
		ActiveXComponent app = null;
		try {
			app = new ActiveXComponent("Powerpoint.Application");
			Dispatch presentations = app.getProperty("Presentations")
					.toDispatch();
			Dispatch presentation = Dispatch.call(presentations,//
					"Open", source,// FileName
					true,// ReadOnly
					true,// Untitled 指定文件是否有标题。
					false // WithWindow 指定文件是否可见。
					).toDispatch();

			File tofile = new File(target);
			if (tofile.exists()) {
				tofile.delete();
			}
			Dispatch.call(presentation,//
					"SaveAs", //
					target, // FileName
					ppSaveAsPDF);

			Dispatch.call(presentation, "Close");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (app != null)
				app.invoke("Quit");
		}
	}

	public void excel2pdf(String source, String target) {
		ActiveXComponent app = new ActiveXComponent("Excel.Application"); // 启动excel(Excel.Application)
		try {
			app.setProperty("Visible", false);
			Dispatch workbooks = app.getProperty("Workbooks").toDispatch();
			Dispatch workbook = Dispatch.invoke(
					workbooks,
					"Open",
					Dispatch.Method,
					new Object[] { source, new Variant(false),
							new Variant(false) }, new int[3]).toDispatch();
			Dispatch.invoke(workbook, "SaveAs", Dispatch.Method, new Object[] {
					target, new Variant(57), new Variant(false),
					new Variant(57), new Variant(57), new Variant(false),
					new Variant(true), new Variant(57), new Variant(true),
					new Variant(true), new Variant(true) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(workbook, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (app != null) {
				app.invoke("Quit", new Variant[] {});
			}
		}
	}
	
	

	// 调用

	public static void main(String[] args) throws FileNotFoundException, IOException {

		OfficeToPdf o2p = new OfficeToPdf();

		// o2p.saveWordAsPDF("c://x.doc", "c://xx.pdf");

//		o2p.saveWordAsPDF("E://tmp//jetty.docx", "E://tmp//jetty.pdf");

//		o2p.ppt2pdf("E://tmp//invokedynamics-110709104307-phpapp02.ppt",
//				"E://tmp//test.pdf");
		
		String sourceFile = "E://tmp//1_ori";
		String descPath = "e://tmp//1//";
		File[] files = new File(sourceFile).listFiles();
		for(File file: files){
			if(file.getName().endsWith("doc") || file.getName().endsWith("docx")){
				 o2p.wordToPdf(file.getAbsolutePath(), descPath + file.getName().substring(0, file.getName().lastIndexOf(".")));
			}else if(file.getName().endsWith("ppt") || file.getName().endsWith("pptx")){
				o2p.ppt2pdf(file.getAbsolutePath(), descPath + file.getName().substring(0, file.getName().lastIndexOf(".")));
			}else if(file.getName().endsWith("pdf")){
				file.renameTo(new File(descPath + file.getName()));
			}
		}

	}

}