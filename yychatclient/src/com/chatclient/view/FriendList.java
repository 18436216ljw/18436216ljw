package com.chatclient.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

public class FriendList extends JFrame implements ActionListener,MouseListener{//顶层容器
	
	public static HashMap hmFriendChatClient1=new HashMap<String,FriendChatClient1>();//键值对
	
	
	CardLayout cardLayout;//卡片布局
	
	JPanel myFriendPanel;
	JButton myFriendJButton;
	
	JScrollPane myFriendScrollPane,myStrangerScrollPane;
	JPanel myFriendListJPanel,myStrangerListJPanel;
	static final int FRIENDCOUNT=51;
	static final int STRANGER=21;
	JLabel[] myFriendJLabel=new JLabel[FRIENDCOUNT];//对象数组
	JLabel[] myStrangerJLabel=new JLabel[STRANGER];
	
	JPanel myStrangerBlackListJPanel;
	JButton myStrangerJButton;
	JButton blackListJButton;
	
	JPanel myStrangerPanel;
	
	JPanel myFriendStrangerPanel;
	JButton myFriendJButton1;
	JButton myStrangerJButton1;
	
	JButton blackListJButton1;
	
	
	String userName;
	
	
	
	
	public FriendList(String userName,String friendString){
		
		this.userName=userName;
		//第一张卡片
		myFriendPanel=new JPanel(new BorderLayout());//边界布局
		//System.out.printin(myFriendPanel.getLayou());
		
		myFriendJButton=new JButton("我的好友");
		myFriendPanel.add(myFriendJButton,"North");
		
		//中部
		/*myFriendListJPanel=new JPanel(new GridLayout(FRIENDCOUNT-1,1));
		for(int i=1;i<FRIENDCOUNT;i++)
		{
			myFriendJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY0.gif"),JLabel.LEFT);//"1"
			myFriendJLabel[i].setEnabled(false);//未激活所有图标
			//
			
			myFriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendJLabel[i]);
		}*/
		String[] friendName=friendString.split(" ");
		int count=friendName.length;
		myFriendListJPanel=new JPanel(new GridLayout(count,1));
		for(int i=0;i<count;i++)
		{
			myFriendJLabel[i]=new JLabel(friendName[i]+"",new ImageIcon("images/YY0.gif"),JLabel.LEFT);//"1"
			//myFriendJLabel[i].setEnabled(false);//未激活所有图标
			//
			
			myFriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendJLabel[i]);
		}
		//myFriendJLabel[Integer.parseInt(userName)].setEnabled(true);
		
		//myFriendScrollPane=new JScrollPane();
		//myFriendScrollPane.add(myFriendListJPanel);
		myFriendScrollPane=new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendScrollPane);
		
		
		
		
		
		
		
		myStrangerBlackListJPanel=new JPanel(new GridLayout(2,1));//网络布局
		myStrangerJButton=new JButton("我的陌生人");
		//添加事件监听器
		myStrangerJButton.addActionListener(this);
		
		blackListJButton=new JButton("黑名单");
		myStrangerBlackListJPanel.add(myStrangerJButton);
		myStrangerBlackListJPanel.add(blackListJButton);
		myFriendPanel.add(myStrangerBlackListJPanel,"South");
		
		//另一张卡片
		myStrangerPanel = new JPanel(new BorderLayout());
		
		myFriendStrangerPanel=new JPanel(new GridLayout(2,1));
		
	
		myFriendJButton1=new JButton("我的好友");//添加监听器
		myFriendJButton1.addActionListener(this);
		myStrangerJButton1=new JButton("我的陌生人");
		myFriendStrangerPanel.add(myFriendJButton1);
		myFriendStrangerPanel.add(myStrangerJButton1);
		myStrangerPanel.add(myFriendStrangerPanel,"North");
		
		myStrangerListJPanel=new JPanel(new GridLayout(STRANGER-1,1));
		for(int i=1;i<STRANGER;i++)
		{
			myStrangerJLabel[i]=new JLabel(i+"",new ImageIcon("images/YY1.gif"),JLabel.LEFT);//"2"
			myStrangerJLabel[i].addMouseListener(this);
			myStrangerListJPanel.add(myStrangerJLabel[i]);
		}
		myStrangerScrollPane=new JScrollPane(myStrangerListJPanel);
		myStrangerPanel.add(myStrangerScrollPane);
		
		
		blackListJButton1=new JButton("黑名单");
		myStrangerPanel.add(blackListJButton1,"South");
		
		cardLayout=new CardLayout();
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");
		this.add(myStrangerPanel,"2");
		
		this.setSize(150,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		//FriendList friendList=new FriendList();
	
	}
		public void setEnableFriendIcon(String friendString){
			
		String[] friendName= friendString.split(" ");
		int count=friendName.length;
		for(int i=0;i<=count;i++){
		myFriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);	
		}
	}
	public void setEnableNewFriendIcon(String newOnlinefriendString){
		myFriendJLabel[Integer.parseInt(newOnlinefriendString)].setEnabled(true);
		
	}
		
		
	@Override
	public void actionPerformed(ActionEvent argo){
		if(argo.getSource()==myStrangerJButton){
			cardLayout.show(this.getContentPane(), "2");
		}
		if(argo.getSource()==myFriendJButton1){
			cardLayout.show(this.getContentPane(), "1");
			
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlbl=(JLabel)arg0.getSource();
			String receiver =jlbl.getText();
			
			
		FriendChatClient1 friendChatClient1=(FriendChatClient1)hmFriendChatClient1.get(userName+"to"+receiver);
			if(friendChatClient1==null){
				friendChatClient1=new FriendChatClient1(this.userName,receiver);//friendChatClient1是一个变量，用于引用
				hmFriendChatClient1.put(userName+"to"+receiver,friendChatClient1);//保存对象到hashmap中	
			}else{
			
				friendChatClient1.setVisible(true);
				
			}			
		}		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel jLabel=(JLabel)arg0.getSource();
		jLabel.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel jLabel=(JLabel)arg0.getSource();
		jLabel.setForeground(Color.black);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		
	}
}