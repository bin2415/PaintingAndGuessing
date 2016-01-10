package PaintingAndGuessing;

import java.awt.event.*;

import javax.swing.*;
public class enterLoin implements KeyListener{
JButton button;
public enterLoin(JButton b){
	button=b;
}
public void keyPressed(KeyEvent e){
	if(e.getKeyCode()==KeyEvent.VK_ENTER)
		button.doClick();
}public void keyReleased(KeyEvent e){}
@Override
public void keyTyped(KeyEvent e) {
}

}
