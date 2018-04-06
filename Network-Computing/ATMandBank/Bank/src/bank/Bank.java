package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.net.ServerSocket;  

public class Bank {
	public Bank()  throws Exception {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		Connection conn=DriverManager.getConnection("jdbc:ucanaccess://userInfo.accdb");
		Statement s = conn.createStatement();
/*		ResultSet rs = s.executeQuery("SELECT * FROM users");
		while (rs.next()) {
		    System.out.println(rs.getString(1));
		}*/
        try   
        {  
        	ServerSocket ss = new ServerSocket(8888);  
              
            System.out.println("The server is waiting your input...");  
              
            while(true)   
            {  
                Socket socket = ss.accept();  
                  
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
    			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line = in.readLine();  
                if(line.equals("login")) {
                	String account = in.readLine();
                	ResultSet rs = s.executeQuery("SELECT * FROM users WHERE account = "+account);
                	String pswd = in.readLine();
                	if(pswd.equals(rs.getString(3))) {
                		String service = in.readLine();
                		if(service.equals("diposit")) {
                			int amount = Integer.parseInt(in.readLine());
                			int oamt = rs.getInt(4);
                			oamt += amount;
                			s.executeQuery("UPDATE users SET amount = " + oamt);
                		}
                		else if(service.equals("withdraw")) {
                			int amount = Integer.parseInt(in.readLine());
                			int oamt = rs.getInt(4);
                			if(oamt>=amount) {
                				oamt -= amount;
                				s.executeQuery("UPDATE users SET amount = " + oamt);
                			}
                			else {
                				
                			}
                		}
                		else if(service.equals("transfer")) {
                			String toAccount = in.readLine();
                			String amount = in.readLine();
                		}
                	}
                	else {
                		String writeTo = "pswdError";
                		bufferedWriter.write(writeTo);
            			bufferedWriter.flush();
                	}
                }
                //System.out.println("you input is : " + line);  
                bufferedWriter.close();  
                in.close();  
                socket.close();  
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	public static void main(String[] args) throws Exception{
		Bank b = new Bank();  
	}
}
