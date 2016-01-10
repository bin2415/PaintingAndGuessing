package PaintingAndGuessing;
import javax.swing.*;

import PaintingAndGuessing.drawTogether.T;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
public class playGames extends JFrame implements ActionListener,MouseListener{
	int width,height;
	int i=0;
 static JPanel pan,jp;
	String friend;
	  JTextField friendName;
	 JButton bFind,changeImage;
	JLabel FriendLabel,imageLabel,player;
	 String friendname;
	Socket socket;
	private BufferedReader read;
	private PrintWriter write;
	MessageList messageList;
	static room roo;
	public playGames(){
		new chat();
		messageList=new MessageList();
		jp=new JPanel();
		jp.setLayout(null);
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int width=(int)screenSize.getWidth();
		int height=(int)screenSize.getHeight();
		setTitle("Play Game");
		JButton button=new JButton("开始游戏");
		button.addActionListener(this);
		FriendLabel=new JLabel("好友列表");
		bFind=new JButton("查找好友");
		bFind.setBackground(Color.LIGHT_GRAY);
		bFind.setForeground(Color.orange);
		bFind.setOpaque(false);
		bFind.addActionListener(this);
		friendName=new JTextField();
		friendName.setForeground(Color.RED);
		friendName.setOpaque(false);
		FriendLabel.setOpaque(false);
		FriendLabel.setForeground(Color.YELLOW);
		FriendLabel.setBounds(width/35*13,height/5*2,width/10,height/15);
		button.setBackground(Color.LIGHT_GRAY);
		ImageIcon image=new ImageIcon("javaStaring.jpg");
		int Iwidth,Iheight;
		Iwidth=image.getIconWidth();
		Iheight=image.getIconHeight();
		button.setBounds(width/7*3, height/60*18, width/8, height/20);
		button.setForeground(Color.ORANGE);
		button.setOpaque(false);
		JLabel label=new JLabel(image);
		getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		 JPanel j=(JPanel) getContentPane();
		 label.setBounds(0,0,Iwidth,Iheight);
		 windowDestroy winDestroy =new windowDestroy(this);
		 addWindowListener(winDestroy);
	     getContentPane().requestFocus();
		 //kit.addAWTEventListener(new escListener(),AWTEvent.KEY_EVENT_MASK);
		 j.setOpaque(false);
		jp.setOpaque(false);
		bFind.setBounds(width/5*3,height/4*3,width/10,height/30);
		friendName.setBounds(width/7*3, height/4*3, width/8, height/30);
		add(button);
		String faceName=starting.face;
		String a=faceName.substring(starting.name.length(),faceName.length());
		ImageIcon imageFace=new ImageIcon(a);
		imageLabel=new JLabel(imageFace);
		imageLabel.setBounds(width/7*3,height/15,imageFace.getIconWidth(),imageFace.getIconHeight());
		player=new JLabel("玩家:        "+starting.name);
		player.setForeground(Color.YELLOW);
		player.setBounds(width/7*3,height/15+80,100,30);
		changeImage=new JButton("更换头像");
		changeImage.setBackground(Color.LIGHT_GRAY);
		changeImage.setOpaque(false);
		changeImage.setForeground(Color.CYAN);
		changeImage.setBounds(width/7*3+100,height/15+50,120,30);
		changeImage.addActionListener(this);
		pan=new JPanel();
		pan.setLayout(new GridLayout(i,1,4,8));
		pan.setOpaque(false);
		JScrollPane scroll=new JScrollPane(pan);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBounds(width/7*3,height/5*2,width/8,height/3);
		jp.add(scroll);
		jp.add(button);
		jp.add(bFind);
		jp.add(friendName);
		jp.add(FriendLabel);
		jp.add(imageLabel);
		jp.add(player);
		jp.add(changeImage);
		add(jp);
		setBounds(0,0,Iwidth,Iheight);
		setVisible(true);
		friendList();
	}
	
//编写好友列表
public void friendList(){
	try{Socket socket=new Socket("127.0.0.1",8899);
	BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	out.println("friend");
	out.println(starting.name);
	int number=Integer.parseInt(in.readLine().trim());
	i=number+1;
	JLabel l=new JLabel("您共有"+number+"个好友");
	l.setOpaque(false);
	l.setForeground(Color.orange);
	pan.add(l);
	String over=in.readLine();
	while(!over.equals("over")){
	String name=over;
	String face=in.readLine().trim();
	String faceImage=face.substring(name.length()+2,face.length());
	ImageIcon image=new ImageIcon(faceImage);
	int status=Integer.parseInt(in.readLine().trim());
	String sta="离线";
	if(status==1)
		sta="在线";
	JLabel label=new JLabel(name+"        "+sta);
	label.setIcon(image);
	label.setOpaque(false);
    label.setForeground(Color.RED);
    label.addMouseListener(new myMouseListener(label));
	pan.add(label);
	over=in.readLine();}
	in.close();
	out.close();
	socket.close();
		}catch(Exception e){
			e.printStackTrace();}}
	
