package PaintingAndGuessing;

import javax.swing.ImageIcon;

public class faceImage extends ImageIcon{
	String i;
public faceImage(String str,String j){
	super(str);
	i=j;
}public String getI(){
	return i;
}
}
