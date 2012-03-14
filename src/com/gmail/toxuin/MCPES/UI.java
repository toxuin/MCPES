package com.gmail.toxuin.MCPES;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * Этот класс отвечает за внешний вид сервера.
 * Когда-нибудь нужно будет все здесь расхуярить и сделать CLI.
 */

public class UI {

	JFrame frame;
	JPanel panel;
	static JButton button1;
	static JButton button2;
	static JTextArea area;
	JScrollPane pane;
	
	public UI() {
		frame = new JFrame("Опа сервер");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		panel = new JPanel();
		panel.setLayout(null);
		area = new JTextArea();
		button1 = new JButton("Старт");
		button1.setBounds(210, 10, 75, 40);
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				new UDPServer.StartThread();
				log("Сервер запущен\n");
			}
		});
		panel.add(button1);
		button2 = new JButton("Стоп");
		button2.setBounds(300, 10, 75, 40);
		button2.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent ae){
				Thread.interrupted();
				UDPServer.running = false;
				UDPServer.socket.close();
				log("Сервер остановлен\n");
				button1.setEnabled(true);
				button2.setEnabled(false);
			}
		});
		button2.setEnabled(false);
		panel.add(button2);
		
		pane = new JScrollPane(area);
		pane.setBounds(10, 60, 365, 250);
		panel.add(pane);
		frame.add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	public static void log(String text) {
		area.append(text+"\n");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new UI();
	}
}
