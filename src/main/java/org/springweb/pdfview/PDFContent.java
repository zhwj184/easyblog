package org.springweb.pdfview;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

public class PDFContent {

	public static String inspect(String filename)throws IOException {
		PdfReader reader = new PdfReader(filename); // 读取pdf所使用的输出流
		int num = reader.getNumberOfPages();// 获得页数
		StringBuilder content = new StringBuilder(); // 存放读取出的文档内容
		for (int i = 1; i < num && i < 10; i++) {
			content.append("<P>" + PdfTextExtractor.getTextFromPage(reader, i, new SimpleTextExtractionStrategy()) + "</p>"); // 读取第i页的文档内容
		}
		return content.toString();
	}

	public static void main(String[] args)throws DocumentException, IOException {
		String string = "E://tmp//1//2012-03-01byteman-carvingupyourjavacode-120301110844-phpapp02.pdf";// pdf文件路径
		System.out.println(inspect(string)); // 调用读取方法
	}
}