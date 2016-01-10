
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
public class ServerThread{
	private  ServerSocket server;
private BufferedReader in;
private PrintWriter out;
private Hashtable<String,Socket> map=new Hashtable<String,Socket>();
private Hashtable<String,String> map1=new Hashtable<String,String>();
public  ServerThread() throws IOException{
	server=new ServerSocket(8899);
    System.out.println("聊天服务器succeed");
	while(true){
		try{
		Socket socket=server.accept();
		new creatThread(socket);}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
class creatThread extends Thread{
	Socket socket;
	public creatThread(Socket so){
		socket=so;
		try{
		in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		start();}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}public void run(){
		try{
			while(true){String str=in.readLine();
				System.out.println(str);
				if(str.equals("end"))
					break;
				else
						if(str.equals("login")){
						try{boolean is=true;
							Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
							Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
						    String sql="SELECT PassWord,头像  FROM player WHERE UserName=?";
							String textName=in.readLine();
							String textPass=in.readLine().trim();
							String g=textName;
							PreparedStatement prepare=coon.prepareCall(sql);
							prepare.clearParameters();
							prepare.setString(1, g);
							ResultSet r=prepare.executeQuery();
							if(r.next()){
								is=false;
								String pass=r.getString("PassWord").trim();
								if(textPass.regionMatches(0,pass,0,pass.length())){
									out.println("ok");
									String face=r.getString("头像").trim();
									out.println(face);
								String setIp="UPDATE player SET IP=? WHERE UserName=?";
								PreparedStatement prest=coon.prepareCall(setIp);
								prest.clearParameters();
								prest.setString(1,socket.getInetAddress().getHostAddress());
								prest.setString(2, g);
								int set=prest.executeUpdate();
								String upDate="UPDATE player SET 在线=1 WHERE UserName=?";
								PreparedStatement prest2=coon.prepareCall(upDate);
								prest2.clearParameters();
								prest2.setString(1, g);
								int set2=prest2.executeUpdate();
							}else
							{
								out.println("false");
							r.close();
							coon.close();
							
							}
							}else{
								out.println("false");
								r.close();
								coon.close();
							}
								}
							catch(Exception e){
							
							out.println("false");
							e.printStackTrace();}
							
						}
					
					    else{
						if(str.equals("new")){
							try{
								boolean is=true;
							Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
							Connection coon2=DriverManager.getConnection("jdbc:odbc:Register","","");
							String newsql="INSERT INTO player (UserName,PassWord,E_Mail,头像) VALUES(?,?,?,?)";
							String exist="SELECT * FROM player";
							String nickname=in.readLine().trim();
							String password=in.readLine().trim();
							String e_mail=in.readLine().trim();
							String faceImage=nickname+"头像1.jpg";
							Statement stat=coon2.createStatement();
							ResultSet r=stat.executeQuery(exist);
							while(r.next()){
								String a=r.getString(1);
								if(a.equals(nickname)){
									out.println("exist");
									is=false;
									break;
								}
							}if(is){
							
							PreparedStatement prepare2=coon2.prepareCall(newsql);
							prepare2.clearParameters();
							prepare2.setString(1, nickname);
							prepare2.setString(2,password);
							prepare2.setString(3, e_mail);
							prepare2.setString(4, faceImage);
							int r3=prepare2.executeUpdate();
							out.println("ok");}
							coon2.close();
							socket.close();}
							catch(Exception e){
								e.printStackTrace();
							}
						}else
							if(str.equals("find")){
								try{boolean is=false;
									Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
									Connection coon3=DriverManager.getConnection("jdbc:odbc:Register","","");
									Statement st=coon3.createStatement();
									String find="SELECT * FROM player";
									ResultSet set=st.executeQuery(find);
									String friendname=in.readLine();
								String name,face,status;
										while(set.next()){
											name=set.getString("UserName");
											if(name.equals(friendname)){
												is=true;
											out.println("ok");
											out.println(name);
											face=set.getString("头像");
											out.println(face);
											status=set.getString("在线");
											out.println(status);
										}}
										if(!is){
											out.println("false");
										}
										set.close();
										st.close();
										coon3.close();
										socket.close();
								}catch(Exception e){
									e.printStackTrace();
								}
							}else
								if(str.equals("friend")){
									try{
										Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
										Connection coon4=DriverManager.getConnection("jdbc:odbc:Register","","");
									String friend="SELECT 好友列表  FROM 好友列表  WHERE 玩家列表=?";
									PreparedStatement prepare4=coon4.prepareCall(friend);
									prepare4.clearParameters();
									String name=in.readLine().trim();
									prepare4.setString(1, name);
									ResultSet r4=prepare4.executeQuery();
									Vector friendlist=new Vector();
									while(r4.next()){
										friendlist.add(r4.getString(1));
									}
									out.println(friendlist.size());
									for(int i=0;i<friendlist.size();i++){
										String friend1="SELECT UserName,头像,在线,IP FROM player WHERE UserName=?";
										PreparedStatement prepare5=coon4.prepareCall(friend1);
										prepare5.clearParameters();
										prepare5.setObject(1, friendlist.get(i));
										ResultSet set1=prepare5.executeQuery();
												while(set1.next()){
													out.println(set1.getString("UserName"));
													out.println(set1.getString("头像"));
													out.println(set1.getInt("在线"));
												}set1.close();		
									}out.println("over");
									coon4.close();
									}catch(Exception e){
										e.printStackTrace();
									}out.close();
									in.close();
									socket.close();
							
								}
								else
									if(str.equals("addfriend")){
										try{boolean is=true;
											Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
											Connection coon6=DriverManager.getConnection("jdbc:odbc:Register","","");
											String name=in.readLine();
											String myName=in.readLine();
											String exist="SELECT * FROM 好友列表";
											Statement stat=coon6.createStatement();
											ResultSet rst=stat.executeQuery(exist);
											while(rst.next()){
												String na=rst.getString(1);
												String p=rst.getString(2);
												if(na.equals(myName)){
													if(p.equals(name)){
													is=false;
													out.println("exist");
													out.close();
													in.close();
													break;}
												}
											}
											if(is){
											String add="INSERT INTO 好友列表 VALUES (?,?)";
											PreparedStatement prepare6=coon6.prepareCall(add);
											prepare6.clearParameters();
											prepare6.setString(1, myName);
											prepare6.setString(2, name);
											int i6;
											i6=prepare6.executeUpdate();
											out.println("ok");}
											coon6.close();
										}catch(Exception e){
											e.printStackTrace();
										}
									}
									else
										if(str.equals("addnewfriend")){
											try{
												Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
												Connection coon6=DriverManager.getConnection("jdbc:odbc:Register","","");
												String friendname=in.readLine();
												String myname=in.readLine();
												String addfriend="INSERT INTO 好友列表 VALUES (?,?)";
												PreparedStatement prepare6=coon6.prepareCall(addfriend);
												prepare6.clearParameters();
												prepare6.setString(1, myname);
												prepare6.setString(2, friendname);
												int r6=0;
												r6=prepare6.executeUpdate();
												coon6.close();
												out.close();
												in.close();
												socket.close();
											}catch(Exception e){
												e.printStackTrace();
											}
										}else
												if(str.equals("logout")){
													try{
														Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
														Connection coon9=DriverManager.getConnection("jdbc:odbc:Register","","");
														String name=in.readLine();
														String status="UPDATE player SET 在线=0 WHERE UserName=?";
														PreparedStatement prepare9=coon9.prepareCall(status);
														prepare9.clearParameters();
														prepare9.setString(1, name);
														int r8=prepare9.executeUpdate();
													}catch(Exception e){
														e.printStackTrace();
													}
												}else
													{
														if(str.equals("change")){
															try{
															Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
															Connection coon6=DriverManager.getConnection("jdbc:odbc:Register","","");
															String change="UPDATE player SET 头像=? WHERE UserName=?";
															PreparedStatement prepare12=coon6.prepareCall(change);
															String name=in.readLine().trim();
															String face=in.readLine();
															String faceImage=name+face;
															prepare12.clearParameters();
															prepare12.setString(1,faceImage);
															prepare12.setString(2,name);
															int set=prepare12.executeUpdate();
															coon6.close();
															socket.close();
															} 
														catch(Exception e){
															e.printStackTrace();
														}
														}
														else
															if(str.equals("roomlist")){
																Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																Connection coon6=DriverManager.getConnection("jdbc:odbc:Register","","");
																String sql="SELECT * FROM roomlist";
																Statement stat=coon6.createStatement();
																ResultSet re=stat.executeQuery(sql);
																int i=0;
																while(re.next()){
																	i++;
																}
																out.println(i);
																String sql1="SELECT * FROM roomlist";
																ResultSet re1=stat.executeQuery(sql1);
																while(re1.next()){
																	out.println(re1.getInt(1));
																	out.println(re1.getInt(2));
																	out.println(re1.getInt(3));
																	out.println(re1.getString(4));
																}out.println("over");
															}
															else
																if(str.equals("join")){
																	boolean is=true;
																	int people = 0;
																	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																	Connection coon6=DriverManager.getConnection("jdbc:odbc:Register","","");
																	String name=in.readLine();
																	String roomnumber=in.readLine();
																	String sql="SELECT state,people FROM roomlist WHERE room=?";
																	PreparedStatement prepare=coon6.prepareCall(sql);
																	prepare.clearParameters();
																	prepare.setInt(1,Integer.parseInt(roomnumber));
																	ResultSet r=prepare.executeQuery();
																	while(r.next()){
																	    int stat=r.getInt("state");
																	    if(stat==1){
																	    	out.println("playing");
																	    	is=false;
																	    }
																	    else
																	    {
																	    	people=r.getInt("people");
																	    	if(people==4){
																	    		out.println("full");
																	    		is=false;
																	    	}
																	    }
																	}
																	if(is){
																		out.println("ok");
																		String sql1 = null;
																		if(people==1){
																			sql1="UPDATE roomlist SET passerger1=?,people=? WHERE room=? ";
																		}
																		else
																			if(people==2){
																				sql1="UPDATE roomlist SET passerger2=?,people=? WHERE room=? ";
																			}
																			else
																				if(people==3){
																					sql1="UPDATE roomlist SET passerger2=?,people=? WHERE room=? ";
																				}
																		PreparedStatement prepare1=coon6.prepareCall(sql1);
																		prepare1.clearParameters();
																		prepare1.setString(1,name);
																		prepare1.setInt(2,++people);
																		prepare1.setInt(3,Integer.parseInt(roomnumber));
																		int set=prepare1.executeUpdate();
																		coon6.close();
																		socket.close();
																	}
																}
																else
																	if(str.equals("creatroom")){
																		boolean is=false;
																		String name=in.readLine();
																		int roomnumber=Integer.parseInt(in.readLine().trim());
																		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																		Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																		String sql="SELECT * FROM roomlist";
																		Statement stat=coon.createStatement();
																		ResultSet re=stat.executeQuery(sql);
																		while(re.next()){
																			int room=re.getInt(1);
																			if(room==roomnumber){
																				is=true;
																				break;
																			}
																		}
																		if(is){
																			out.println("exist");
																		}
																		else {
																			out.println("ok");
																			System.out.println("ok");
																			String sql1="INSERT INTO roomlist (room,people,host) VALUES(?,?,?)";
																			PreparedStatement prepare=coon.prepareCall(sql1);
																			prepare.clearParameters();
																			prepare.setInt(1, roomnumber);
																			prepare.setInt(2, 1);
																			prepare.setString(3,name);
																			int r2=prepare.executeUpdate();
																		}
																	}
																	else
																		if(str.equals("closeroom")){
																			int roomnum=Integer.parseInt(in.readLine());
																			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																			Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																			String del="DELETE FROM ROOMLIST WHERE room=?";
																			PreparedStatement prepare=coon.prepareCall(del);
																			prepare.clearParameters();
																			prepare.setInt(1, roomnum);
																			int r7=prepare.executeUpdate();
																			int i=Integer.parseInt(in.readLine());
																			for(int j=0;j<i;j++){
																				String name=in.readLine();
																				Set<String> set=map.keySet();
																				Iterator<String> interator=set.iterator();
																				while(interator.hasNext()){
																					String name1=interator.next();
																					if(name1.equals(name)){
																						Socket s=map.get(name);
																						PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))),true);
																						out1.println("closeroom");
																					}
																				}
																			}
																			
																		}
																		else
																		if(str.equals("roomer")){
																			String num=in.readLine();
																			int roomnum=Integer.parseInt(num);
																			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																			Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																			Statement stat=coon.createStatement();
																			String sql1="SELECT * FROM roomlist";
																			ResultSet r=stat.executeQuery(sql1);
																			while(r.next()){
																				int number=r.getInt(1);
																				if(number==roomnum){
																					int i=r.getInt(3);
																					out.println(i);
																					if(i==1){out.println(r.getString(4));}
																					if(i==2){
																						out.println(r.getString(4));
																						out.println(r.getString(5));
																					}
																					else
																						if(i==3){
																							out.println(r.getString(4));
																							out.println(r.getString(5));
																							out.println(r.getString(6));
																						}
																						else
																							if(i==4){
																								out.println(r.getString(4));
																								out.println(r.getString(5));
																								out.println(r.getString(6));
																								out.println(r.getString(7));
																							}
																				}
																			}
																		}
														else
															if(str.equals("game")){
																String room=in.readLine();
																Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																Statement stat=coon.createStatement();
																String sql1="SELECT * FROM game";
																ResultSet re1=stat.executeQuery(sql1);
																int num=0;
																while(re1.next()){
																	num++;
																}
																int i=(int)(Math.random()*num+1);
																System.out.println(i);
																		String sql2="SELECT * FROM game";
																		ResultSet re2=stat.executeQuery(sql2);
																for(int j=0;j<i;j++){
																	re2.next();
																}
																
																String name=re2.getString(2);
																String help=re2.getString(3);
																String word=name;
																map1.put(room,word);
																out.println(name);
																out.println(help);
															}
															else
																if(str.equals("guess")){
																	String name=in.readLine();
																	String num=in.readLine();
																	String w=in.readLine();
																	Set<String> s=map1.keySet();
																	Iterator<String> interator=s.iterator();
																	String word = null;
																	while(interator.hasNext()){
																		String numb=interator.next();
																		if(numb.equals(num)){
																			word=map1.get(numb);
																			break;
																		}
																	}
																	if(w.equals(word)){
																		out.println("success");
																	    out.println("恭喜你,答对了!得2分");
																	     int sco=2;
																		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																		Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																		int sc=0;
																		String sql1="SELECT * FROM player";
																		Statement st=coon.createStatement();
																		ResultSet set=st.executeQuery(sql1);
																		while(set.next()){
																			String na=set.getString(1);
																			if(na.equals(name)){
																				sc=set.getInt(7);
																				break;
																			}
																		}
																		String sql2="UPDATE player SET score=? WHERE UserName=?";
																		PreparedStatement prepare=coon.prepareCall(sql2);
																		prepare.clearParameters();
																		prepare.setInt(1,sc+sco);
																		prepare.setString(2,name);
																		int i=prepare.executeUpdate();
																	}
																	else
																		out.println("flase");
																}
																else
																	if(str.equals("finish")){
																		String num=in.readLine();
																		Set<String> set=map1.keySet();
																		Iterator<String> iterator=set.iterator();
																		while(iterator.hasNext()){
																			String numb=iterator.next();
																			if(numb.equals(num)){
																				map1.remove(numb);
																				break;
																			}
																		}
																	}
															else
														        if(str.equals("insertword")){
														        boolean is=true;
															String word=in.readLine();
															String help=in.readLine();
															Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
															Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
															Statement stat=coon.createStatement();
															String sql1="SELECT * FROM game";
															ResultSet re=stat.executeQuery(sql1);
															while(re.next()){
																String name=re.getString(1);
																if(name.equals(word))
																{out.println("exist");
																is=false;
																break;
																	
																}
															}
															if(is){
																String sql="INSERT INTO game (Words,helps) VALUES(?,?)";
																PreparedStatement prepare=coon.prepareCall(sql);
																prepare.clearParameters();
																prepare.setString(1, word);
																prepare.setString(2, help);
																int s=prepare.executeUpdate();
																out.println("ok");
															}
														   }
															else
																if(str.equals("person")){
																	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																	Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																	Statement stat=coon.createStatement();
																	String sql="SELECT * FROM player";
																	ResultSet re=stat.executeQuery(sql);
																	String name=in.readLine();
																	while(re.next()){
																		String n=re.getString(1);
																		if(n.equals(name)){
																			String face=re.getString(4);
																			int score=re.getInt(7);
																			out.println(face);
																			out.println(score);
																			System.out.println(face);
																			break;
																		}
																	}
																}
																else
																	
															if(str.equals("USER")){
																String key=in.readLine();
																map.put(key,socket);
															}else
															if(str.equals("EXIT")){
																String key=in.readLine();
																map.remove(key);
															}
															else
																if(str.equals("start")){
																	int room=Integer.parseInt(in.readLine());
																	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																	Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																	String sql="UPDATE roomlist SET state=? WHERE room=?";
																	PreparedStatement prepare=coon.prepareCall(sql);
																	prepare.clearParameters();
																	prepare.setInt(1, 1);
																	prepare.setInt(2, room);
																	int t=prepare.executeUpdate();
																	int num=Integer.parseInt(in.readLine());
																	Vector<String> list=new Vector<String>();
																	for(int i=0;i<num;i++){
																		String name=in.readLine();
																		Set<String> set=map.keySet();
																		Iterator<String> interator=set.iterator();
																		while(interator.hasNext()){
																			String keyName=interator.next();
																			if(keyName.equals(name)){
																				Socket s=map.get(name);
																				PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))),true);
																				out1.println("start");
																			}
																		}
																	}
																	
																}
																else
																	if(str.equals("tell")){
																		String name=in.readLine();
																		String face=null;
																		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
																		Connection coon=DriverManager.getConnection("jdbc:odbc:Register","","");
																		String sql="SELECT * FROM player";
																		Statement stat=coon.createStatement();
																		ResultSet r=stat.executeQuery(sql);
																		while(r.next()){
																		    String name1=r.getString(1);
																		    if(name1.equals(name)){
																		    	face=r.getString(4);
																		    	break;
																		    }
																		}
																		int num=Integer.parseInt(in.readLine());
																		for(int i=0;i<num;i++){
																			String name2=in.readLine();
																			Set<String> set=map.keySet();
																			Iterator<String> interator=set.iterator();
																			while(interator.hasNext()){
																				String keyName=interator.next();
																				if(keyName.equals(name2)){
																					Socket s=map.get(keyName);
																					PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))),true);
																					out1.println("tell");
																					out1.println(name);
																					out1.println(face);
																					out1.flush();
																				}
																			}
																		}
																	}
																else
																	
																if(str.equals("MSG")){
																	try{
									                                String sendname=in.readLine();
									                                String name=in.readLine().trim();
									                                System.out.println(name);
									                                String  info=in.readLine();
																	boolean is=true;
																	Set<String> set=map.keySet();
																	Iterator<String> interator=set.iterator();
																	while(interator.hasNext()){
																		String keyName=interator.next();
																		if(keyName.equals(name)){
																			Socket s=map.get(keyName);
																			is=false;
																			PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))),true);
																			out1.println(str);
																			out1.println(sendname);
																			System.out.println(sendname);
																			out1.println(info);
																			out1.flush();
																		}if(is){
																			out.println("disappear");
																		}
																	}}catch(Exception e){
																		e.printStackTrace();
																	}
																	 } 
																else
																	if(str.equals("MSG:paint")){
																		String sendname=in.readLine();
																		String info=in.readLine();
																		int num=Integer.parseInt(in.readLine());
																		for(int i=0;i<num;i++){
																			String name=in.readLine();
																			Set<String> set=map.keySet();
																			Iterator<String> interator=set.iterator();
																			while(interator.hasNext()){
																				String keyName=interator.next();
																				if(keyName.equals(name)){
																					Socket s=map.get(name);
																					PrintWriter out1=new PrintWriter(new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))),true);
																					out1.println("MSG:paint");
																					out1.println(sendname);
																					out1.println(info);
																				}
																			}
																			}
																		}
																	}
														
					}
				
				
		}}catch(Exception e){
			e.printStackTrace();}
	}
}public static void main(String args[]) throws IOException{
	new ServerThread();
}
}

