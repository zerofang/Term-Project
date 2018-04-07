package atm;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login {

	private JFrame frmAtm;
	private JTextField account;
	private JTextField password;
	private JLabel tips;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmAtm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1",8888);
			initialize(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Socket socket) {
		frmAtm = new JFrame();
		frmAtm.setTitle("ATM-\u767B\u5F55");
		frmAtm.setBounds(100, 100, 450, 300);
		frmAtm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAtm.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u8D26\u53F7");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		label.setBounds(65, 104, 31, 14);
		frmAtm.getContentPane().add(label);
		
		account = new JTextField();
		account.setBounds(106, 102, 204, 20);
		frmAtm.getContentPane().add(account);
		account.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		label_1.setBounds(65, 146, 31, 14);
		frmAtm.getContentPane().add(label_1);
		
		password = new JTextField();
		password.setBounds(106, 144, 204, 20);
		frmAtm.getContentPane().add(password);
		password.setColumns(10);
		
		tips = new JLabel("");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		tips.setForeground(Color.RED);
		tips.setBounds(106, 65, 204, 14);
		frmAtm.getContentPane().add(tips);
		
		JButton login = new JButton("\u767B\u5F55");
		login.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					String userAccount = account.getText();
					String userPSW = password.getText();
					userAccount.trim();
					userPSW.trim();
					if(userAccount.length() <16||userPSW.length()!=6) {
						tips.setText("¿¨ºÅ»òÃÜÂë¸ñÊ½²»ÕýÈ·£¡");
					}
					else {
						String writeTo = "login\n" + userAccount + '\n' + userPSW+'\n';
						String answer;
						try {
							answer = send(socket,writeTo);
							System.out.println(answer);
							if(answer.equals("notFound\n")) {
								tips.setText("ÕË»§²»´æÔÚ£¡");
							}
							else if(answer.equals("pswdError\n")){
								tips.setText("ÃÜÂë´íÎó£¡");
							}
							else {
								tips.setText("³É¹¦£¡");
								new Service(socket);
								//frmAtm.setVisible(false);							
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}
		});
		login.setBounds(106, 197, 204, 23);
		frmAtm.getContentPane().add(login);
	}
	private String send(Socket socket, String writeTo) throws IOException {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(writeTo);
			bufferedWriter.flush();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str + "\n");
			}
			bufferedReader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
