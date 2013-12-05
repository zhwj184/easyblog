package org.work;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessData {

	public static void main(String[] args) throws IOException {
//		List<String> list = IOUtils.readLines(new FileReader("E:\\log\\data.txt"));
//		String[] first = list.get(0).split("\t");
//		for(int i = 1; i < list.size(); i++){
//			String[] in = list.get(i).split("\t");
//			for(int j = 0; j < in.length; j++){
//				int a = Integer.parseInt(first[j].substring(first[j].lastIndexOf("=") + 1, first[j].lastIndexOf(")")));
//				int b =  Integer.parseInt(in[j].substring(0, in[j].lastIndexOf("(")));
//				String res = new DecimalFormat("#0.0").format(b*100.0/a);
//				System.out.print(b + "(" + res + ")");
//				if(j != in.length - 1){
//					System.out.print("\t");
//				}else{
//					System.out.println();
//				}
//			}
//		}
		
//		for(int i = 1; i < list.size(); i++){
//			String[] in = list.get(i).split("\t");
//			System.out.print(in[0] + "\t");
//			for(int j = 1; j < in.length; j++){
//				int a = Integer.parseInt(first[j].substring(0, first[j].lastIndexOf("(")));
//				int b =  Integer.parseInt(in[j].substring(0, in[j].lastIndexOf("(")));
//				String res = new DecimalFormat("#0.0").format(b*100.0/a);
//				System.out.print(b + "(" + res + ")");
//				if(j != in.length - 1){
//					System.out.print("\t");
//				}else{
//					System.out.println();
//				}
//			}
//		}
		
//		for(int i = 1; i < list.size(); i++){
//			String[] in = list.get(i).split("\t");
//			for(int j = 0; j < in.length; j++){
//				int a = Integer.parseInt(first[j].substring(0, first[j].lastIndexOf("(")));
//				int b =  Integer.parseInt(in[j].substring(0, in[j].lastIndexOf("(")));
//				String res = new DecimalFormat("#0.0").format(b*100.0/a);
//				System.out.print(b + "(" + res + ")");
//				if(j != in.length - 1){
//					System.out.print("\t");
//				}else{
//					System.out.println();
//				}
//			}
//		}
		
//		List<String> list = IOUtils.readLines(new FileReader("E:\\log\\data.txt"));
//		String[] first = list.get(0).split("\t");
//		for(int i = 1; i < list.size(); i++){
//			String[] in = list.get(i).split("\t");
//			for(int j = 0; j < in.length; j++){
//				int a = Integer.parseInt(first[j].substring(first[j].lastIndexOf("=") + 1, first[j].lastIndexOf(")")));
//				double b =  Double.parseDouble(in[j].substring(in[j].lastIndexOf("(") + 1, in[j].lastIndexOf(")")));
//				long res = Math.round(a * b / 100);
//				String val = in[j].substring(in[j].lastIndexOf("(") + 1, in[j].lastIndexOf(")"));
//				System.out.print(res + "(" + val + ")");
//				if(j != in.length - 1){
//					System.out.print("\t");
//				}else{
//					System.out.println();
//				}
//			}
//		}
		
		//System.out.println(new Date(1385535806000L));
		//test();
		BigDecimal first=new BigDecimal("-313.19").setScale(2, BigDecimal.ROUND_UP).stripTrailingZeros();
        
        System.out.println(first.toEngineeringString());
        System.out.println(first.toPlainString());
        System.out.println(first.toString());
        
		BigDecimal first1=new BigDecimal("-1226869950").setScale(2, BigDecimal.ROUND_UP).stripTrailingZeros();
        
        System.out.println(first1.toEngineeringString());
        System.out.println(first1.toPlainString());
        System.out.println(first1.toString());
        
		BigDecimal first2=new BigDecimal("-1226869844").setScale(2, BigDecimal.ROUND_UP).stripTrailingZeros();
        
        System.out.println(first2.toEngineeringString());
        System.out.println(first2.toPlainString());
        System.out.println(first2.toString());
	}

	private static void test() {
		// String s may be user controllable
		// \uFE64 is normalized to < and \uFE65 is normalized to > using NFKC
		String s = "\uFE64" + "script" + "\uFE65";
		// Validate
		Pattern pattern = Pattern.compile("[<>]"); // Check for angle brackets
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {  
		  // Found black listed tag
		  throw new IllegalStateException();
		} else {
		  // . . .
		}
		// Normalize
		s = Normalizer.normalize(s, Form.NFKC);
		System.out.println(s);
	}
	
}
