package com.chatclient.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.yychat.model.User;
import com.yychatclient.control.ClientConnet;

public class password extends JFrame implements ActionListener{
	public static HashMap hmFriendList=new HashMap<String,FriendList>();
	
	
JButton jb1;
		JPanel jp1;
		JTextField jtf1;
		JLabel jlbl1,jlbl2,jlbl3;
		JPasswordField jpf1,jpf2;
	public password() {
		
		jp1=new JPanel(new GridLayout(3,2));
		jlbl1=new JLabel("�˺�",JLabel.CENTER);jlbl2=new JLabel("����",JLabel.CENTER);
		jlbl3=new JLabel("ȷ������",JLabel.CENTER);
		jb1=new JButton("ע��");
		jb1.addActionListener(this);

		jtf1=new JTextField();
		jpf1=new JPasswordField();
		jpf2=new JPasswordField();
	
		
		jp1.add(jlbl1);		jp1.add(jtf1);
		jp1.add(jlbl2);		jp1.add(jpf1);	
		jp1.add(jlbl3);		jp1.add(jpf2);
		
		
		getRootPane().setDefaultButton(jb1);
			
		this.add(jp1,"North");
		this.add(jb1,"South");
		this.setSize(340,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setTitle("���û�ע��");
	}
	public static void main(String[] argo){
		password password = new password();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb1){
			
			
			
			String userName= jtf1.getText();
			//ClientLogin clientLogin=new ClientLogin(userName);
			String password=new String(jpf1.getPassword());
			//����User���� 
			User user =new User();
			user.setUserName(userName);
			
			
			
			
			String password1=new String(jpf1.getPassword());
			String password2=new String(jpf2.getPassword());
			System.out.println(password1+"  "+password2);
			if(userName.equals(""))
			{
				JOptionPane.showMessageDialog(this,"�û�������Ϊ��");
			}
			else
				if(!userName.equals("")&password1.equals(""))
				{
					JOptionPane.showMessageDialog(this,"���벻��Ϊ��");
				}
				
				else
				if(!userName.equals("")&!password1.equals("")&password2.equals(""))
				{
					JOptionPane.showMessageDialog(this,"���ظ���������");
				}
				else
			if(!password2.equals(password1)){
			JOptionPane.showMessageDialog(this,"���벻һ��,����������");
			} 
		else{ //boolean registerSuccess=new ClientConnet().registerUserIntoDB(user);
				
			user.setPassWord(password);
			user.setUserMessageType("USER_REGISTER");
			if(new ClientConnet().registerUserIntoDB(user)){
				
				JOptionPane.showMessageDialog(this,"ע��ɹ���");
				this.dispose();
			
				} 
			
				else{
				JOptionPane.showMessageDialog(this,"���ܴ���ͬ���û���ע��ʧ�ܣ�");
					}
			}                         
			
		}
		
	}
}
