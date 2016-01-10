package PaintingAndGuessing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class escListener implements AWTEventListener{

	@Override
	public void eventDispatched(AWTEvent event) {
		if(event.getClass()==KeyEvent.class){
			KeyEvent keyEvent=(KeyEvent)event;
			if(keyEvent.getID()==KeyEvent.KEY_PRESSED){
				keyPressed(keyEvent);
			}
		}
		
	}public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			new JDialogTest();
	}
	public class JDialogTest extends JDialog{

		JButton exitButton=new JButton("�˳���Ϸ");
		JButton helpButton=new JButton("����");
		JButton returnButton=new JButton("������Ϸ");
		public JDialogTest(){
			super(new JFrame(),null,true);
			Toolkit kit=Toolkit.getDefaultToolkit();
			Dimension screenSize=kit.getScreenSize();
			int width=screenSize.width;
			int height=screenSize.height;
			JPanel jp=new JPanel();
			exitButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int num=JOptionPane.showConfirmDialog(null,"ȷ��Ҫ�˳���Ϸ��");
					if(num==JOptionPane.YES_OPTION)
						System.exit(0);
					else
						setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				}
			});
			helpButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JOptionPane.showMessageDialog(null,"��Ϸ����\n1���Ƚ��뷿�䣬�ҵ����ѡ�Ҳ���Զ��˲μӡ�\n2:һ���˻�������������ʱ�����ƣ�\n3:������ʾ������");
				}
			});
			returnButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			jp.setLayout(new GridLayout(3,1,0,4));
			setBounds(width/5*2,height/5*2,width/5,height/3);
			jp.add(exitButton);
			jp.add(helpButton);
			jp.add(returnButton);
			setContentPane(jp);
			JPanel panel=(JPanel) this.getContentPane();
			setVisible(true);
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
	}
}
