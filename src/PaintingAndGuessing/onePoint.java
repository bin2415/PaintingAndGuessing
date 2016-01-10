package PaintingAndGuessing;

import java.awt.*; 
import java.net.*;
import java.awt.event.*; 
import java.awt.geom.*; 
import java.io.*; 
import java.util.*; 
import javax.swing.*; 

//the point 
//impress the info of one point,the x and y 

class onePoint implements Serializable
{ 
 int x; 
 int y; 
 int tool; 
 Color c; 
 int border; 
 public onePoint(int x,int y,int tool,Color cc,int border)
 { 
  this.x=x; 
  this.y=y; 
  this.tool=tool; 
  this.c=cc; 
  this.border=border; 
 }
}

class DrawingBoard extends JPanel implements MouseListener,ItemListener,ActionListener,MouseMotionListener
{
 Button pen,ellipse,rect,clear,colorboard,eraser;  
 Choice sizechoice,colorchoice ;  
 Label pensize, pencolor; 
 Panel panel ;  
 int mode=0;
 int flagtool=0; 
 Color flagcolor=Color.BLACK; 
 int border; 
 boolean is=true;
 int isEraser=0;
 BasicStroke size; 
 private Point2D[] p=new Point2D[3];;
 
 onePoint p1,p2; 
 Vector<onePoint> points=new Vector<onePoint>(); 
public DrawingBoard()
{ 
	try{
		Socket socket=new Socket("127.0.0.1",8866);
		PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		out.println("USER");
		out.println(starting.name);
		System.out.println(starting.name);
		out.close();
		socket.close();
	}catch(Exception e){
		e.printStackTrace();
	}
 pen=new Button("����"); 
 ellipse=new Button("Բ"); 
 rect=new Button("����"); 
 clear=new Button("���"); 
 colorboard=new Button("��ɫ��");
 eraser=new Button("��Ƥ");
 pensize=new Label("���ʴ�С"); 
 pencolor=new Label("������ɫ");
 sizechoice=new Choice(); 
 sizechoice.add("1"); 
 sizechoice.add("2"); 
 sizechoice.add("4"); 
 sizechoice.add("6"); 
 sizechoice.add("8"); 
 sizechoice.addItemListener(this); 
 
 colorchoice=new Choice(); 
 colorchoice.add("black"); 
 colorchoice.add("red"); 
 colorchoice.add("blue"); 
 colorchoice.add("green"); 
 colorchoice.addItemListener(this); 
 
 pen.addActionListener(this); 
 ellipse.addActionListener(this); 
 rect.addActionListener(this); 
 clear.addActionListener(this); 
 colorboard.addActionListener(this); 
 eraser.addActionListener(this);;
 panel=new Panel(); 
 
 
 panel.add(pen); 
 panel.add(ellipse); 
 panel.add(rect); 
 panel.add(clear); 
 
 panel.add(sizechoice); 
 panel.add(pensize); 
 
 panel.add(colorchoice); 
 panel.add(pencolor); 
 panel.add(colorboard); 
 panel.add(eraser);
 
 add(panel,BorderLayout.NORTH); 
 setBounds(100,100,700,600); 
 setVisible(true); 
 
 /** 
 * �������¼��ļ����������������ƶ��͵�������޷�ʶ�� 
 * */ 
 addMouseListener(this); 
 addMouseMotionListener(this); 
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
 { //���
	
  g.clearRect(0,0,getSize().width,getSize().height); 
 }
 for(int i=0;i<points.size()-1;i++)
 { 
  p1=(onePoint)points.elementAt(i); 
  p2=(onePoint)points.elementAt(i+1); 
  
  g2d.setColor(p1.c); //////////////��Ҫʹ��Graphics2D��Graphics���м̳������ķ��� setColor�������õ�ǰ����ɫ 
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
public void mouseClicked(MouseEvent e) {} 
public void mouseEntered(MouseEvent e) {} 
public void mouseExited(MouseEvent e) {} 
public void mousePressed(MouseEvent e) //������ʱ�򣬽���ǰ�ĵ���Ϣ��¼ 
{
 mode=0;
 p[0]=e.getPoint();
 onePoint pp1=new onePoint(e.getX(),e.getY(),flagtool,flagcolor,border);
 points.addElement(pp1);
 repaint();
 try{
 Socket socket=new Socket("127.0.0.1",8866);
 PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
 out.println("mousePressed");
 int nm=drawTogether.list.size();
 out.println(nm);
 for(int i=0;i<nm;i++){
	 out.println(drawTogether.list.get(i));
 }
 out.println(e.getX());
 out.println(e.getY());
 out.println(flagtool);
 out.println(flagcolor.getRed());
 out.println(flagcolor.getGreen());
 out.println(flagcolor.getBlue());
 out.println(border);
 out.close();
 socket.close();}
 catch(Exception ex){
	 ex.printStackTrace();
 }
 
} 
public void mouseReleased(MouseEvent e) //����ɿ�ʱ������ǻ��ʣ���ǰ�ضϣ�������״̬����һö����Ϣ 
{try{ 
	Socket socket=new Socket("127.0.0.1",8866);
	 PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	 out.println("mouseReleased");
	 int nm=drawTogether.list.size();
	 out.println(nm);
	 for(int i=0;i<nm;i++){
		 out.println(drawTogether.list.get(i));
	 }
 mode=1;
 if(flagtool==0)
 {   out.println("isPen");
	 if(isEraser==0){
		 out.println("notEraser");
  points.addElement(new onePoint(-1,-1,22,flagcolor,border)); }
 else if(isEraser==1){
	 out.println("isEraser");
	 points.addElement(new onePoint(-1,-1,22,Color.WHITE,6*2));}
 } 
 else
 { out.println("notPen");
  onePoint pp2=new onePoint(e.getX(),e.getY(),flagtool,flagcolor,border);
  out.println(e.getX());
  out.println(e.getY());
  out.println(flagtool);
  out.println(flagcolor.getRed());
  out.println(flagcolor.getGreen());
  out.println(flagcolor.getBlue());
  out.println(border);
  points.addElement(pp2);
  points.add(new onePoint(-1,-1,22,flagcolor,border));
 }
 out.close();
 socket.close();
 repaint(); }
catch(Exception ex){
	ex.printStackTrace();
}
}

public void itemStateChanged(ItemEvent e) 
{ try{
	Socket socket=new Socket("127.0.0.1",8866);
      PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
      out.println("itemStateChanged");
      int nm=drawTogether.list.size();
      out.println(nm);
      for(int i=0;i<nm;i++){
     	 out.println(drawTogether.list.get(i));
      }
 if(e.getSource()==colorchoice)
 { out.println("colorchoice");
  String selected=colorchoice.getSelectedItem(); 
  if(selected=="black"){
	  out.println("balck");
	  flagcolor=new Color(0,0,0);} 
  else if(selected=="red"){
	  out.println("red");
	  flagcolor=new Color(255,0,0); } 
  else if(selected=="blue"){
	  out.println("blue");
	  flagcolor=new Color(0,0,255);} 
  else if(selected=="green"){
	  out.println("green");
	  flagcolor=new Color(0,255,0); } 
 } 
 else if(e.getSource()==sizechoice)
 { out.println("sizechoice");
  String selected=sizechoice.getSelectedItem(); 
  if (selected=="1"){ 
	  out.println("1");
	  border=1; } 
  else if(selected=="2"){ 
	  out.println("2");
	  border=2*2; } 
  else if(selected=="4"){ 
	  out.println("4");
	  border=4*2; } 
  else if(selected=="6"){
	  out.println("6");
	  border=6*2; } 
  else if(selected=="8"){ 
	  out.println("8");
	  border=8*2; } 
 } 
 out.close();
 socket.close();}catch(Exception ex){
	 ex.printStackTrace();
 }
} 
/*public void update(Graphics g) { //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 
paint(g); 
} */
public void actionPerformed(ActionEvent e)
{ try{
	Socket socket=new Socket("127.0.0.1",8866);
	PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
// TODO Auto-generated method stub 
	out.println("actionPerformed");
	int nm=drawTogether.list.size();
	 out.println(nm);
	 for(int i=0;i<nm;i++){
		 out.println(drawTogether.list.get(i));
	 }
 if(e.getSource()==pen){flagtool=0; isEraser=0;
 out.println("pen");} 
 else if(e.getActionCommand().equals("ֱ��")){ flagtool=1;
 out.println("line");} 
 else if(e.getSource()==clear)
 { out.println("clear");
  flagtool=2; 
  points.removeAllElements(); 
  repaint(); //����Ҫ�У���������޷�ɾ���� 
 } 
 else if(e.getSource()==ellipse){ 
	 out.println("ellipse");
	 flagtool=3; } 
 else if(e.getSource()==rect){ 
	 out.println("rect");
	 flagtool=4; } 
 else if(e.getSource()==colorboard)
 { out.println("colorboard");
  /* 
  * ʹ�� javax.swing.�����е� JColorChooser ��ľ�̬����showDialog��Component component��String title��Color color ���� 
  * �÷����Ĳ�����component�ǵ�ǰ��ʾ�Ի���ĸ���ܣ�color�����õ�ɫ���ʼ�ı�ѡ��ɫ 
  * 
  * �÷������ر�ѡ����ɫ������ΪColor 
  * */ 
  Color color=JColorChooser.showDialog(this, "��ɫ��",flagcolor); 
  out.println(color.getRed());
  out.println(color.getGreen());
  out.println(color.getBlue());
  flagcolor=color; 
 } 
 else
	 if(e.getSource()==eraser){
		 out.println("eraser");
		 flagtool=0;
		 isEraser=1;
	 }
 out.close();
 socket.close();
 }catch(Exception ex){
		 ex.printStackTrace();
	 }
 
  
 
} 

public void mouseDragged(MouseEvent e) //����϶�ʱ��//���ҽ��� flagtool����0�����߱�ʾΪ��Ƥ��ʱ�� 
//�Ž��϶��������漰���ĵ�ȫ����¼���������ҵ���repain()�������ػ���ǰ 
// TODO Auto-generated method stub 
{try{
	Socket socket=new Socket("127.0.0.1",8866);
	PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	out.println("mouseDragged");
	int nm=drawTogether.list.size();
	 out.println(nm);
	 for(int i=0;i<nm;i++){
		 out.println(drawTogether.list.get(i));
	 }
 if(flagtool==0)
 { out.println("0");
	 if(isEraser==0){
  onePoint pp3=new onePoint(e.getX(),e.getY(),flagtool,flagcolor,border); 
  out.println(e.getX());
  out.println(e.getY());
  out.println(flagtool);
  out.println(flagcolor.getRed());
  out.println(flagcolor.getGreen());
  out.println(flagcolor.getBlue());
  out.println(border);
  points.addElement(pp3); 
  repaint(); }
 else{
	onePoint pp3=new onePoint(e.getX(),e.getY(),flagtool,Color.WHITE,8*4);
	out.println(e.getX());
	out.println(e.getY());
	out.println(flagtool);
	out.println(Color.WHITE.getRed());
	out.println(Color.WHITE.getGreen());
	out.println(Color.WHITE.getBlue());
	out.println(8*4);
	points.addElement(pp3);
	repaint();
 }
 } 
 if(flagtool==1)
 {out.println("1");
  onePoint pp3=new onePoint(e.getX(),e.getY(),flagtool,flagcolor,border); 
  out.println(e.getX());
  out.println(e.getY());
  out.println(flagtool);
  out.println(flagcolor.getRed());
  out.println(flagcolor.getGreen());
  out.println(flagcolor.getBlue());
  out.println(border);
  points.add(pp3);
  repaint();
 }out.close();
 socket.close();
 }catch(Exception ex){
	 ex.printStackTrace();
 }
} 

public void mouseMoved(MouseEvent e) { 
// TODO Auto-generated method stub 
}
} 