package yychatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{
	Socket s;
	public ServerReceiverThread(Socket s) {//
	this.s=s;
	}
		
	public void run(){
		ObjectInputStream ois;
			ObjectOutputStream oos;
			Message mess;
		while(true){
		
		try{
			
		ois=new ObjectInputStream(s.getInputStream());
		mess=(Message)ois.readObject();//接收聊天信息
		System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent());
	
		if(mess.getMessageType().equals(Message.message_Common)){
		Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
		oos=new ObjectOutputStream(s1.getOutputStream());
		oos.writeObject(mess);//转发聊天信息
		
		}
		
		if(mess.getMessageType().equals(Message.message_RequestOnLineFriend)){
			//拿到全部在线好友的名字
			Set friendSet=StartServer.hmSocket.keySet();//键值对
			Iterator it=friendSet.iterator();
			String friendName;
			String friendString=" ";
		while(it.hasNext()){
			friendName=(String)it.next();//取出元素
			if(!friendName.equals(mess.getSender()))
				friendString=friendString+friendName+" ";//为什么用空格？
				}
			
		//发送好友名字到客户端
		
		}
		
		
		}
		catch(IOException | ClassNotFoundException e){
		e.printStackTrace();
	}
	
	}
	}
}
		
	
