package com.yychatclient.control;

import java.io.*;
import java.net.Socket;
import java.util.Date;

import javax.swing.JOptionPane;

import com.chatclient.view.ClientLogin;
import com.chatclient.view.FriendChatClient1;
import com.chatclient.view.FriendList;
import com.yychat.model.Message;

public class ClientReceiverThread extends Thread{
	Socket s;
	public ClientReceiverThread(Socket s) {
		this.s=s;
	}


	public void run(){
		
		while(true){
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message mess = (Message)ois.readObject();
				String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说："+mess.getContent();
				System.out.println(showMessage);
				
				//添加新好友
				if(mess.getMessageType().equals(Message.message_AddFriendFailure_NoUser)){
					JOptionPane.showMessageDialog(null, "添加好友失败！用户名不存在！");
				}
				if(mess.getMessageType().equals(Message.message_AddFriendFailure_AlreadyFriend)){
					JOptionPane.showMessageDialog(null, "添加好友失败！不能重复添加好友！");
				}
				if(mess.getMessageType().equals(Message.message_AddFriendSuccess)){
					JOptionPane.showMessageDialog(null, "添加好友成功！");
					String allFriendName=mess.getContent();
					FriendList friendList =(FriendList)ClientLogin.hmFriendList.get(mess.getSender());
					friendList.updateFriendIcon(allFriendName);
					friendList.revalidate();
				}
				
			if(mess.getMessageType().equals(Message.message_Common)){
				FriendChatClient1 friendChatClient1=(FriendChatClient1)FriendList.hmFriendChat1.get(mess.getReceiver()+"to"+mess.getSender());
				Date date =mess.getSendTime();
				friendChatClient1.appendJta(date.toString()+"\r\n"+showMessage);
			}
				//3
			if(mess.getMessageType().equals(Message.message_OnlineFriend)){
				System.out.println("在线好友"+mess.getContent());
				
				//
				FriendList friendList=(FriendList)ClientLogin.hmFriendList.get(mess.getReceiver());
				//
				friendList.setEnableFriendIcon(mess.getContent());
				
			}
				
			//2.2
			if(mess.getMessageType().equals(Message.message_NewOnlineFriend)){
				System.out.println("新用户上线了，用户名："+mess.getContent());
				//
				FriendList friendList=(FriendList)ClientLogin.hmFriendList.get(mess.getReceiver());
				//
				friendList.setEnableFriendIcon(mess.getContent());
				friendList.revalidate();
			}
			
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
