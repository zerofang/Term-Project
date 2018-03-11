package test;

import java.util.Scanner;
import drawshapes.*;

public class Test{
	
	//初始化用于存放各种形状的引用的数组
	private static Shape[] shapes = new Shape[20];
	
	public static void main(String[] args) {
		
		int no = 0;				//用于记录shapes的数目，初始为0
		Scanner sc = new Scanner(System.in);			//初始化scanner对象接收用户输入
		Test test = new Test();				//初始化本类对象，用于调用各方法
		
		//永真循环，直到用户输入“quit”时跳出循环
		while(true) {
			
			//用户提示文字，输入“create”图形创建，“search”图形检索，“draw”图形绘制，“quit”退出
			System.out.println("please choose operations:(ops:\"creat\" \"search\" \"draw\" \"quit\")");
			String command = sc.next();				//接收用户输入的命令
			//图形创建
			if(command.equals("creat")) {
				if(no<20) {							//检查用户输入是否合法（最多仅可创建20个图形）
					System.out.println("please choose shapes:(ops:\"rectangle\" \"circle\" \"triangle\")");		//提示选择要创建的形状类型
					String shape = sc.next();
					System.out.println("please input name");		//提示输入形状的名字
					String name = sc.next();
					if(test.creatShape(no,shape,name))		//调用创建图形的方法，若创建成功则令计数器加1
						no++;				
				}
				else {
					System.out.println("the maximum count of shapes is 20!");
				}
			}
			//图形检索
			else if(command.equals("search")) {
				System.out.println("please input name");		//提示输入要检索的形状的名字
				String sname = sc.next();
				searchShape(sname,no);		//调用检索图形的方法
			}
			//图形绘制
			else if(command.equals("draw")) {
				System.out.println("please input number(range from 0 to " + (no-1) + ",both ends are included)");		//提示输入序号并给出可选序号范围
				int sno = sc.nextInt();
				if(sno<no) {			//检查用户输入是否合法
					drawShape(sno);			//调用绘制图形方法	
				}
				else {
					System.out.println("please input number range from 0 to " + (no-1) + "!");
				}
			}
			//退出
			else if(command.equals("quit")) {
				sc.close();			//关闭输入
				break;				//退出循环
			}
			//若用户输入非法则驳回
			else {			
				System.out.println("please choose operations from \"creat\" \"search\" \"draw\" \"quit\"!");
			}
		}
	}
	//创建图形方法
	public boolean creatShape(int no,String shape,String name) {
		
		//根据用户输入选择创建不同的形状对象并赋值给shapes的成员
		if(shape.equals("rectangle")) {
			shapes[no] = new Rectangle(name);
			System.out.println("creat successful!");	//提示创建成功
		}
		
		else if(shape.equals("circle")) {
			shapes[no] = new Circle(name);
			System.out.println("creat successful!");
		}
		
		else if(shape.equals("triangle")) {
			shapes[no] = new Triangle(name);
			System.out.println("creat successful!");
		}
		//若用户输入非法则驳回并返回创建失败的标志
		else {
			System.out.println("please choose shapes from \"rectangle\" \"circle\" \"triangle\"!");
			return false;
		}
		return true;
	}
	//检索图形方法
	public static void searchShape(String sname,int no) {
		
		//查找并打印与sname相匹配的第一个图形的类型和序号
		for(int i = 0; i < no; i++) {
			if(sname.equals(shapes[i].getName())) {
				System.out.println("type:"+shapes[i].getType()+" number:"+i);
				return;
			}
		}
		//若未找到则提示not found
		System.out.println("not found!");
		return;
	}
	//绘制图形方法
	public static void drawShape(int sno) {
		//按序号顺序循环绘制son及之前的所有图形
		for(int i = 0; i <= sno; i++) {
			shapes[i].drawShape();
		}
	}
}
