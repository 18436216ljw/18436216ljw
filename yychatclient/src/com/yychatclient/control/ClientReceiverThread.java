package com.yychatclient.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.chatclient.view.FriendChatClient1;
import com.chatclient.view.FriendList;
import com.yychat.model.Message;

public class ClientReceiverThread extends Thread{

	
	
		private Socket s;//��Ա����
	public ClientReceiverThread(Socket s) {
		this.s=s;
	
	}
public void run(){
	ObjectInputStream ois;
	while(true){
		try {	
					ois=new ObjectInputStream(s.getInputStream());
					Message mess=(Message)ois.readObject();//����������Ϣ
					String showMessage=mess.getSender()+"��"+mess.getReceiver()+"˵:"+mess.getContent();
					System.out.println(showMessage);
					//jta.append(showMessage+"\r\n");             
					//�ں��ѽ���friendchatclient1����ʾ������Ϣ
					//1����εõ������������
					FriendChatClient1 friendChatClient1=(FriendChatClient1)FriendList.hmFriendChatClient1.get(mess.getReceiver()+"to"+mess.getSender());
					
					
					//2������ʾ��Ϣ
					
					friendChatClient1.appendJta(showMessage);
					
					
			} catch (IOException | ClassNotFoundException e) {

				e.printStackTrace();
				} 
			}
		}
	}













