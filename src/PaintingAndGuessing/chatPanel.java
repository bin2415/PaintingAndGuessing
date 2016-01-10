package PaintingAndGuessing;

import java.awt.BorderLayout;
import java.awt.Event.*;

import javax.swing.*;
public class chatPanel extends JFrame{
static JTextArea chatArea;
static JTextField chatField;
JButton send;
JPanel panel;
public chatPanel(){
	chatArea=new JTextArea();
	chatField=new JTextField(15);
	send=new JButton("·¢ËÍ");
	send.addActionListener(new myMouseListener(new JLabel()));
	enterLoin en=new enterLoin(send);
	chatField.addKeyListener(en);
	panel=new JPanel();
	panel.add(chatField);
	panel.add(send);
	chatArea.setEditable(false);
	chatField.addKeyListener(new returnListener(send));
	JScrollPane scroll=new JScrollPane(chatArea);
	this.add(scroll,BorderLayout.CENTER);
	this.add(panel,BorderLayout.SOUTH);
	this.setSize(300,200);
	this.setVisible(true);
}
}
