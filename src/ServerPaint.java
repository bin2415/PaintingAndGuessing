import java.io.*;
import java.net.*;

import javax.swing.*;

import java.util.*;
public class ServerPaint {
private ServerSocket server;
private BufferedReader in;
private PrintWriter out;
private Hashtable<String,Socket> map=new Hashtable<String,Socket>();
public ServerPaint() throws IOException{
	server=new ServerSocket(8866);
	System.out.println("画板服务器succeed");
	while(true){
		try{
			Socket socket=server.accept();
			new creatSocket(socket);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
public class creatSocket extends Thread{
	Socket socket;
	public creatSocket(Socket so){
		socket=so;
		try{in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))),true);
		start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void run(){
		try{
		while(true){
			String str=in.readLine();
			System.out.println(str);
			if(str.equals("USER")){
				String key=in.readLine();
				map.put(key,socket);
			}
			else
				if(str.equals("EXIT")){
					String key=in.readLine();
					map.remove(key);
				}
			if(str.equals("mousePressed")){
				int num=Integer.parseInt(in.readLine().trim());
				System.out.println("玩家人数："+num);
				String[] list=new String[num];
				for(int i=0;i<num;i++){
					String name=in.readLine();
					list[i]=name;
				}
				String x=in.readLine();
				String y=in.readLine();
				String flag=in.readLine();
				String R=in.readLine();
				String G=in.readLine();
				String B=in.readLine();
				String bord=in.readLine();
				for(int i=0;i<num;i++){
					String name=list[i];
					Set<String> set=map.keySet();
					Iterator<String> iterator=set.iterator();
				while(iterator.hasNext()){
					String key=iterator.next();
					if(key.equals(name)){
						System.out.println(name);
						Socket so=map.get(key);
						PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
						out1.println("mousePressed");
						out1.println(x);
						out1.println(y);
						out1.println(flag);
						out1.println(R);
						out1.println(G);
						out1.println(B);
						out1.println(bord);
					}
				}}
				
			}else
				if(str.equals("mouseReleased")){
					int num=Integer.parseInt(in.readLine().trim());
					String[] list=new String[num];
					for(int i=0;i<num;i++){
						String name=in.readLine();
						list[i]=name;
					}
					String ispen=in.readLine();
					if(ispen.equals("isPen")){
						String iseraser=in.readLine();
					   
					    for(int i=0;i<num;i++){
					    	String name=list[i];
					    	 Set<String> set=map.keySet();
							    Iterator<String> iterator=set.iterator();
					    while(iterator.hasNext()){
					    	String key=iterator.next();
					    	if(key.equals(name)){
					    		System.out.println(name);
					    		Socket so=map.get(key);
								PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
								out1.println("mouseReleased");
								out1.println("isPen");
					    	}
					    }}
					}
					else
					{
						String x=in.readLine();
						String y=in.readLine();
						String flag=in.readLine();
						String R=in.readLine();
						String G=in.readLine();
						String B=in.readLine();
						String bord=in.readLine();
						
					    for(int i=0;i<num;i++){
					    	String name=list[i];
					    	Set<String> set=map.keySet();
						    Iterator<String> iterator=set.iterator();
					    while(iterator.hasNext()){
					    	String key=iterator.next();
					    	if(key.equals(name)){
					    		System.out.println(name);
					    		Socket so=map.get(key);
								PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
								out1.println("mouseReleased");
								out1.println("notPen");
								out1.println(x);
								out1.println(y);
								out1.println(flag);
								out1.println(R);
								out1.println(G);
								out1.println(B);
								out1.println(bord);	
					    }}}
					}
				}
				else
					if(str.equals("itemStateChanged")){
						int num=Integer.parseInt(in.readLine().trim());
						String[] list=new String[num];
						for(int i=0;i<num;i++){
							String name=in.readLine();
							list[i]=name;
						}
						String isColor=in.readLine();
				        String Color=in.readLine();
				        
					    for(int i=0;i<num;i++){
					    	String name=list[i];
					    	Set<String> set=map.keySet();
						    Iterator<String> iterator=set.iterator();
					    while(iterator.hasNext()){
					    	String key=iterator.next();
					    	if(key.equals(name)){
					    		System.out.println(name);
					    		Socket so=map.get(key);
								PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
								out1.println("itemStateChanged");
								out1.println(isColor);
								out1.println(Color);
					    	}}
					    	
					    }
					}
					else
						if(str.equals("actionPerformed")){
							int num=Integer.parseInt(in.readLine().trim());
							String[] list=new String[num];
							for(int i=0;i<num;i++){
								String name=in.readLine();
								list[i]=name;
							}
							String isPen=in.readLine();
								if(isPen.equals("colorboard")){
									String red=in.readLine();
									String green=in.readLine();
									String blue=in.readLine();
									    for(int i=0;i<num;i++){
									    	String name=list[i];
									    	 Set<String> set=map.keySet();
											    Iterator<String> iterator=set.iterator();
									    while(iterator.hasNext()){
									    	String key=iterator.next();
									    	if(key.equals(name)){
									    		System.out.println(name);
									    		Socket so=map.get(key);
									    		PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
									    		out1.println("actionPerformed");
									    		out1.println("colorboard");
									    		out1.println(red);
									    		out1.println(green);
									    		out1.println(blue);
									    	}}
									    }
								}
								else
								{ 
										 
										    for(int i=0;i<num;i++){
										    	Set<String> set=map.keySet();
											    Iterator<String> iterator=set.iterator();
										    	String name=list[i];
										    while(iterator.hasNext()){
										    	String key=iterator.next();
										    	if(key.equals(name)){
										    		System.out.println(name);
										    		Socket so=map.get(key);
										    		PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
										    		out1.println("actionPerformed");
										    		out1.println(isPen);
										    	}}
										    }
									}
						}
						else
							if(str.equals("mouseDragged")){
								int num=Integer.parseInt(in.readLine().trim());
								String[] list=new String[num];
								for(int i=0;i<num;i++){
									String name=in.readLine();
									list[i]=name;
								}
								String flagtool=in.readLine();
									String x=in.readLine();
									String y=in.readLine();
									String flag=in.readLine();
									String red=in.readLine();
									String green=in.readLine();
									String blue=in.readLine();
							        String border=in.readLine();
							       	for(int i=0;i<num;i++){
							       		Set<String> set=map.keySet();
							     		Iterator<String> iterator=set.iterator();
								    	String name=list[i];
								    while(iterator.hasNext()){
								    String key=iterator.next();
								    if(key.equals(name)){
								    	System.out.println(name);
								    	Socket so=map.get(key);
								    	PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(so.getOutputStream()))),true);
								    	out1.println("mouseDragged");
								    	out1.println(flagtool);
								    	out1.println(x);
								    	out1.println(y);
								    	out1.println(flag);
								    	out1.println(red);
								    	out1.println(green);
								    	out1.println(blue);
								    	out1.println(border);
								    }}
								    }
								
								
							}
		}}catch(Exception e){
			e.printStackTrace();}
		
	}
}public static void main(String args[]) throws IOException{
	new ServerPaint();
}
}
