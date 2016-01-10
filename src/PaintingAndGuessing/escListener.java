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

		JButton exitButton=new JButton("退出游戏");
		JButton helpButton=new JButton("帮助");
		JButton returnButton=new JButton("返回游戏");
		public JDialogTest(){
			super(new JFrame(),null,true);
			Toolkit kit=Toolkit.getDefaultToolkit();
			Dimension screenSize=kit.getScreenSize();
			int width=screenSize.width;
			int height=screenSize.height;
			JPanel jp=new JPanel();
			exitButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int num=JOptionPane.showConfirmDialog(null,"确定要退出游戏？");
					if(num==JOptionPane.YES_OPTION)
						System.exit(0);
					else
						setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				}
			});
			helpButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JOptionPane.showMessageDialog(null,"游戏规则：\n1：先进入房间，找到好友。也可以多人参加。\n2:一个人画，多人抢答（有时间限制）\n3:可以提示字数，");
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
