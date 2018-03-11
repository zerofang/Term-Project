package txt;

import java.util.*;

public class Test {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Txt txt = new Txt(new LinkedList<Object>());
		Scanner scanner0 = new Scanner(System.in);
		for(;;)
		{
			System.out.print("1.增加记录\n2.获得记录数量\n3.展示记录\n4.删除记录\n5.退出\n");
			int i = scanner0.nextInt();
			if(i == 1){
				System.out.println("输入字符串：");
				Scanner scanner1 = new Scanner(System.in);
				String newLine = scanner1.nextLine();
				txt.addALine(newLine);
			}else if(i == 2){
				System.out.println("总数为:" + txt.getAmount());
			}else if(i == 3){
				System.out.println("1.展示所有记录\n2.展示某一条记录");
				Scanner scanner3 = new Scanner(System.in);
				int Mode = scanner3.nextInt();
				if(Mode!=1&&Mode!=2){
					System.out.println("error!");
					continue;
				}
				if(Mode == 1){
					txt.printAll();
				}
				if(Mode == 2){
					System.out.println("输入记录序号(1~" + txt.getAmount()+")");
					int No = scanner3.nextInt();
					if(No < 1|| No > txt.getAmount()){
						System.out.println("error!");
						continue;
					}
					txt.printALine(No);
				}
			}else if(i == 4){
				System.out.println("1.删除所有记录\n2.删除某一条记录");
				Scanner scanner4 = new Scanner(System.in);
				int Mode = scanner4.nextInt();
				if(Mode != 1 && Mode != 2){
					System.out.println("error!");
					continue;
				}
				if(Mode == 1){
					if(txt.deleteAll()) System.out.println("Successed!");
					else System.out.println("Failed to delete.");
				}
				if(Mode == 2){
					System.out.println("输入记录序号(1~"+txt.getAmount()+")");
					int No = scanner4.nextInt();
					if(No < 1|| No > txt.getAmount()){
						System.out.println("error!");
						continue;
					}
					int curAmount = txt.getAmount();
					txt.deleteALine(No);
					if(txt.getAmount() == curAmount - 1) System.out.println("Successed!");
					else System.out.println("Failed to delete.");
				}
			}
			else break;
		}
		scanner0.close();
	}
}
