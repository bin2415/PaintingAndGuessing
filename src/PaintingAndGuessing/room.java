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
	join=new JButton("���뷿��");
	creat=new JButton("��������");
	join.addActionListener(this);
	creat.addActionListener(this);
	this.setLayout(new GridLayout(2,1));
	this.add(join);
	this.add(creat);
	this.setVisible(true);
	this.setSize(300,200);
	this.setLocation(300,200);
}public void actionPerformed(ActionEvent e){
	if(e.getActionCommand().equals("���뷿��")){
	roomlist();
	}
	else
		if(e.getActionCommand().equals("��������")){
			try{String a=JOptionPane.showInputDialog(null,"�������������ķ���ţ�","����Ի���",JOptionPane.INFORMATION_MESSAGE);
			    roomnumber=Integer.parseInt(a);
				Socket socket=new Socket("127.0.0.1",8899);
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("creatroom");
				out.println(starting.name);
				out.println(a);
				String message=in.readLine();
				if(message.equals("exist")){
					JOptionPane.showMessageDialog(null,"�÷����Ѵ���,�����´���","����",JOptionPane.WARNING_MESSAGE);
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
	 r=new JFrame("�����б�");
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
	JLabel la=new JLabel("����"+i+"������");
	la.setOpaque(false);
	mlPanel.add(la);
	String message=in.readLine();
	while(!message.equals("over")){
		String stat="�ȴ���Ҽ���";
		String roomname=message;
		String state=in.readLine();
		String people=in.readLine();
		String host=in.readLine();
		if(state.equals("1")){
			stat="������Ϸ��";
		}
		la1=new JLabel("�����:"+roomname+" �������:"+Integer.parseInt(people)+" ������"+host+" ״̬��"+stat);
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
