package javaio;

import java.io.*;
import java.util.Scanner;

public class Interaction {
	public static void main(String[] args) throws IOException {
		String fileDir;
		BufferedReader brSys=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入文件地址（以“\\”为分隔符）：");
		while((fileDir=brSys.readLine())==null){
			System.out.println("请输入文件地址（以“\\”为分隔符）：");
		}
		System.out.println("请选择输出至文件/屏幕(1/2)：");
		int mode = 0;
		Scanner Scan=new Scanner(System.in);
		while(mode != 1 && mode !=2)
		{
			mode = Scan.nextInt();
		}
		
		UseIO user = new UseIO();
		BufferedReader fileBr = user.readIn(fileDir);
		user.numCal(fileBr, mode);
	}
}
