package atm;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ATM {

	private JFrame frmAtm;
	private JTextField account;
	private JTextField password;
	private JLabel tips;
	private JPanel Jservice;
	private JPanel Jlogin;
	private JPanel JDiposit;
	private JTextField DPamount;
	private JPanel JWithdraw;
	private JTextField WDamount;
	private JPanel JTransfer;
	private JTextField TFtoAccount;
	private JTextField TFamount;
	
	BufferedWriter bufferedWriter;
	BufferedReader bufferedReader;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATM window = new ATM();
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
	public ATM() {
		frmAtm = new JFrame();
		frmAtm.setTitle("ATM-\u767B\u5F55");
		frmAtm.setBounds(100, 100, 450, 300);
		frmAtm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Jlogin = new JPanel(); 
		Jlogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		Jlogin.setLayout(null);
		Jlogin.setVisible(true);
		
		JLabel label = new JLabel("\u8D26\u53F7");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		label.setBounds(65, 104, 31, 14);
		Jlogin.add(label);
		
		account = new JTextField();
		account.setBounds(106, 102, 204, 20);
		Jlogin.add(account);
		account.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		label_1.setBounds(65, 146, 31, 14);
		Jlogin.add(label_1);
		
		password = new JTextField();
		password.setBounds(106, 144, 204, 20);
		Jlogin.add(password);
		password.setColumns(10);
		
		tips = new JLabel("");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		tips.setForeground(Color.RED);
		tips.setBounds(106, 65, 204, 14);
		Jlogin.add(tips);
		
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
					Socket socket = null;
					try {
						socket = new Socket("127.0.0.1",8888);
						bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String writeTo = "login\n" + userAccount + '\n' + userPSW+'\n';
					String answer;
					try {
						send(socket,writeTo);
						answer = read(socket);
						if(answer.equals("notFound")) {
							tips.setText("ÕË»§²»´æÔÚ£¡");
						}
						else if(answer.equals("pswdError")){
							tips.setText("ÃÜÂë´íÎó£¡");
						}
						else if(answer.equals("success")){
							System.out.println(answer);
							//frmAtm.remove(Jlogin);
							Jlogin.setVisible(false);
							Service(socket);
						}
						else {
							tips.setText("Î´Öª´íÎó£¡");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		login.setBounds(106, 197, 204, 23);
		Jlogin.add(login);
		frmAtm.setContentPane(Jlogin);
	}
	
	public void Service(Socket socket) {
		Jservice = new JPanel();
		Jservice.setBorder(new EmptyBorder(5, 5, 5, 5));
		Jservice.setLayout(null);
		Jservice.setVisible(true);
		frmAtm.setTitle("ATM-Ñ¡Ôñ·þÎñ");
		
		JButton diposit = new JButton("\u5B58\u6B3E");
		diposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//frmAtm.remove(Jservice);
				Jservice.setVisible(false);
				Diposit(socket);
			}
		});
		diposit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		diposit.setBounds(157, 77, 89, 23);
		Jservice.add(diposit);
		
		JButton withdraw = new JButton("\u53D6\u6B3E");
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frmAtm.remove(Jservice);
				Jservice.setVisible(false);
				Withdraw(socket);
			}
		});
		withdraw.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		withdraw.setBounds(158, 117, 89, 23);
		Jservice.add(withdraw);
		
		JButton transfer = new JButton("\u8F6C\u8D26");
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frmAtm.remove(Jservice);
				Jservice.setVisible(false);
				Transfer(socket);
			}
		});
		transfer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		transfer.setBounds(158, 156, 89, 23);
		Jservice.add(transfer);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u9009\u62E9\u670D\u52A1\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setBounds(157, 37, 86, 14);
		Jservice.add(lblNewLabel);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frmAtm.remove(Jservice);
				Jservice.setVisible(false);
				initialize();
			}
		});
		back.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		back.setBounds(159, 200, 89, 23);
		Jservice.add(back);
		frmAtm.setContentPane(Jservice);
	}
	
	public void Diposit(Socket socket) {
		JDiposit = new JPanel();
		JDiposit.setBorder(new EmptyBorder(5, 5, 5, 5));
		JDiposit.setLayout(null);
		JDiposit.setVisible(true);
		frmAtm.setTitle("ATM-´æ¿î");
		
		JLabel tips = new JLabel("");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setForeground(Color.RED);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		tips.setBounds(142, 70, 164, 14);
		JDiposit.add(tips);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u5B58\u6B3E\u91D1\u989D\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setBounds(59, 123, 109, 14);
		JDiposit.add(lblNewLabel);
		
		DPamount = new JTextField();
		DPamount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		DPamount.setBounds(175, 120, 101, 20);
		JDiposit.add(DPamount);
		DPamount.setColumns(10);
		
		JButton confirm = new JButton("\u786E\u8BA4");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amts = DPamount.getText();
				amts.trim();
				if(amts.length()==0) {
					tips.setText("ÇëÊäÈë´æ¿î½ð¶î£¡");
				}
				int amt = Integer.parseInt(amts);
				if(amt<0) {
					tips.setText("´æ¿î½ð¶î²»ÄÜÐ¡ÓÚ0£¡");
				}
				else if(amt==0) {
					tips.setText("³É¹¦´æÈë0Ôª£¡");
				}
				else {
					try {
						String writeTo = "diposit\n" + amts + '\n';
						send(socket,writeTo);
						String answer  = read(socket);
						if(answer.equals("success")) {
							//answer = read(socket);
							tips.setText("³É¹¦´æÈë"+amts+"Ôª£¡");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		confirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		confirm.setBounds(311, 208, 101, 23);
		JDiposit.add(confirm);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDiposit.setVisible(false);
				Service(socket);			
			}
		});
		back.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		back.setBounds(35, 208, 101, 23);
		JDiposit.add(back);
		frmAtm.setContentPane(JDiposit);
	}
	
	public void Withdraw(Socket socket) {
		JWithdraw = new JPanel();
		JWithdraw.setBorder(new EmptyBorder(5, 5, 5, 5));
		JWithdraw.setLayout(null);
		JWithdraw.setVisible(true);
		frmAtm.setTitle("ATM-È¡¿î");
		
		JLabel tips = new JLabel("");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setForeground(Color.RED);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		tips.setBounds(127, 58, 186, 14);
		JWithdraw.add(tips);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u53D6\u6B3E\u91D1\u989D\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setBounds(62, 113, 104, 14);
		JWithdraw.add(lblNewLabel);
		
		WDamount = new JTextField();
		WDamount.setBounds(176, 111, 104, 20);
		JWithdraw.add(WDamount);
		WDamount.setColumns(10);
		
		JButton confirm = new JButton("\u786E\u8BA4");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amts = WDamount.getText();
				amts.trim();
				if(amts.length()==0) {
					tips.setText("ÇëÊäÈëÈ¡¿î½ð¶î£¡");
				}
				int amt = Integer.parseInt(amts);
				if(amt<0) {
					tips.setText("È¡¿î½ð¶î²»ÄÜÐ¡ÓÚ0£¡");
				}
				else if(amt==0) {
					tips.setText("³É¹¦È¡³ö0Ôª£¡");
				}
				else {
					try {
						String writeTo = "withdraw\n" + amts + '\n';
						send(socket,writeTo);
						String answer  = read(socket);
						if(answer.equals("success")) {
							tips.setText("³É¹¦È¡³ö"+amts+"Ôª£¡");
						}
						else if(answer.equals("insufficient")) {
							tips.setText("Óà¶î²»×ã£¡");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}				
			}
		});
		confirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		confirm.setBounds(301, 200, 104, 23);
		JWithdraw.add(confirm);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JWithdraw.setVisible(false);
				Service(socket);
			}
		});
		back.setBounds(30, 201, 104, 23);
		JWithdraw.add(back);
		frmAtm.setContentPane(JWithdraw);
	}
	
	public void Transfer(Socket socket) {
		JTransfer = new JPanel();
		JTransfer.setBorder(new EmptyBorder(5, 5, 5, 5));
		JTransfer.setLayout(null);
		JTransfer.setVisible(true);
		frmAtm.setTitle("ATM-×ªÕË");
		
		JLabel tips = new JLabel("");
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setForeground(Color.RED);
		tips.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		tips.setBounds(137, 57, 189, 14);
		JTransfer.add(tips);
		
		JLabel lblNewLabel = new JLabel("\u8F6C\u5165\u8D26\u6237\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setBounds(82, 108, 67, 14);
		JTransfer.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8F6C\u8D26\u91D1\u989D\uFF1A");
		lblNewLabel_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(83, 152, 66, 14);
		JTransfer.add(lblNewLabel_1);
		
		TFtoAccount = new JTextField();
		TFtoAccount.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		TFtoAccount.setBounds(157, 105, 151, 20);
		JTransfer.add(TFtoAccount);
		TFtoAccount.setColumns(10);
		
		TFamount = new JTextField();
		TFamount.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		TFamount.setBounds(156, 149, 152, 20);
		JTransfer.add(TFamount);
		TFamount.setColumns(10);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTransfer.setVisible(false);
				Service(socket);			
			}
		});
		back.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		back.setBounds(31, 209, 101, 23);
		JTransfer.add(back);
		
		JButton confirm = new JButton("\u786E\u8BA4");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String account = TFtoAccount.getText();
					String amt = TFamount.getText();
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
						send(socket,writeTo);
						String answer  = read(socket);
						if(answer.equals("success")) {
							tips.setText("³É¹¦×ªÕË"+amt+"£¡");
						}
						else if(answer.equals("insufficient")) {
							tips.setText("Óà¶î²»×ã£¡");
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
		JTransfer.add(confirm);
		frmAtm.setContentPane(JTransfer);
	}
	public String read(Socket socket) throws IOException{
		try {
			String line = bufferedReader.readLine(); 
			return line;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return "0";
		}
	}
	public boolean send(Socket socket,String writeTo) throws IOException{
		try {
			bufferedWriter.write(writeTo);
			bufferedWriter.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}
