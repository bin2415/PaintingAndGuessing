package PaintingAndGuessing;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JPasswordField;
public class returnListener implements KeyListener{
	  Component com;
	public returnListener(Component jpw){
		com=jpw;
	}
public void keyPressed(KeyEvent event){
	if(event.getKeyCode()==KeyEvent.VK_ENTER)
		com.requestFocus();
	
}
@Override
public void keyTyped(KeyEvent e) {
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
}
}
