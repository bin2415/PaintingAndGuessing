package PaintingAndGuessing;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
public class drawTogether extends JFrame implements ActionListener{
	DrawingBoard drawPanel=new DrawingBoard();
	JButton send,start,add;
	JTextField answer,time,word;
	JLabel answerla,timela,imageLabel,describe;
	static boolean isOpen=false;
	static JTextArea messagePanel=null;
	static Vector<String> list;
	static Vector<String> friend;
	JPanel j,timep;
	person self;
	int width,height;
	public drawTogether(){
		super("Drawings");
		isOpen=true;
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();
         width=(int) screenSize.getWidth();
         height=(int) screenSize.getHeight();
        this.setLayout(null);
        selectPerson s=new selectPerson(starting.name);
        self=s.select();
        JLabel label1=new JLabel("房主:"+self.getName());
        ImageIcon image=new ImageIcon(self.getFace());
        label1.setIcon(image);
        label1.setBounds(width/2+50,height/10,200,100);
        answerla=new JLabel("写出你的猜想:");
        answerla.setBounds(width/5*4-200,height/5*4-30,width/15,height/20);
        answer=new JTextField();
        answer.setBounds(width/5*4-100, height/5*4-30, width/7, height/20);
        answer.setBackground(Color.gray);
        describe= new JLabel("您所要描绘的词语为：");
        describe.setBounds(733, 170, width/7, height/20);
        describe.setBackground(Color.gray);
        word=new JTextField();
        word.setBounds(733, 210, width/11, height/20);
        word.setOpaque(false);
        word.setBackground(Color.gray);
        word.setBackground(Color.LIGHT_GRAY);
        send=new JButton("发送");
        send.addActionListener(this);
        send.setOpaque(false);
        send.setBackground(Color.GRAY);
        send.setBounds(width/5*4-80, height/5*4+20, width/15, height/20);
        send.setOpaque(false);
        enterLoin en=new enterLoin(send);
        answer.addKeyListener(en);
        answer.setOpaque(false);
        drawPanel.setBounds(0,0, width/2, height);
        time=new JTextField();
        time.setBounds(width/2+270, height/20,100,30);
        time.setBackground(Color.gray);
        time.setOpaque(false);
        start=new JButton("开始");
        start.setOpaque(false);
        start.setBackground(Color.GRAY);
        start.addActionListener(this);
        start.setBounds(width/3*2+10,height/10+20,100,50);
        timela=new JLabel("计时器");
        timela.setBounds(width/2+200,height/20,100,30);
        messagePanel=new JTextArea();
        messagePanel.setOpaque(false);
        messagePanel.setBackground(Color.gray);
        messagePanel.setEditable(false);
        JScrollPane scroll=new JScrollPane(messagePanel);
        scroll.setBackground(Color.gray);
        scroll.setOpaque(false);
        scroll.setBounds(width/3*2,height/4,width/4,height/2);
        this.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				try{
					Socket socket=new Socket("127.0.0.1",8899);
					PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println("closeroom");
					out.println(room.roomnumber);
					out.println(list.size());
					for(int i=0;i<list.size();i++){
						out.println(list.get(i));
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				dispose();
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        ImageIcon ima=new ImageIcon("94cad1c8a786c917852da7a7c83d70cf3bc75706.jpg");
        imageLabel=new JLabel(ima);
        imageLabel.setBounds(0,0,width,height);
        this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));
         j=(JPanel)this.getContentPane();
        add=new JButton("加入词语");
        add.setOpaque(false);
        add.addActionListener(this);
        add.setBackground(Color.gray);
        add.setBounds(width/3*2+200,height/10+20,100,50);
        ImageIcon imge=new ImageIcon("u=1767957811,2507653835&fm=23&gp=0.jpg");
        j.setOpaque(false);
        j.add(add);
        j.add(label1);
        j.add(time);
        j.add(scroll);
        j.add(drawPanel);
        j.add(answerla);
        j.add(answer);
        j.add(send);
        j.add(start);
        j.add(timela);
        j.add(describe);
        j.add(word);
        setSize(width,height);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}public void actionPerformed(ActionEvent e){
	if(e.getActionCommand().equals("开始")){
		T timer=new T();
		timer.s();
		try{
			list=new Vector<String>();
		friend=new Vector<String>();
			Socket socket=new Socket("127.0.0.1",8899);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println("roomer");
			if(room.roomnumber==0)
			out.println(roomListener.roomnumber);
			else
				out.println(room.roomnumber);
			int num=Integer.parseInt(in.readLine().trim());
			if(num==1){
				JOptionPane.showMessageDialog(null,"该房间只有你一人，不能进行游戏","错误",JOptionPane.WARNING_MESSAGE);
			}
			else
				if(num==2){
				String name1=in.readLine();
				String name2=in.readLine();
				friend.addElement(name1);
				friend.addElement(name2);
				if(name1.equals(starting.name))
				list.addElement(name2);
				else
					if(name2.equals(starting.name))
						list.addElement(name1);
			}
				else
					if(num==3){
				String name1=in.readLine();
				String name2=in.readLine();
				String name3=in.readLine();
				friend.addElement(name1);
				friend.addElement(name2);
				friend.addElement(name3);
				if(name1.equals(starting.name)){
					list.addElement(name2);
					list.addElement(name3);
				}
				else
					if(name2.equals(starting.name)){
						list.addElement(name1);
						list.addElement(name3);}
					else
						if(name3.equals(starting.name)){
							list.addElement(name1);
							list.addElement(name2);
						}
					}
					else
						if(num==4){
							String name1=in.readLine();
							String name2=in.readLine();
							String name3=in.readLine();
							String name4=in.readLine();
							friend.add(name1);
							friend.add(name2);
							friend.add(name3);
							friend.add(name4);
							if(name1.equals(starting.name)){
								list.addElement(name2);
								list.addElement(name3);
								list.addElement(name4);
							}
							else
								if(name2.equals(starting.name)){
									list.addElement(name1);
									list.addElement(name3);
									list.addElement(name4);}
								else
									if(name3.equals(starting.name)){
										list.addElement(name1);
										list.addElement(name2);
										list.addElement(name4);
									}
									else
										if(name4.equals(starting.name)){
											list.addElement(name1);
											list.addElement(name2);
											list.addElement(name3);
										}
						}
		}catch(Exception ex){
			ex.printStackTrace();
		}try{
			Socket socket=new Socket("127.0.0.1",8899);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println("game");
			out.println(room.roomnumber);
			String name=in.readLine();
			String help=in.readLine();
			answer.setText(help);
			send.doClick();
			JOptionPane.showMessageDialog(null,"词语:"+name+"\n提示:"+help);
			word.setText(name);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			Socket socket=new Socket("127.0.0.1",8899);
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println("start");
			out.println(room.roomnumber);
			out.println(list.size());
			for(int i=0;i<list.size();i++){
				out.println(list.get(i));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	else
		if(e.getActionCommand().equals("发送")){
try{  String word=answer.getText();
			Socket socket=new Socket("127.0.0.1",8899);
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		    String info=answer.getText();
		    out.println("MSG:paint");
		    out.println(starting.name);
		    out.println(info);
		    out.println(friend.size());
		    for(int i=0;i<friend.size();i++)
		    out.println(friend.get(i));
		    out.flush();
		    answer.setText("");
	}catch(Exception ex){
			ex.printStackTrace();
	}		
		}
		else
			if(e.getActionCommand().equals("加入词语")){
				String a=JOptionPane.showInputDialog(null,"请输入所要添加的词语","添加词语",JOptionPane.INFORMATION_MESSAGE);
				String b=JOptionPane.showInputDialog(null,"请输入词语的提示","添加提示",JOptionPane.INFORMATION_MESSAGE);
				if(a==null){
					JOptionPane.showMessageDialog(null,"词语不能为空","警告",JOptionPane.WARNING_MESSAGE);
				}
				else
				if(b==null){
					JOptionPane.showMessageDialog(null,"词语不能为空","警告",JOptionPane.WARNING_MESSAGE);
				}
				else{
					try{
						Socket socket=new Socket("127.0.0.1",8899);
						BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
						out.println("insertword");
						out.println(a);
						out.println(b);
						String message=in.readLine();
						if(message.equals("exist")){
							JOptionPane.showMessageDialog(null,"词语已存在","警告",JOptionPane.WARNING_MESSAGE);
						}
						else
							if(message.equals("ok")){
								JOptionPane.showMessageDialog(null,"已添加成功");
							}
					}catch(Exception ex){
						ex.printStackTrace();
					}}}
}
public class T extends Thread{
	public T(){
	}
	public void s(){
		start();
	}
	public void run(){
		try{
		for(int i=60;i>=0;i--){
			String a=Integer.toString(i);
			time.setText(a);
			sleep(1000);
			if(i==0){
				Socket socket=new Socket("127.0.0.1",8899);
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("finish");
				out.println(room.roomnumber);
			}
		}
		stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
	public static void main(String args[]){
		new drawTogether();}
	}
