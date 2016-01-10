package PaintingAndGuessing;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class room extends JFrame implements ActionListener{
	JButton join,creat;
	static int roomnumber=0;
	static JFrame r;
public room(){
	join=new JButton("加入房间");
	creat=new JButton("创建房间");
	join.addActionListener(this);
	creat.addActionListener(this);
	this.setLayout(new GridLayout(2,1));
	this.add(join);
	this.add(creat);
	this.setVisible(true);
	this.setSize(300,200);
	this.setLocation(300,200);
}public void actionPerformed(ActionEvent e){
	if(e.getActionCommand().equals("加入房间")){
	roomlist();
	}
	else
		if(e.getActionCommand().equals("创建房间")){
			try{String a=JOptionPane.showInputDialog(null,"请输入所创建的房间号：","输入对话框",JOptionPane.INFORMATION_MESSAGE);
			    roomnumber=Integer.parseInt(a);
				Socket socket=new Socket("127.0.0.1",8899);
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("creatroom");
				out.println(starting.name);
				out.println(a);
				String message=in.readLine();
				if(message.equals("exist")){
					JOptionPane.showMessageDialog(null,"该房间已存在,请重新创建","错误",JOptionPane.WARNING_MESSAGE);
				}
				else
					if(message.equals("ok")){
						dispose();
						new drawTogether();
					}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
}
public void roomlist(){
	 r=new JFrame("房间列表");
	JPanel mlPanel=new JPanel();
	mlPanel.setOpaque(false);
	JScrollPane scroll=new JScrollPane(mlPanel);
	JLabel la1;
	try{
	Socket socket =new Socket("127.0.0.1",8899);
	BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	out.println("roomlist");
	int i=Integer.parseInt(in.readLine().trim());
	mlPanel.setLayout(new GridLayout(i+1,1,4,4));
	JLabel la=new JLabel("共有"+i+"个房间");
	la.setOpaque(false);
	mlPanel.add(la);
	String message=in.readLine();
	while(!message.equals("over")){
		String stat="等待玩家加入";
		String roomname=message;
		String state=in.readLine();
		String people=in.readLine();
		String host=in.readLine();
		if(state.equals("1")){
			stat="正在游戏中";
		}
		la1=new JLabel("房间号:"+roomname+" 玩家人数:"+Integer.parseInt(people)+" 房主："+host+" 状态："+stat);
		la1.setBackground(Color.cyan);
		la1.setForeground(Color.cyan);
		la1.addMouseListener(new roomListener(la1));
		mlPanel.add(la1);
		message=in.readLine();
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	r.setSize(500,400);
	r.setLocation(400,300);
	r.setContentPane(mlPanel);
	r.setVisible(true);
	r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}

}
