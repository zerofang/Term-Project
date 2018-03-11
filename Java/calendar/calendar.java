package calendar;
import javax.swing.JOptionPane;
import java.util.Scanner;
public class calendar {

	public static void main(String[] args) {
		//选择功能：输入年份输出日历请输入1 输入日期输出星期几请输入2
		System.out.println("Options:\nInput year and then output calendar please input 1\nInput date and then output weekday please input 2");
		Scanner sc = new Scanner(System.in);
		try {
			int op = sc.nextInt();
			if(op == 1) {
				//请输入年份（如2017）
				System.out.println("Please input year(eg:2017):");
				int year1 = sc.nextInt();
				sc.close();
				System.out.println(year1+"calendar");
				System.out.println();
				calendar c = new calendar();
				c.printCalendar(year1);
				
			}
			else if(op == 2) {
				//请输入日期（如20170924）
				System.out.println("Please input date(eg:20170924):");
				int date = sc.nextInt();
				sc.close();
				int year2 = date/10000;//获取年份
				int month2 = (date%10000)/100;//获取月份
				int day2 = date - year2*10000-month2*100;//获取日
				calendar c = new calendar();
				c.printWeekday(year2,month2,day2);
			}
			else {
				
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getStackTrace());
		}

	}
	
	//打印日历
	public void printCalendar(int year) {
		int weekday = getWeekday(year);//获取这一年的第一天的前一天是星期几
		if(weekday>6)
			weekday=0;
		int i,j,k;
		for(i = 1; i <= 12; i++) {
			System.out.println(year + "/" + i);//输出当前月份
	        System.out.println("Mon" + "  " + "Tue" + "  " + "Wed" + "  " + "Thu" + "  " + "Fri" + "  " + "Sat" + "  "+"Sun");//输出星期几标注
	        //输出weekday个前导空格，使日期和星期能够对应起来
            for (j = 0; j < weekday; j++) {
                System.out.print("     ");
            }
            
            int day = getDay(year,i);//获取第i个月的天数
            //输出具体日，每7个换行，并更新weekday的值
            for(k = 1; k <= day; k++) {
                if (k < 10) {
                    System.out.print(" " + k + "   ");
                } else {
                    System.out.print(k + "   ");
                }
                weekday++;
                if (weekday > 6) {
                    System.out.println();
                    weekday = 0;
                }            	
            }
            
            System.out.println();
            System.out.println();
		}
	}
	
	//打印输入日期是星期几
	public void printWeekday(int year,int month,int day) {
		int weekday = getWeekday(year);//获取这一年的第一天的前一天是星期几
		int i,sum=0;
		//计算从这年第一天到当前日期经过了多少天
		for(i = 1; i < month; i++) {
			sum+=getDay(year,i);
		}
		sum+=day;
		//计算当前日期是星期几，并输出
		switch((weekday+sum)%7) {
			case 1:
				System.out.println("Monday");
				return;
			case 2:
				System.out.println("Tuesday");
				return;
			case 3:
				System.out.println("Wednesday");
				return;
			case 4:
				System.out.println("Thursday");
				return;
			case 5:
				System.out.println("Friday");
				return;
			case 6:
				System.out.println("Saturday");
				return;
			case 0:
				System.out.println("Sunday");
				return;
			default:
				System.out.println("error");
				return;
		}
	}
	
	//得到输入年份的第一天的前一天是星期几，以2000.1.1为基准
	int getWeekday(int year) {
		if(year > 2000) {
			return (5+366+((year-2001)/4)*(365*3+366)+((year-2001)%4)*365)%7;
		}
		else if(year == 2000) {
			return 5;
		} 
		else {
			return 7-(((2000-year)/4)*(365*3+366)+((2000-year)%4)*365-5)%7;
		}
	}
	
	//得到某年某月的天数
	int getDay(int year,int month) {
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        case 2:
            if (isLeapyear(year)) {
                return 29;
            } else {
                return 28;
            }
        default:
            return 0;
        }		
	}
	
	//判断某年是不是闰年
	boolean isLeapyear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true;
        } else {
            return false;
        }		
	}
}