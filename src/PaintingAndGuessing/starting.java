package PaintingAndGuessing;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.applet.*;
import java.net.*;
import java.io.*;

import javax.swing.*;

public class starting extends JFrame{
	protected int width,height;
 static String name,passWord;
 static String face;
	JMenu menu1;
	JButton jb1,jb2;
	JTextField jtf;
	JPasswordField jpw;
	JCheckBox checkBox;
	static starting s;
public starting(){
	super("�㻭�Ҳ�");
	Toolkit kit=Toolkit.getDefaultToolkit();
	Dimension screenSize=kit.getScreenSize();
	JPanel mlMain=new JPanel();
	mlMain.setLayout(null);
	 jb1=new JButton("��¼(L)");
	 jb2=new JButton("ע��(T)");
	 jb1.setBorderPainted(true);
	jb1.addActionListener(new ButtonListener());
	jb2.addActionListener(new ButtonListener());
	jb1.setToolTipText("�����¼");
	jb2.setToolTipText("���ע��");
	jb1.setMnemonic('L');
	jb2.setMnemonic('T');
	JLabel jl1=new JLabel("�û���");
	JLabel jl2=new JLabel("����");
	JMenu menu1=new JMenu("����");
	JMenu menu2=new JMenu("����");
	JMenuBar jb=new JMenuBar();
	jb.add(menu1);
	jb.add(menu2);
	setJMenuBar(jb);
	menu1.addMouseListener(new DotsListener());
	menu2.addMouseListener(new BListener());
	 width=(int)screenSize.getWidth();
	  height=(int)screenSize.getHeight();
	ImageIcon image=new ImageIcon("3240795.jpg");
	JLabel backImage=new JLabel(image);
	checkBox=new JCheckBox("��ʾ����");
	checkBox.addActionListener(new check());
	jtf=new JTextField(30);
	jpw=new JPasswordField(30);
	returnListener rListener=new returnListener(jpw);
	jtf.addKeyListener(rListener);
	enterLoin rListener1=new enterLoin(jb1);
	jpw.addKeyListener(rListener1);
	jpw.setEchoChar('*');
	backImage.setBounds(width/7*2,height/30,image.getIconWidth(),image.getIconHeight());
	jl1.setBounds(width/3,height/12*5,width/15,height/25);
	jtf.setBounds(width/12*5,height/12*5,width/4,height/25);
	jpw.setBounds(width/12*5,height/2,width/4,height/25);
	jl2.setBounds(width/3,height/2,width/15,height/25);
	checkBox.setBounds(width/3*2,height/2,width/15,height/25);
	jb1.setBounds(width/3,height/3*2,width/10,height/15);
	jb2.setBounds(width/24*15,height/3*2,width/10,height/15);
	Color c=new Color(54,67,99);
	kit.addAWTEventListener(new escListener(),AWTEvent.KEY_EVENT_MASK);
	mlMain.add(backImage);
	mlMain.add(jl1);
	mlMain.add(jtf);
	mlMain.add(jpw);
	mlMain.add(jl2);
	mlMain.add(checkBox);
	mlMain.add(jb1);
	mlMain.add(jb2);
	getContentPane().add(mlMain);
	setSize(width,height);
	setVisible(true);
	checkBox.setBackground(c);
	mlMain.setBackground(c);
	addWindowListener(new windowDestroyer());
	
}
//���ڶԻ���
public class DotsListener implements MouseListener{
	public void mouseClicked(MouseEvent e){
		
		JOptionPane.showMessageDialog(null,"��PCB����");	
	}public void mousePressed(MouseEvent e){};
	public void mouseReleased(MouseEvent e){};
	public void mouseEntered(MouseEvent e){};
	public void mouseExited(MouseEvent e){}		
}public class BListener implements MouseListener{
	public void mouseClicked(MouseEvent e){
		JOptionPane.showMessageDialog(null,"��Ϸ����\n1���Ƚ��뷿�䣬�ҵ����ѡ�Ҳ���Զ��˲μӡ�\n2:һ���˻�������������ʱ�����ƣ�\n3:������ʾ������");
	}public void mousePressed(MouseEvent e){};
	public void mouseReleased(MouseEvent e){};
	public void mouseEntered(MouseEvent e){};
	public void mouseExited(MouseEvent e){}	}	
	
//���ڹر���ʾ������
public class windowDestroyer extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		int num=JOptionPane.showConfirmDialog(null,"ȷ��Ҫ�˳���Ϸ��");
		if(num==JOptionPane.YES_OPTION)
			System.exit(0);
		else
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

}//��½��ע�������
public class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand()=="ע��(T)"){
				Register re=new Register();
				re.register();
		}else{ name=jtf.getText();
		char[] a=jpw.getPassword();
		String pass="";
			for(int i=0;i<a.length;i++)
				pass+=a[i];
			try{
			    Socket socket=new Socket("127.0.0.1",8899);
			    BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			    out.println("login");
			    out.println(name);
			    out.println(pass);
	            String msg=in.readLine();
				if(msg.equals("ok")){
					face=in.readLine();
					playGames play=new playGames();
					s.dispose();
					
				}
				else
					if(msg.equals("false")){
					JOptionPane.showMessageDialog(null,"�û��������ڻ��������","��½����",JOptionPane.ERROR_MESSAGE);
				
				}in.close();
				out.close();
				socket.close();
				}  
			catch(Exception err){
					err.printStackTrace();	
			}
			
		}
	}	
	}public class check implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(checkBox.isSelected())
				jpw.setEchoChar((char) 0);
			else
				jpw.setEchoChar('*');
		}
	}
public static void main(String args[]){
	s=new starting();
}
	}
	



