package PaintingAndGuessing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.*;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.net.*;
import java.io.*;
public class roomListener implements MouseListener{
	static int roomnumber=0;
	JLabel label;
	Vector<String> list=new Vector<String>();
public roomListener(JLabel labe){
	label=labe;
}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){
			try{
				Socket socket=new Socket("127.0.0.1",8899);
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("join");
				String name=label.getText();
				System.out.println(name);
				String room=name.substring(4,name.indexOf("玩家人数")).trim();
				System.out.println(room);
				roomnumber=Integer.parseInt(room);
				out.println(starting.name);
				out.println(room);
				String message=in.readLine();
				if(message.equals("playing")){
					JOptionPane.showMessageDialog(null,"该房间已经开始游戏了","加入错误",JOptionPane.WARNING_MESSAGE);
				}
				else
					if(message.equals("full")){
						JOptionPane.showMessageDialog(null,"该房间人数已满,请选择其他房间","加入错误",JOptionPane.WARNING_MESSAGE);
					}
					else
						if(message.equals("ok")){
							playGames.roo.setVisible(false);
							new Drawguess();
							
						
						}}catch(Exception ex){
				ex.printStackTrace();}}
		
		
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
		label.setBackground(Color.red);
		label.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		label.setBackground(Color.cyan);
		label.setForeground(Color.cyan);
	}

}