		//查找好友
public  void selectFriend(){
	try{
	Socket socket=new Socket("127.0.0.1",8899);
	BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	out.println("find");
	String textname=friendName.getText();
	out.println(textname);
	String have=in.readLine();
	String name,face,statue="离线";
	if(have.equals("ok")){
		name=in.readLine().trim();
		friendname=name;
		face=in.readLine().trim();
		int a=Integer.parseInt(in.readLine().trim());
		if(a==1)
			statue="在线";
		JFrame frame=new JFrame("好友信息");
		JPanel p=(JPanel)frame.getContentPane();
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		p1.setLayout(new GridLayout(1,2));
		String imageName=face.substring(name.length(),face.length());
		ImageIcon i=new ImageIcon(imageName);
		JButton add=new JButton("添加为好友");
		add.addActionListener(this);
		JLabel imageL=new JLabel(i);
		p1.add(imageL);
		JPanel pa=new JPanel();
		pa.setLayout(new GridLayout(2,1));
		JLabel namel=new JLabel("用户名： "+name);
		JLabel statuL=new JLabel("状态: "+statue);
		pa.add(namel);
		pa.add(statuL);
		p1.add(pa);
		p2.add(add);
		p.setLayout(new BorderLayout());
		p.add(p1,BorderLayout.CENTER);
		p.add(p2,BorderLayout.SOUTH);
		frame.setSize(300,300);
		frame.setLocation(300,300);
		frame.setVisible(true);
	}
	else
		if(have.equals("false")){
			JOptionPane.showMessageDialog(null,"您查找的好友不存在","查找错误",JOptionPane.WARNING_MESSAGE);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
		
}//查找好友按钮监听器
public void actionPerformed(ActionEvent e){
	if(e.getActionCommand().equals("查找好友")){
		selectFriend();}
	else{
		if(e.getActionCommand().equals("更换头像")){
			JFrame frame=new JFrame();
			JPanel p=new JPanel();
			for(int i=1;i<14;i++){
				faceImage face=new faceImage("头像"+i+".jpg","头像"+i+".jpg");
				  JLabel label=new JLabel(face);
				  label.addMouseListener(this);
	              p.add(label);
			}
		    JScrollPane scrollFace=new JScrollPane(p);
		    p.setLayout(new GridLayout(13,1,0,6));
		    frame.add(scrollFace);
		    frame.setResizable(false);
		    frame.setBounds(300, 200, 100, 400);
		    frame.setVisible(true);
		}
		else
			if(e.getActionCommand().equals("添加为好友")){
				try{
				Socket socket=new Socket("127.0.0.1",8899);
				BufferedReader in1=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out1=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out1.println("addfriend");
				out1.println(friendname);
				out1.println(starting.name);
				String success=in1.readLine();
				if(success.equals("exist")){
					JOptionPane.showMessageDialog(null,"您已添加了该好友,无需再添加","提示",JOptionPane.WARNING_MESSAGE);
				}else{
					if(success.equals("ok"));{
					JOptionPane.showMessageDialog(null,"添加成功");
					this.setVisible(false);
					new playGames();
				}
					out1.close();
					in1.close();
					socket.close();
				}
				}catch(Exception ex){
					ex.printStackTrace();
				}try{
					Socket socket=new Socket("127.0.0.1",8899);
					BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println("addnewfriend");
					out.println(starting.name);
					out.println(friendname);
				}catch(Exception ex){
					
				}
			}else
				if(e.getActionCommand().equals("开始游戏")){
					roo=new room();
				}
			}
}


public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	if(e.getClickCount()==2){
	    JLabel l=(JLabel) e.getComponent();
	    faceImage i=(faceImage) l.getIcon();
	    imageLabel.setIcon(i);
	    String face=i.getI();
		try{
			Socket socket=new Socket("127.0.0.1",8899);
			BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println("change");
			out.println(starting.name);
			out.println(face);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
}
public class chat extends Thread{
	public chat(){
		try{
			 socket=new Socket("127.0.0.1",8899);
			read=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			write=new PrintWriter(new PrintWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			write.println("USER");
			write.println(starting.name);
			start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}public void run(){
		try{
			while(true){
				read=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				write=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				String message=read.readLine();
				System.out.println(message);
				if(message.equals("disappear")){
					JOptionPane.showMessageDialog(null,"好友已离线","错误",JOptionPane.WARNING_MESSAGE);
				}
				else
					if(message.equals("MSG")){
						String name=read.readLine();
				        String info=read.readLine();
				        System.out.println(info);
				        DateFormat df = DateFormat.getDateInstance();
				        String dateString = df.format(new Date());         
				        df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
				        String timeString = df.format(new Date());
				        messageList.appendInfo(timeString,name, info);
				        messageList.setVisible(true);}
					else
						if(message.equals("MSG:paint")){
							String name=read.readLine();
							String info=read.readLine();
							System.out.println(info);
							 DateFormat df = DateFormat.getDateInstance();
						        String dateString = df.format(new Date());         
						        df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
						        String timeString = df.format(new Date());
						        try{if(Drawguess.isOpen==true)
						        Drawguess.messagePanel.append(timeString+"\n"+name+":"+info+"\n");
						        else
						        drawTogether.messagePanel.append(timeString+"\n"+name+":"+info+"\n");}
						        catch(Exception e){
						        	e.printStackTrace();
						        }
							
						}
						else
							if(message.equals("start")){
								if(Drawguess.isOpen==true){
									T time=new T();
									time.s();
								}
							}
							else
								if(message.equals("closeroom")){
									JOptionPane.showMessageDialog(null,"房主已关闭了房间");
								}
								else
									if(message.equals("tell")){
										String name=read.readLine();
										String face=read.readLine();
										System.out.println("玩家上线:"+name+face);
									}
		}}catch(Exception e){
			e.printStackTrace();
		
	}}}

class MessageList extends JFrame{
	JTextArea area;
	public MessageList(){
		super("接受信息对话框");
		area=new JTextArea(300,250);
		JPanel panel=(JPanel) this.getContentPane();
		JScrollPane scroll=new JScrollPane(area);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scroll);
		setSize(300,250);
	}public void appendInfo(String date,String name,String info){
		area.append(date+"\n"+name+"给你发来消息："+info+"\n");
	}public void appendInvite(String date,String name){
		area.append(date+"\n"+name+"邀请你加入游戏");
	}
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
			Drawguess.time.setText(a);
			sleep(1000);
		}
		stop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}}
	




