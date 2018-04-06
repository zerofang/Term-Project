package atm;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.net.Socket;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Transfer extends JFrame {

	private JPanel contentPane;
	private JTextField toAccount;
	private JTextField amount;

	/**
	 * Launch the application.for test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Socket socket = new Socket("127.0.0.1",8888);
					Transfer frame = new Transfer(socket);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Transfer(Socket socket) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel tips = new JLabel("");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setForeground(Color.RED);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		tips.setBounds(137, 57, 189, 14);
		contentPane.add(tips);
		
		JLabel lblNewLabel = new JLabel("\u8F6C\u5165\u8D26\u6237\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setBounds(82, 108, 67, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8F6C\u8D26\u91D1\u989D\uFF1A");
		lblNewLabel_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(83, 152, 66, 14);
		contentPane.add(lblNewLabel_1);
		
		toAccount = new JTextField();
		toAccount.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		toAccount.setBounds(157, 105, 151, 20);
		contentPane.add(toAccount);
		toAccount.setColumns(10);
		
		amount = new JTextField();
		amount.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		amount.setBounds(156, 149, 152, 20);
		contentPane.add(amount);
		amount.setColumns(10);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Service(socket);
				dispose();				
			}
		});
		back.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		back.setBounds(31, 209, 101, 23);
		contentPane.add(back);
		
		JButton confirm = new JButton("\u786E\u8BA4");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String account = toAccount.getText();
					String amt = amount.getText();
					account.trim();
					amt.trim();
					if(account.length() <16||amt.length()==0) {
						tips.setText("ÇëÊäÈëÕýÈ·µÄ¿¨ºÅºÍ½ð¶î£¡");
					}
					else if(Integer.parseInt(amt)<0) {
						tips.setText("×ªÕË½ð¶î²»ÄÜÐ¡ÓÚ0£¡");
					}
					else if(Integer.parseInt(amt)==0) {
						tips.setText("³É¹¦×ªÕË0Ôª£¡");
					}
					else {
						String writeTo = "transfer\n" + account + '\n'+amt + '\n';
						String answer = send(socket,writeTo);
						if(answer.equals("0")) {
							tips.setText("");
						}
						else {
							tips.setText("ÕË»§²»´æÔÚ£¡");
						}
					}
				} catch (Exception e1) {
					tips.setText("Á¬½Ó·þÎñÆ÷Ê§°Ü£¡");
				}				
			}
		});
		confirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		confirm.setBounds(296, 210, 101, 23);
		contentPane.add(confirm);
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
