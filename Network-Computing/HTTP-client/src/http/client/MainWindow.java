package http.client;

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

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea txt;
	private String url;
	private int port;
	private JTextArea getUrl;
	private JTextArea getPort;
	private JTextArea getTxt;
	private JTextArea attrTxt;

	public MainWindow() {
		this.setSize(700, 500);
		this.setLocation(350, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("HTTP Client 1.0");
		this.setLayout(null);
		
		JLabel logLabel = new JLabel("LOG");
		logLabel.setBounds(20, 10, 170, 35);
		this.add(logLabel);
		txt = new JTextArea();
		txt.setEditable(false);
		JScrollPane scroll = new JScrollPane(txt);
		scroll.setBounds(20, 40, 400, 400);
		this.add(scroll);
		
		JLabel urlLabel = new JLabel("URL");
		urlLabel.setBounds(470, 10, 170, 35);
		this.add(urlLabel);
		getUrl = new JTextArea();
		JScrollPane urlSP = new JScrollPane(getUrl);
		urlSP.setBounds(470, 40, 170, 23);
		this.add(urlSP);
		
		JLabel portLabel = new JLabel("PORT");
		portLabel.setBounds(470, 70, 170, 35);
		this.add(portLabel);
		getPort = new JTextArea();
		JScrollPane portSP = new JScrollPane(getPort);
		portSP.setBounds(470, 100, 170, 23);
		this.add(portSP);
		
		JLabel getLabel = new JLabel("PATH");
		getLabel.setBounds(470, 130, 170, 35);
		this.add(getLabel);		
		getTxt = new JTextArea();
		JScrollPane scr = new JScrollPane(getTxt);
		scr.setBounds(470, 160, 170, 23);
		this.add(scr);

		JLabel attLabel = new JLabel("QUERY / CONTENT");
		attLabel.setBounds(470, 190, 170, 35);
		this.add(attLabel);
		attrTxt = new JTextArea();
		JScrollPane scrl = new JScrollPane(attrTxt);
		scrl.setBounds(470, 220, 170, 23);
		this.add(scrl);
		addControlArea();
		this.setResizable(false);
		this.setVisible(true);
	}

	private void addControlArea() {
		JButton b1 = new JButton("GET");
		b1.setBounds(510, 280, 100, 30);
		this.add(b1);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				url = getUrl.getText();
				port = Integer.parseInt(getPort.getText());
				txt.setText(txt.getText() + "\n正在连接到 " + url + "...");
				try {
					long startTime = System.currentTimeMillis();
					Socket socket = new Socket(url, port);
					long endTime   = System.currentTimeMillis(); 
					txt.setText(txt.getText() + "\n连接成功！ ");
					txt.setText(txt.getText() + "\n连接用时: " + (endTime - startTime) + "ms");
					String getStr = getTxt.getText();
					String attrStr = attrTxt.getText();
					String request = "GET /" + getStr.replaceAll(" ", "")
							+"?"+ attrStr.replaceAll(" ", "") + " HTTP/1.0\n\n";
					txt.setText(txt.getText() + "\n发送请求: \n" + request);
					startTime = System.currentTimeMillis();
					String result = send(socket, request);
					endTime   = System.currentTimeMillis(); 
					txt.setText(txt.getText() + "\n得到返回结果: \n" + result);
					txt.setText(txt.getText() + "\n通信用时: " + (endTime - startTime) + "ms\n");
					socket.close();
				} catch (IOException ex) {
					txt.setText(txt.getText() + "\n连接失败！ ");
				}
			}
		});
		JButton b2 = new JButton("POST");
		b2.setBounds(510, 340, 100, 30);
		this.add(b2);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				url = getUrl.getText();
				port = Integer.parseInt(getPort.getText());	
				txt.setText(txt.getText() + "\n正在连接到 " + url + "...");
				try {				
					long startTime = System.currentTimeMillis();
					Socket socket = new Socket(url, port);
					long endTime   = System.currentTimeMillis(); 
					txt.setText(txt.getText() + "\n连接成功！ ");
					txt.setText(txt.getText() + "\n连接用时: " + (endTime - startTime) + "ms");
					String getStr = getTxt.getText();
					String attrStr = attrTxt.getText();
					String request = "POST /" + getStr.replaceAll(" ", "") + " HTTP/1.0\r\n" 
							+ "Accept-Encoding: */*\r\n"
							+ "Accept-Language: zh-cn\r\n"
							+ "host: localhost\r\n"
							+ "Content-Type: application/x-www-form-urlencoded\r\n"
							+ "Content-Length: " + attrStr.replaceAll(" ", "").length() + "\r\n"
							+ "\r\n"
							+ attrStr.replaceAll(" ", "") + "\r\n";
					txt.setText(txt.getText() + "\n发送请求: \n" + request);
					startTime = System.currentTimeMillis();
					String result = send(socket, request);
					endTime   = System.currentTimeMillis(); 
					txt.setText(txt.getText() + "\n得到返回结果: \n" + result);
					txt.setText(txt.getText() + "\n通信用时: " + (endTime - startTime) + "ms\n");
					socket.close();
				} catch (IOException ex) {
					txt.setText(txt.getText() + "\n连接失败 ！");
				}
			}
		});
/*		JButton b3 = new JButton("HEAD");
		b3.setBounds(510, 360, 100, 30);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				url = getUrl.getText();
				port = Integer.parseInt(getPort.getText());		
				txt.setText(txt.getText() + "\nConnecting to " + url + "..");
				try {			
					long startTime = System.currentTimeMillis();
					Socket socket = new Socket(url, port);
					long endTime   = System.currentTimeMillis();
					txt.setText(txt.getText() + "\n连接成功！ ");
					txt.setText(txt.getText() + "\n连接用时: " + (endTime - startTime) + "ms");
					String getStr = getTxt.getText();
					getTxt.setText("");
					String request = "HEAD /" + getStr.replaceAll(" ", "")
							+ " HTTP/1.0\n\n";
					txt.setText(txt.getText() + "\n发送请求: \n" + request);
					startTime = System.currentTimeMillis();
					String result = send(socket, request);
					endTime   = System.currentTimeMillis(); 
					txt.setText(txt.getText() + "\n得到返回结果: \n" + result);
					txt.setText(txt.getText() + "\n通信用时: " + (endTime - startTime) + "ms\n");
					socket.close();
				} catch (IOException ex) {
					txt.setText(txt.getText() + "\n连接失败 ！");
				}
			}
		});
		this.add(b3);*/
		JButton b4 = new JButton("Clear Log");
		b4.setBounds(510, 400, 100, 30);
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt.setText("");
			}
		});
		this.add(b4);
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