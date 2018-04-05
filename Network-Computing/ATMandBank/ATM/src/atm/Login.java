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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
				try {
					Socket socket = new Socket("127.0.0.1",8888);
					String userAccount = account.getText();
					String userPSW = password.getText();
					userAccount.trim();
					userPSW.trim();
					if(userAccount.length() <16||userPSW.length()!=6) {
						tips.setText("¿¨ºÅ»òÃÜÂë¸ñÊ½²»ÕýÈ·£¡");
					}
					else {
						String writeTo = userAccount+' '+userPSW;
						String answer = send(socket,writeTo);
						if(answer.equals("0")) {
							tips.setText("");
							new Service(socket);
							frmAtm.dispose();
						}
						else if(answer.equals("1")){
							tips.setText("ÃÜÂë´íÎó£¡");
						}
						else {
							tips.setText("ÕË»§²»´æÔÚ£¡");
						}
					}
				} catch (Exception e) {
					tips.setText("Á¬½Ó·þÎñÆ÷Ê§°Ü£¡");
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
