package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.ServerSocket;  

public class Bank {

	Connection conn;
	
	public Bank()  throws Exception {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		conn=DriverManager.getConnection("jdbc:ucanaccess://userInfo.accdb");
/*		ResultSet rs = s.executeQuery("SELECT * FROM users");
		while (rs.next()) {
		    System.out.println(rs.getString(1));
		}*/
        try   
        {  
        	@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(8888);  
              
            System.out.println("The server is waiting your input...");  
              
            while(true)   
            {  
                Socket socket = ss.accept(); 
                invoke(socket); 
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
    private void invoke(final Socket socket) throws IOException {  
        new Thread(new Runnable() {  
            public void run() {
            	BufferedReader in = null;
            	BufferedWriter out = null;
                try {  
                	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                	out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String line = read(socket,in);  
                    if(line.equals("login")) {
                    	String account = read(socket,in);
                    	System.out.println(account);
                    	Statement s = conn.createStatement();
                    	ResultSet rs = s.executeQuery("SELECT * FROM users WHERE account = '"+account + "'");
                    	if(rs.next()){
                        	String pswd = read(socket,in);
                        	System.out.println(pswd);
                        	if(pswd.equals(rs.getString(3))) {
                        		send(socket,"success\r\n",out); 
    							System.out.println("8888");
                        		while(true) 
                        		{
                        			String service = read(socket,in);
                        			if(service.equals("0")) {
                        				break;
                        			}
                            		if(service.equals("diposit")) {
                            			String answer = read(socket,in);
                            			if(answer.equals("0")) {
                            				break;
                            			}
                            			int amount = Integer.parseInt(answer);
                            			int oamt = rs.getInt(4);
                            			oamt += amount;
                            			String sql = "UPDATE users SET amount = ? WHERE account = ?";
                            			PreparedStatement pstmt = conn.prepareStatement(sql);
                            			pstmt.setInt(1, oamt);
                            			pstmt.setString(2,account);
                            			pstmt.executeUpdate();
                            			send(socket,"success\r\n"+oamt+"\r\n",out); 
                            		}
                            		else if(service.equals("withdraw")) {
                            			String answer = read(socket,in);
                            			if(answer.equals("0")) {
                            				break;
                            			}
                            			int amount = Integer.parseInt(answer);
                            			int oamt = rs.getInt(4);
                            			if(oamt>=amount) {
                            				oamt -= amount;
                                			String sql = "UPDATE users SET amount = ? WHERE account = ?";
                                			PreparedStatement pstmt = conn.prepareStatement(sql);
                                			pstmt.setInt(1, oamt);
                                			pstmt.setString(2,account);
                                			pstmt.executeUpdate();
                            				send(socket,"success\r\n"+oamt+"\r\n",out);                   				
                            			}
                            			else {
                                    		send(socket,"insufficient\r\n",out); 
                            			}
                            		}
                            		else if(service.equals("transfer")) {
                            			String answer = read(socket,in);
                            			if(answer.equals("0")) {
                            				break;
                            			}
                            			String toAccount = answer; 
                            			int amount = Integer.parseInt(read(socket,in));
                            			ResultSet toAcnt = s.executeQuery("SELECT * FROM users WHERE account = '"+toAccount + "'");
                            			if(toAcnt.next()) {
                            				int tamt = toAcnt.getInt(4);
                            				int oamt = rs.getInt(4);
                            				if(oamt>=amount) {
                            					oamt -= amount;
                            					tamt += amount;
                                    			String osql = "UPDATE users SET amount = ? WHERE account = ?";
                                    			PreparedStatement opstmt = conn.prepareStatement(osql);
                                    			opstmt.setInt(1, oamt);
                                    			opstmt.setString(2,account);
                                    			opstmt.executeUpdate();
                                    			String tsql = "UPDATE users SET amount = ? WHERE account = ?";
                                    			PreparedStatement tpstmt = conn.prepareStatement(tsql);
                                    			tpstmt.setInt(1, tamt);
                                    			tpstmt.setString(2,account);
                                    			tpstmt.executeUpdate();
                            					send(socket,"success\n"+oamt+"\r\n",out);    
                            				}
                            				else {
                            					send(socket,"insufficient\r\n",out);                     					
                            				}
                            			}
                            			else {
                            				send(socket,"notFound\r\n",out);                      				
                            			}
                            		}                    			
                        		}
                        	}
                        	else {
                        		if(!(send(socket,"pswdError\r\n",out))) {
                        			System.out.println("pswderror");
                        		}                		
                        	}
                    	}
                    	else {
                    		if(!(send(socket,"notFound\n",out))) {   
                    			System.out.println("notFound");
                    		}
                    	}
                    }
                } catch(IOException | SQLException ex) {  
                    ex.printStackTrace();  
                } finally {  
                    try {  
                        in.close();  
                    } catch (Exception e) {}  
                    try {  
                    	out.close();  
                    } catch (Exception e) {}  
                    try {  
                        socket.close();  
                    } catch (Exception e) {}  
                }  
            }  
        }).start();  
    }	
	public static void main(String[] args) throws Exception{
		new Bank();  
	}
	
	public String read(Socket socket,BufferedReader in) throws IOException{
		try {
			String line = in.readLine(); 
			return line;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return "0";
		}
	}
	public boolean send(Socket socket,String writeTo,BufferedWriter out) throws IOException{
		try {
			out.write(writeTo);
			out.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}
