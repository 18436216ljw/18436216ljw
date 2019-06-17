package yychatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer  {
	public static HashMap hmSocket=new HashMap<String,Socket>();
	
	ServerSocket ss;
	String userName;
	String passWord;
	String userMessageType;
	Message mess;
	ObjectOutputStream oos;
	public StartServer() {
		
		
		try {//
			ss=new ServerSocket(3456);
			System.out.println("服务器已经启动，监听3456端口");
		
		while(true){

			Socket s=ss.accept();//
			System.out.println("连接成功:"+s);
		//
		ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
		User user=(User)ois.readObject();
		userName=user.getUserName();
		passWord=user.getPassWord();
		userMessageType=user.getUserMessageType();
		System.out.println(userName);
		System.out.println(passWord);
		
		mess=new Message();
		mess.setSender("Server");
		mess.setReceiver(userName);
		
		if(user.getUserMessageType().equals("USER_LOGIN")){
		
		boolean loginSuccess=yychatDbUtil.loginValidate(userName, passWord);
		//
		
		//if(user.getPassWord().equals("123456")){
			//
		if(loginSuccess){
			mess.setMessageType(Message.message_LoginSuccess);//
			String friendString=yychatDbUtil.getFriendString(userName);
			//
			mess.setContent(friendString);
			System.out.println(userName+"的relation数据表中好友："+friendString);
		
		}else{
			mess.setMessageType(Message.message_LoginFailure);
			
		}
		sendMessage(s,mess);
		
		
		
		//
		if(loginSuccess){
			//2.1
			mess.setMessageType(Message.message_NewOnlineFriend);
			mess.setSender("Server");
			mess.setContent(userName);
			//拿到已经在线用户的全部名字
			Set onlineFriendSet =hmSocket.keySet();
			Iterator it=onlineFriendSet.iterator();
			String friendName;
			while(it.hasNext()){
				friendName=(String)it.next();
				mess.setReceiver(friendName);
				//向friendName
				//Socket s1=(Socket)hmSocket.get(friendName);
				sendMessage(s,mess);
			}
			
			
			
			hmSocket.put(userName, s);
			new ServerReceiverThread(s).start();
			
		
			}
		}
		if(user.getUserMessageType().equals("USER_REGISTER")){
			boolean seekUserResult=yychatDbUtil.seekUser(userName);
			if(seekUserResult){
				mess.setMessageType(Message.message_RegisterFailure);
				//System.out.println("注册失败 ");
			}else {
				yychatDbUtil.addUser(userName,passWord);
				mess.setMessageType(Message.message_RegisterSuccess);
				//System.out.println("注册成功 ");
			}
			sendMessage(s,mess);
			s.close();
		}
		
		
		

	}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	public void sendMessage(Socket s,Message mess) throws IOException {
		ObjectOutputStream oos =new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
