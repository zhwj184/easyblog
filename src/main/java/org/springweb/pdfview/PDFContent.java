package org.springweb.pdfview;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

public class PDFContent {

	public static String inspect(String filename)throws IOException {
		PdfReader reader = new PdfReader(filename); // ��ȡpdf��ʹ�õ������
		int num = reader.getNumberOfPages();// ���ҳ��
		StringBuilder content = new StringBuilder(); // ��Ŷ�ȡ�����ĵ�����
		for (int i = 1; i < num && i < 10; i++) {
			content.append("<P>" + PdfTextExtractor.getTextFromPage(reader, i, new SimpleTextExtractionStrategy()) + "</p>"); // ��ȡ��iҳ���ĵ�����
		}
		return content.toString();
	}

	public static void main(String[] args)throws DocumentException, IOException {
		String string = "E://tmp//1//2012-03-01byteman-carvingupyourjavacode-120301110844-phpapp02.pdf";// pdf�ļ�·��
		System.out.println(inspect(string)); // ���ö�ȡ����
	}
}