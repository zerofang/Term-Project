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
		Connection conn=DriverManager.getConnection(
		        "jdbc:ucanaccess://C:\\Users\\zerofang\\Desktop\\userInfo.accdb");
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM users");
		while (rs.next()) {
		    System.out.println(rs.getString(1));
		}
        try   
        {  
        	ServerSocket ss = new ServerSocket(10000);  
              
            System.out.println("The server is waiting your input...");  
              
            while(true)   
            {  
                Socket socket = ss.accept();  
                  
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
                String line = in.readLine();  
                  
                System.out.println("you input is : " + line);  
                  
                out.close();  
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
