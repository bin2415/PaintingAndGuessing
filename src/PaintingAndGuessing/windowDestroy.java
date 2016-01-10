package PaintingAndGuessing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.*;
import java.net.*;

public class windowDestroy extends WindowAdapter{
	JFrame frame;
	public windowDestroy(JFrame frame){
	this.frame=frame;
	}

	public void windowClosing(WindowEvent e){
		int num=JOptionPane.showConfirmDialog(null,"确定要退出游戏？");
		if(num==JOptionPane.YES_OPTION){
			
			try{
				Socket socket=new Socket("127.0.0.1",8899);
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				out.println("logout");
				out.println(starting.name);
				out.close();
				in.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}System.exit(0);
			}
		else
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}}