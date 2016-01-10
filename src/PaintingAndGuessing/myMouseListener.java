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
			p.setTitle("���ںͺ���"+name+"����");
		}
		else{int mods=e.getModifiers();
			if((mods&InputEvent.BUTTON3_MASK)!=0){
				PopupMenu pop=new PopupMenu();
				MenuItem itemChat=new MenuItem("����");
				itemChat.addActionListener(this);
				MenuItem itemPlay=new MenuItem("������Ϸ");
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
		if(e.getActionCommand().equals("����")){
			new chatPanel();
		}
		else
			if(e.getActionCommand().equals("������Ϸ")){
				try{
				Socket socket=new Socket("127.0.0.1",8899);
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("MSG");
				out.println(starting.name);
				out.println(myMouseListener.name);
				out.println("һ��������Ϸ��");}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			else if(e.getActionCommand().equals("����")){
			try{
			String message=chatPanel.chatField.getText();
			Socket socket=new Socket("127.0.0.1",8899);
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			String info=chatPanel.chatField.getText();
	        out.println("MSG");
	        out.println(starting.name);
	        out.println(myMouseListener.name.trim());
	        out.println(info);
	        DateFormat df = DateFormat.getDateInstance();// ���DateFormatʵ��
	        String dateString = df.format(new Date());         // ��ʽ��Ϊ����
	        df = DateFormat.getTimeInstance(DateFormat.MEDIUM);// ���DateFormatʵ��
	        String timeString = df.format(new Date());         // ��ʽ��Ϊʱ��
		    chatPanel.chatField.setText("");
		    chatPanel.chatArea.append(timeString+"\n"+message+"\n");
		   }
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		}
	}


