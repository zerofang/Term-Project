package atm;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.Socket;

public class Service extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Service frame = new Service();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Service(Socket socket) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton diposit = new JButton("\u5B58\u6B3E");
		diposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Diposit(socket);
				dispose();
			}
		});
		diposit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		diposit.setBounds(157, 77, 89, 23);
		contentPane.add(diposit);
		
		JButton withdraw = new JButton("\u53D6\u6B3E");
		withdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Withdraw(socket);
				dispose();
			}
		});
		withdraw.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		withdraw.setBounds(158, 117, 89, 23);
		contentPane.add(withdraw);
		
		JButton transfer = new JButton("\u8F6C\u8D26");
		transfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Transfer(socket);
				dispose();
			}
		});
		transfer.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		transfer.setBounds(158, 156, 89, 23);
		contentPane.add(transfer);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u9009\u62E9\u670D\u52A1\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setBounds(157, 37, 86, 14);
		contentPane.add(lblNewLabel);
		
		JButton back = new JButton("\u8FD4\u56DE");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ATM();
				dispose();
			}
		});
		back.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		back.setBounds(159, 200, 89, 23);
		contentPane.add(back);
	}

}
