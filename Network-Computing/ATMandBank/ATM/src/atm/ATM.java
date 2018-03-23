package atm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ATM extends JFrame {
	private JTextArea account;
	private JTextArea pswd;
	private JButton login;
	private JButton deposit;
	private JButton withdraw;
	private JButton transfer;
	private JButton printReceipt;
	private JTextArea receipt;
	public ATM(){
		this.setSize(700, 500);
		this.setLocation(350, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("ATM");
		this.setLayout(null);
		
		
	}
}
