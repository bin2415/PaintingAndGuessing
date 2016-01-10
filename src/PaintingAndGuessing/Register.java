package PaintingAndGuessing;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

import javax.swing.*;
import java.sql.*;
public class Register implements ActionListener{
	JFrame JRegister=new JFrame("注册");
	JPasswordField wPass,wPass1;
	JTextField tName,tMessage;
public void register(){
	Toolkit kit=Toolkit.getDefaultToolkit();
	Dimension screenSize=kit.getScreenSize();
	int width=screenSize.width;
	int height=screenSize.height;
	JRegister.setBounds(width/4,height/4,600,400);
	JRegister.setLayout(new GridLayout(6,1));
	JLabel lName,lPass,lPass1,lMessage;
	JButton jR,jC;
	lName =new JLabel("用户名");
	lPass=new JLabel("密码");
	lPass1=new JLabel("确认密码");
	lMessage=new JLabel("邮箱");
	jR=new JButton("注册");
	jC=new JButton("取消");
	jR.addActionListener(this);
	tName=new JTextField(20);
	tMessage=new JTextField(20);
	wPass=new JPasswordField(20);
	wPass1=new JPasswordField(20);
	returnListener listener1=new returnListener(wPass);
	returnListener listener2=new returnListener(wPass1);
	returnListener listener3=new returnListener(tMessage);
	enterLoin listener4=new enterLoin(jR);
	tName.addKeyListener(listener1);
	wPass.addKeyListener(listener2);
	wPass1.addKeyListener(listener3);
	tMessage.addKeyListener(listener4);
	wPass.setEchoChar('*');
	wPass1.setEchoChar('*');
	JPanel panel1,panel2,panel3,panel4,panel5;
	panel1=new JPanel();
	panel2=new JPanel();
	panel3=new JPanel();
	panel4=new JPanel();
	panel5=new JPanel();
	JRegister.add(new JLabel());
	panel1.add(lName);
	panel1.add(tName);
	panel2.add(lPass);
	panel2.add(wPass);
	panel3.add(lPass1);
	panel3.add(wPass1);
	panel4.add(lMessage);
	panel4.add(tMessage);
	panel5.add(jR);
	panel5.add(jC);
	JRegister.add(panel1);
	JRegister.add(panel2);
	JRegister.add(panel3);
	JRegister.add(panel4);
	JRegister.add(panel5);
	JRegister.setResizable(false);
	JRegister.setVisible(true);
	JRegister.addWindowListener(new ExitListener());
}public class ExitListener extends WindowAdapter{
	public void windowListener(WindowEvent e){
		JRegister.dispose();
	}}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("注册")){
		String text=null,e_mail = null;
		boolean is=true,ie=true;
		char[] pass,pass1;
		text=tName.getText();
		pass=wPass.getPassword();
		pass1=wPass1.getPassword();
		e_mail=tMessage.getText();
		String s="";
		int a,b;
		a=pass.length;
		b=pass1.length;
		if(a!=b){
			JOptionPane.showMessageDialog(null,"两次密码不一致","错误",JOptionPane.ERROR_MESSAGE);
			is=false;
			ie=false;}
		else{
			for(int i=0;i<a;i++){
				if(pass[i]!=pass1[i]){
					JOptionPane.showMessageDialog(null,"两次密码不一致","错误",JOptionPane.ERROR_MESSAGE);
					is=false;
					ie=false;
					break;
				}
				
			}
		}
		for(int i=0;i<a;i++)
			s+=pass[i];
		if(is){
			if(text==null){
				ie=false;
				JOptionPane.showMessageDialog(null, "用户名不能为空，请重新注册","错误",JOptionPane.ERROR_MESSAGE);
			}
			else
			if(s==""){
				ie=false;
				JOptionPane.showMessageDialog(null, "密码不能为空，请重新注册","错误",JOptionPane.ERROR_MESSAGE);
			}}
		if(ie){
			try{Socket socket=new Socket("127.0.0.1",8899);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println("new");
			out.println(text);
			out.println(s);
			out.println(e_mail);
			String msg=in.readLine();
			if(msg.equals("exist")){
				JOptionPane.showMessageDialog(null,"用户名已存在啊，请重新注册","注册错误",JOptionPane.ERROR_MESSAGE);
			}else
				if(msg.equals("ok")){
				JOptionPane.showMessageDialog(null,"您已注册成功,请尽情的游戏吧");
				}	
			in.close();
			out.close();
			socket.close();}catch(Exception e1){
				e1.printStackTrace();
				}
			}
		}else{
			JRegister.dispose();}
		}
		}
	

