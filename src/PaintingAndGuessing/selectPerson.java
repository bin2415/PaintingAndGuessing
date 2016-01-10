package PaintingAndGuessing;
import java.net.*;
import java.io.*;
public class selectPerson {
	String name;
	person p;
public selectPerson(String name){
	this.name=name;		
	}
public person select(){
	try{
		Socket socket=new Socket("127.0.0.1",8899);
		BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		out.println("person");
		out.println(name);
		String face=in.readLine();
		String s=in.readLine();
		in.close();
		out.close();
		//socket.close();
		String faceImage=face.substring(name.length(),face.length());
		p=new person(name,faceImage,Integer.parseInt(s));
	}catch(Exception e){
		e.printStackTrace();
	}
	return p;
}
}
