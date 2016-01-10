package PaintingAndGuessing;
import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;
public class Drawguess extends JFrame implements ActionListener{
receiveDrawPanel drawPanel;
static JTextField  answer,time;
JLabel answerla,timela,imageLabel;
JButton send,add;
static boolean isOpen=false;
static JTextArea messagePanel=null;
static Vector<String> friend;
person self;
public Drawguess(){
	super("Drawings");
	isOpen=true;
	friendList();
    Toolkit kit=Toolkit.getDefaultToolkit();
    Dimension screenSize=kit.getScreenSize();
    int width=(int) screenSize.getWidth();
    int height=(int) screenSize.getHeight();
    this.setLayout(null);
    drawPanel=new receiveDrawPanel();
    Thread thread=new Thread(drawPanel);
    thread.start();
    selectPerson s=new selectPerson(starting.name);
    self=s.select();
    JLabel label1=new JLabel("我:"+self.getName());
    ImageIcon image=new ImageIcon(self.getFace());
    label1.setIcon(image);
    label1.setBounds(width/2+50,height/10,200,100);
    time=new JTextField();
    time.setBounds(width/2+320, height/10,100,30);
    time.setBackground(Color.GRAY);
    time.setOpaque(false);
    timela=new JLabel("计时器");
    timela.setBounds(width/2+250,height/10,100,30);
    answerla=new JLabel("写出你的猜想:");
    answerla.setBounds(width/5*4-200,height/5*4-30,width/15,height/20);
    answer=new JTextField();
    answer.setBounds(width/5*4-100, height/5*4-30, width/7, height/20);
    answer.setBackground(Color.gray);
    answer.setOpaque(false);
    send=new JButton("发送");
    send.setBounds(width/5*4-80, height/5*4+20, width/15, height/20);
    send.addActionListener(this);
    send.setOpaque(false);
    send.setBackground(Color.GRAY);
    enterLoin en=new enterLoin(send);
    answer.addKeyListener(en);
    drawPanel.setBounds(0,0, width/2, height);
    messagePanel=new JTextArea();
    messagePanel.setEditable(false);
    messagePanel.setOpaque(false);
    messagePanel.setBackground(Color.GRAY);
    JScrollPane scroll=new JScrollPane(messagePanel);
    scroll.setOpaque(false);
    scroll.setBounds(width/3*2,height/4,width/4,height/2);
    add=new JButton("加入词语");
    add.setBackground(Color.gray);
    add.addActionListener(this);
    add.setOpaque(false);
    add.setBounds(width/3*2+100,height/10+40,100,50);
    ImageIcon ima=new ImageIcon("94cad1c8a786c917852da7a7c83d70cf3bc75706.jpg");
    imageLabel=new JLabel(ima);
    imageLabel.setBounds(0,0,width,height);
    this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));
    JPanel j=(JPanel)this.getContentPane();
    j.setOpaque(false);
    j.add(add);
    j.add(label1);
    j.add(drawPanel);
    j.add(answerla);
    j.add(answer);
    j.add(send);
    j.add(scroll);
    j.add(time);
    j.add(timela);
    setSize(width,height);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
public static void friendList(){
	System.out.println("friendList start");
	try{friend=new Vector<String>();
	Socket socket=new Socket("127.0.0.1",8899);
	BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	out.println("roomer");
	if(room.roomnumber==0)
	out.println(roomListener.roomnumber);
	else
		out.println(room.roomnumber);
	int num=Integer.parseInt(in.readLine().trim());
	System.out.println("huoquliebiao");
	if(num==2){
		String name1=in.readLine();
		String name2=in.readLine();
		friend.addElement(name1);
		friend.addElement(name2);
		}
	else
		if(num==3){
			String name1=in.readLine();
			String name2=in.readLine();
			String name3=in.readLine();
			friend.addElement(name1);
			friend.addElement(name2);
			friend.addElement(name3);
		}
		else
			if(num==4){
				String name1=in.readLine();
				String name2=in.readLine();
				String name3=in.readLine();
				String name4=in.readLine();
				friend.addElement(name1);
				friend.addElement(name2);
				friend.addElement(name3);
				friend.addElement(name4);
			}System.out.println("friendlist的大小："+friend.size());
	}
	
	catch(Exception e){
		e.printStackTrace();
	}
}
public void actionPerformed(ActionEvent e){
	if(e.getActionCommand().equals("发送")){
		String word=answer.getText();
		try{  
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
		try{
			Socket socket=new Socket("127.0.0.1",8899);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println("guess");
			out.println(starting.name);
			out.println(roomListener.roomnumber);
			out.println(word);
			String message=in.readLine();
			if(message.equals("success")){
				String s=in.readLine();
				JOptionPane.showMessageDialog(null,s);
			}
			else
				if(message.equals("false")){
					
				}
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
				}
			}
			
		}
	}

public static void main(String args[]){
	new Drawguess();
}
}
