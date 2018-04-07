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

                String line = read(socket);  
                if(line.equals("login")) {
                	String account = read(socket);
                	ResultSet rs = s.executeQuery("SELECT * FROM users WHERE account = "+account);
                	if(rs.next()){
                    	String pswd = read(socket);
                    	if(pswd.equals(rs.getString(3))) {
                    		send(socket,"success"); 
							System.out.println("8888");
                    		String service = read(socket);
                    		if(service.equals("diposit")) {
                    			int amount = Integer.parseInt(read(socket));
                    			int oamt = rs.getInt(4);
                    			oamt += amount;
                    			s.executeQuery("UPDATE users SET amount = " + oamt + "WHERE account =" + account);
                    			send(socket,"success"); 
                    		}
                    		else if(service.equals("withdraw")) {
                    			int amount = Integer.parseInt(read(socket));
                    			int oamt = rs.getInt(4);
                    			if(oamt>=amount) {
                    				oamt -= amount;
                    				s.executeQuery("UPDATE users SET amount = " + oamt + "WHERE account =" + account);
                    				send(socket,"success");                   				
                    			}
                    			else {
                            		send(socket,"insufficient"); 
                    			}
                    		}
                    		else if(service.equals("transfer")) {
                    			String toAccount = read(socket); 
                    			int amount = Integer.parseInt(read(socket));
                    			ResultSet toAcnt = s.executeQuery("SELECT * FROM users WHERE account = "+toAccount);
                    			if(toAcnt.next()) {
                    				int tamt = toAcnt.getInt(4);
                    				int oamt = rs.getInt(4);
                    				if(oamt>=amount) {
                    					oamt -= amount;
                    					tamt += amount;
                    					s.executeQuery("UPDATE users SET amount = " + oamt + "WHERE account =" + account);
                    					s.executeQuery("UPDATE users SET amount = " + tamt + "WHERE account =" + toAccount);
                    					send(socket,"success");    
                    				}
                    				else {
                    					send(socket,"insufficient");                     					
                    				}
                    			}
                    			else {
                    				send(socket,"notFound");                      				
                    			}
                    		}
                    	}
                    	else {
                    		send(socket,"pswdError");
                    	                		
                    	}
                	}
                	else {
                		send(socket,"notFound");                		
                	}
                }
                //System.out.println("you input is : " + line);  
                socket.close();  
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	public static void main(String[] args) throws Exception{
		Bank b = new Bank();  
	}
	public String read(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line = in.readLine(); 
		in.close();
		return line;
	}
	public void send(Socket socket,String writeTo) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bufferedWriter.write(writeTo);
		bufferedWriter.flush();
		bufferedWriter.close(); 
	}
}
