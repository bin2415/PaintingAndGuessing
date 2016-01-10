package PaintingAndGuessing;

public class person {
	String name,face;
	int score;
public person(String name,String face,int score){
     this.name=name;
     this.face=face;
     this.score=score;
}public String getName(){
	return name;
}public String getFace(){
	return face;
}public int getScore(){
	return score;
}
}
