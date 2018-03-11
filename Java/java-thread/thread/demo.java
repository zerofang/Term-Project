package thread;
import java.util.Scanner;
public class demo {
	public static void main(String[] args) {
		System.out.println("please input the total tickets:");
		Scanner in = new Scanner(System.in);
		final titicks N;
		int num=in.nextInt();
		in.close();
		N = new titicks(num);
		Thread s1=new Thread(new windows(1,N));
		Thread s2=new Thread(new windows(2,N));
		Thread s3=new Thread(new windows(3,N));
		s1.start();
		s2.start();
		s3.start();
	}
}
