package com.yychatclient.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

import com.chatclient.view.ClientLogin;
import com.chatclient.view.FriendChatClient1;
import com.chatclient.view.FriendList;
import com.yychat.model.Message;

public class ClientReceiverThread extends Thread{
		
	private Socket s;//成员变量
	
	public ClientReceiverThread(Socket s) {
		this.s=s;
	}
	
public void run(){
	ObjectInputStream ois;
	while(true){
		try {	
					ois=new ObjectInputStream(s.getInputStream());
					Message mess=(Message)ois.readObject();//接收聊天信息
					String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent();
					System.out.println(showMessage);
					
					if(mess.getMessageType().equals(Message.message_Common)){
						
					
					//jta.append(showMessage+"\r\n");             
					//在好友界面friendchatclient1上显示聊天信息
					//1。如何得到好友聊天界面
					FriendChatClient1 friendChatClient1=(FriendChatClient1)FriendList.hmFriendChatClient1.get(mess.getReceiver()+"to"+mess.getSender());
					
				
					//2。再显示信息
					
					friendChatClient1.appendJta(showMessage);
					}
					
					if(mess.getMessageType().equals(Message.message_OnlineFriend)){
						
						//激活对应图标，首先要拿到好友列表对象
						FriendList friendList=(FriendList)ClientLogin.hmFriendList.get(mess.getReceiver());
						//激活对应图标
						friendList.setEnableFriendIcon(mess.getContent());
						
					}
					
			} 
					catch (IOException | ClassNotFoundException e) {

				e.printStackTrace();
				} 
			}
		
	}
}













