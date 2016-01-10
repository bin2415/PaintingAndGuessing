package PaintingAndGuessing;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.*;
public class myMouseListener implements MouseListener,ActionListener{
	JLabel label;
	static String name;
public myMouseListener(JLabel l){
	label=l;
}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2){
			chatPanel p=new chatPanel();
			String a=label.getText();
			name=a.substring(0,a.length()-8);
			p.setTitle("正在和好友"+name+"聊天");
		}
		else{int mods=e.getModifiers();
			if((mods&InputEvent.BUTTON3_MASK)!=0){
				PopupMenu pop=new PopupMenu();
				MenuItem itemChat=new MenuItem("聊天");
				itemChat.addActionListener(this);
				MenuItem itemPlay=new MenuItem("邀请游戏");
				itemPlay.addActionListener(this);
				pop.add(itemChat);
				pop.add(itemPlay);
				playGames.jp.add(pop);
				pop.show(label,e.getX(),e.getY());
				name=label.getText().substring(0,label.getText().length()-8);
				
			}
		}
		
	}

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
		label.setForeground(Color.GREEN);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		label.setForeground(Color.RED);
	}public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("聊天")){
			new chatPanel();
		}
		else
			if(e.getActionCommand().equals("邀请游戏")){
				try{
				Socket socket=new Socket("127.0.0.1",8899);
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("MSG");
				out.println(starting.name);
				out.println(myMouseListener.name);
				out.println("一块来玩游戏吧");}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			else if(e.getActionCommand().equals("发送")){
			try{
			String message=chatPanel.chatField.getText();
			Socket socket=new Socket("127.0.0.1",8899);
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			String info=chatPanel.chatField.getText();
	        out.println("MSG");
	        out.println(starting.name);
	        out.println(myMouseListener.name.trim());
	        out.println(info);
	        DateFormat df = DateFormat.getDateInstance();// 获得DateFormat实例
	        String dateString = df.format(new Date());         // 格式化为日期
	        df = DateFormat.getTimeInstance(DateFormat.MEDIUM);// 获得DateFormat实例
	        String timeString = df.format(new Date());         // 格式化为时间
		    chatPanel.chatField.setText("");
		    chatPanel.chatArea.append(timeString+"\n"+message+"\n");
		   }
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		}
	}


