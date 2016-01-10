package PaintingAndGuessing;
import javax.swing.*;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.*;
import java.util.Vector;
public class receiveDrawPanel extends JPanel implements Runnable{
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	int mode=0;
	 int flagtool=0; 
	 Color flagcolor; 
	 int border; 
	 boolean is=true;
	 int isEraser=0;
	 BasicStroke size; 
	 private Point2D[] p=new Point2D[3];
	 onePoint p1,p2; 
	 Vector<onePoint> points=new Vector<onePoint>(); 
	public receiveDrawPanel(){
		try{
		socket=new Socket("127.0.0.1",8866);
		in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		out.println("USER");
		out.println(starting.name);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void paint(Graphics g)
	{ 
	 Graphics2D g2d=(Graphics2D)g; 
	 if(is){
		 if(true){
			 g.clearRect(0,0,getSize().width,getSize().height);
		 }
	 }
	 if(flagtool==2)
	 { //清除
		
	  g.clearRect(0,0,getSize().width,getSize().height); 
	 }
	 for(int i=0;i<points.size()-1;i++)
	 { 
	  p1=(onePoint)points.elementAt(i); 
	  p2=(onePoint)points.elementAt(i+1); 
	  
	  g2d.setColor(p1.c); //////////////需要使用Graphics2D从Graphics类中继承下来的方法 setColor（）设置当前的颜色 
	  size=new BasicStroke(p1.border,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND); 
	  g2d.setStroke(size);
	  if(p1.tool==p2.tool)
	  { 
	   switch(p1.tool)
	    { 
	    case 0: 
	     Line2D.Double line1=new Line2D.Double(p1.x,p1.y,p2.x,p2.y); 
	     g2d.draw(line1); 
	     break; 
	    case 1: 
	     Line2D.Double line2=new Line2D.Double(p1.x,p1.y,p2.x,p2.y); 
	     g2d.draw(line2); 
	     break; 
	    case 3: 
	     Ellipse2D.Double ellipse=new Ellipse2D.Double(p1.x,p1.y,Math.abs(p2.x-p1.x),Math.abs(p2.y-p1.y)); 
	     g2d.draw(ellipse); 
	     break; 
	    case 4: 
	     Rectangle2D.Double rect=new Rectangle2D.Double(p1.x,p1.y,Math.abs(p2.x-p1.x),Math.abs(p2.y-p1.y)); 
	     g2d.draw(rect); 
	     break; 
	    default: 
	   }
	  } 
	 } 
	} 
public void run(){
	try{
	while(true){
		String message=in.readLine();
		
		if(message.equals("mousePressed")){
			try{
			String x=in.readLine();
			String y=in.readLine();
			String flag=in.readLine();
			String re=in.readLine();
			String gree=in.readLine();
			String blac=in.readLine();
			String size=in.readLine();
			int x_=Integer.parseInt(x);
			int y_=Integer.parseInt(y);
			int red=Integer.parseInt(re);
			int green=Integer.parseInt(gree);
			int black=Integer.parseInt(blac);
		    flagtool=Integer.parseInt(flag);
		    border=Integer.parseInt(size);
		    flagcolor=new Color(red,green,black);
		    onePoint pp1=new onePoint(x_,y_,flagtool,flagcolor,border);
		    points.add(pp1);
		    repaint();}
		catch(Exception ex){
			ex.getSuppressed();
		}
		}
		else
			if(message.equals("mouseReleased")){
				String isPen=in.readLine();
				if(isPen.equals("isPen")){
					 if(isEraser==0)
						  points.addElement(new onePoint(-1,-1,22,flagcolor,border)); 
						 else if(isEraser==1)
							 points.addElement(new onePoint(-1,-1,22,Color.WHITE,6*2));
				}else
				{
					String x=in.readLine();
					String y=in.readLine();
					String flag=in.readLine();
					String re=in.readLine();
					String gree=in.readLine();
					String blac=in.readLine();
					String size=in.readLine();
					try{
					int x_=Integer.parseInt(x);
					int y_=Integer.parseInt(y);
					int red=Integer.parseInt(re);
					int green=Integer.parseInt(gree);
					int black=Integer.parseInt(blac);
				    flagtool=Integer.parseInt(flag);
				    border=Integer.parseInt(size);
				    flagcolor=new Color(red,green,black);
				    onePoint pp1=new onePoint(x_,y_,flagtool,flagcolor,border);
				    points.addElement(pp1);
				    points.add(new onePoint(-1,-1,22,flagcolor,border));
				    repaint();}
					catch(NumberFormatException e){
						e.getSuppressed();
					}
				}
			}
			else
				if(message.equals("itemStateChanged")){
					String item=in.readLine();
					if(item.equals("colorchoice")){
						String color=in.readLine();
						if(color.equals("black"))
							flagcolor=new Color(0,0,0);
						else
							if(color.equals("red"))
								flagcolor=new Color(255,0,0);
							else 
								if(color.equals("blue"))
									flagcolor=new Color(0,0,255);
								else
									if(color.equals("green"))
										flagcolor=new Color(0,255,0);
						
					}else
						if(item.equals("sizechoice")){
							String size=in.readLine();
							if(size.equals("1")){
						     border=1;
							}
							else
								if(size.equals("2"))
									border=2*2;
								else
									if(size.equals("4"))
										border=4*2;
									else 
										if(size.equals("6"))
										border=6*2;
										else 
											if(size.equals("8"))
												border=8*2;
						}
				}
				else
					if(message.equals("actionPerformed")){
						String ispen=in.readLine();
						if(ispen.equals("pen")){
							flagtool=0;
							isEraser=0;
						}
						else
							if(ispen.equals("line")){
								flagtool=1;
							}
							else if(ispen.equals("clear")){
								flagtool=2; 
								  points.removeAllElements(); 
								  repaint(); 
							}
							else
								if(ispen.equals("ellipse")){
									flagtool=3;
								}
								else
									if(ispen.equals("rect")){
										flagtool=4;
									}
									else
										if(ispen.equals("colorboard")){
											try{
											String red=in.readLine();
											String green=in.readLine();
											String blue=in.readLine();
											flagcolor=new Color(Integer.parseInt(red),Integer.parseInt(green),Integer.parseInt(blue));
											
										}catch(Exception e){
											e.getSuppressed();}
										}
										else
											if(ispen.equals("eraser")){
												 flagtool=0;
												 isEraser=1;
											}
					}
					else
						if(message.equals("mouseDragged")){
							String a=in.readLine();
							if(a.equals("0")){
								try{
								String x=in.readLine();
								String y=in.readLine();
								String flag=in.readLine();
								String red=in.readLine();
								String green=in.readLine();
								String blue=in.readLine();
						        String bord=in.readLine();
						        int x_=Integer.parseInt(x);
						        int y_=Integer.parseInt(y);
						        flagtool=Integer.parseInt(flag);
						        flagcolor=new Color(Integer.parseInt(red),Integer.parseInt(green),Integer.parseInt(blue));
						        border=Integer.parseInt(bord);
						        onePoint pp=new onePoint(x_,y_,flagtool,flagcolor,border);
						        points.addElement(pp);
						        	repaint();}
								catch(Exception e){
									e.getSuppressed();
								}
							}
							else
								if(a.equals("1")){
									try{
									String x=in.readLine();
									String y=in.readLine();
									String flag=in.readLine();
									String red=in.readLine();
									String green=in.readLine();
									String blue=in.readLine();
							        String bord=in.readLine();
							        int x_=Integer.parseInt(x);
							        int y_=Integer.parseInt(y);
							        flagtool=Integer.parseInt(flag);
							        flagcolor=new Color(Integer.parseInt(red),Integer.parseInt(green),Integer.parseInt(blue));
							        border=Integer.parseInt(bord);
							        onePoint pp=new onePoint(x_,y_,flagtool,flagcolor,border);
							        points.add(pp);
							        repaint();}
									catch(Exception e){
										e.getSuppressed();
									}
								}
						}
	}}catch(Exception e){
		e.printStackTrace();}
	
}
}
