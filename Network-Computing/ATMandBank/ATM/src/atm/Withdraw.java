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
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.event.ActionEvent;

import java.net.Socket;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Withdraw extends JFrame {

	private JPanel contentPane;
	private JTextField amount;

	/**
	 * Launch the application.for test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Socket socket = new Socket("127.0.0.1",8888);
					Withdraw frame = new Withdraw(socket);
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
	public Withdraw(Socket socket) {
		setTitle("ATM-\u53D6\u6B3E");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel tips = new JLabel("New label");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setForeground(Color.RED);
		tips.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		tips.setBounds(127, 58, 186, 14);
		contentPane.add(tips);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u53D6\u6B3E\u91D1\u989D\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel.setBounds(62, 113, 104, 14);
		contentPane.add(lblNewLabel);
		
		amount = new JTextField();
		amount.setBounds(176, 111, 104, 20);
		contentPane.add(amount);
		amount.setColumns(10);
		
		JButton confirm = new JButton("\u786E\u8BA4");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amts = amount.getText();
				amts.trim();
				if(amts.length()==0) {
					tips.setText("请输入取款金额！");
				}
				int amt = Integer.parseInt(amts);
				if(amt<0) {
					tips.setText("取款金额不能小于0！");
				}
				else if(amt==0) {
					tips.setText("成功取出0元！");
				}
				else {
					try {
						String writeTo = "withdraw\n" + amts + '\n';
						String answer = send(socket,writeTo);
						if(answer.equals("success")) {
							tips.setText("成功存入！");
						}
						else if(answer.equals("insufficient")) {
							tips.setText("余额不足！");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}				
			}
		});
		confirm.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		confirm.setBounds(301, 200, 104, 23);
		contentPane.add(confirm);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Service(socket);
				dispose();
			}
		});
		back.setBounds(46, 200, 104, 23);
		contentPane.add(back);
		
		JButton WRECPT = new JButton("\u6253\u5370\u51ED\u6761");
		WRECPT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		WRECPT.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		WRECPT.setEnabled(false);
		WRECPT.setBounds(176, 200, 101, 23);
		contentPane.add(WRECPT);
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
				sb.append(str);
			}
			bufferedReader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
