package com.yychatclient.control;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;

public class ClientConnet {
	
	//public static Socket s;
	public  Socket s;
	public static HashMap hmSocket=new HashMap<String,Socket>();
	public ClientConnet() {
		try {
			s=new Socket("127.0.0.1",3456);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean registerUserIntoDB(User user){
		boolean registerSuccess=false;
		
		
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos =new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);
			//System.out.println("用户登录消息发送成功！密码是："+user.getPassWord());
			//
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();
			if(mess.getMessageType().equals(Message.message_RegisterSuccess))
				registerSuccess=true;
				s.close();//关闭客户端你socket对象
		}
		catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	}return registerSuccess;
	}
	public Message loginValidateFromDB(User user){
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos =new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);
			//System.out.println("用户登录消息发送成功！密码是："+user.getPassWord());
			//
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();
			
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				System.out.println("验证成功");
				hmSocket.put(user.getUserName(), s);
				new ClientReceiverThread(s).start();
			}
		}catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mess;
		
	}
	/*public boolean loginValidate(User user){
		boolean loginSuccess=false;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Message mess=null;
		try {
			oos =new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(user);
			//System.out.println("用户登录消息发送成功！密码是："+user.getPassWord());
			//
			ois=new ObjectInputStream(s.getInputStream());
			mess=(Message)ois.readObject();
			
			//System.out.println("收到服务器返回的登录是否成功的消息,类型是："+mess.getMessageType());
			
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				loginSuccess=true;
				System.out.println(user.getUserName()+"登录成功");
				hmSocket.put(user.getUserName(), s);
				new ClientReceiverThread(s).start();
			}
		}
			catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loginSuccess;
		
	}*/

}