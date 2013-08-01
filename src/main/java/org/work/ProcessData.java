package org.work;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;

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
		
		List<String> list = IOUtils.readLines(new FileReader("E:\\log\\data.txt"));
		String[] first = list.get(0).split("\t");
		for(int i = 1; i < list.size(); i++){
			String[] in = list.get(i).split("\t");
			for(int j = 0; j < in.length; j++){
				int a = Integer.parseInt(first[j].substring(first[j].lastIndexOf("=") + 1, first[j].lastIndexOf(")")));
				double b =  Double.parseDouble(in[j].substring(in[j].lastIndexOf("(") + 1, in[j].lastIndexOf(")")));
				long res = Math.round(a * b / 100);
				String val = in[j].substring(in[j].lastIndexOf("(") + 1, in[j].lastIndexOf(")"));
				System.out.print(res + "(" + val + ")");
				if(j != in.length - 1){
					System.out.print("\t");
				}else{
					System.out.println();
				}
			}
		}
	}
}
