package com.chatclient.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;

import com.yychat.model.Message;
import com.yychat.model.MessageType;
import com.yychatclient.control.ClientConnet;

public class FriendChatClient1 extends JFrame implements ActionListener{//严格实现单继承

	
	//
	JScrollPane jsp;
	JTextArea jta;

	
	//
	JPanel jp;
	JTextField jtf;
	JButton jb;
	
	String sender;
	String receiver;
	
	public FriendChatClient1(String sender, String receiver){
		
		this.sender=sender;
		this.receiver=receiver;
		
		jta = new JTextArea();
		jta.setEditable(false);
		jsp =new JScrollPane(jta);
		this.add(jsp,"Center");
		
		
		
		jp =new JPanel();
		jtf =new JTextField(15);
		jtf.setForeground(Color.red);
		jb=new JButton("发送");
		jb.addActionListener(this);
		jp.add(jtf);
		////////////////////////////////////////////////////////////////////////////////////////
		jp.add(jb);getRootPane().setDefaultButton(jb);
		////////////////////////////////////////////////////////////////////////////////////////
		
		this.add(jp,"South");
		
		
		this.setSize(350,240);
		this.setTitle(sender+"正在和"+receiver+"聊天");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		//FriendChatClient FriendChatClient=new FriendChatClient();


	}
	@Override
	public void actionPerformed(ActionEvent arg0) {//事件处理代码
		if(arg0.getSource()==jb) jta.append(jtf.getText()+"\r\n");
		
		
		//向服务器发送聊天信息
		Message mess=new Message();
		mess.setSender(sender);
		mess.setReceiver(receiver);
		mess.setContent(jtf.getText());
		mess.setMessageType(Message.message_Common);
		ObjectOutputStream oos;
		try{
			Socket s=(Socket)ClientConnet.hmSocket.get(sender);
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
		
		
	    
	
		
		//ObjectInputStream ois=new ObjectInputStream(ClientConnet.s.getInputStream());
		//mess=(Message)ois.readObject();//接收聊天信息
		String showMessage=mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent();
		System.out.println(showMessage);
		//jta.append(showMessage+"\r\n");
		
		
		} catch (IOException e){
			e.printStackTrace();
		}
		}
   public void appendJta(String showMessage){
	   jta.append(showMessage+"\r\n");
	
	
   
   			}
		

}
