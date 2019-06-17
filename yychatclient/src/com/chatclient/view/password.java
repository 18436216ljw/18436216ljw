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
		jlbl1=new JLabel("账号",JLabel.CENTER);jlbl2=new JLabel("密码",JLabel.CENTER);
		jlbl3=new JLabel("确认密码",JLabel.CENTER);
		jb1=new JButton("注册");
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
		this.setTitle("新用户注册");
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
			//创建User对象 
			User user =new User();
			user.setUserName(userName);
			
			
			
			
			String password1=new String(jpf1.getPassword());
			String password2=new String(jpf2.getPassword());
			System.out.println(password1+"  "+password2);
			if(userName.equals(""))
			{
				JOptionPane.showMessageDialog(this,"用户名不能为空");
			}
			else
				if(!userName.equals("")&password1.equals(""))
				{
					JOptionPane.showMessageDialog(this,"密码不能为空");
				}
				
				else
				if(!userName.equals("")&!password1.equals("")&password2.equals(""))
				{
					JOptionPane.showMessageDialog(this,"请重复输入密码");
				}
				else
			if(!password2.equals(password1)){
			JOptionPane.showMessageDialog(this,"密码不一致,请重新输入");
			} 
		else{ //boolean registerSuccess=new ClientConnet().registerUserIntoDB(user);
				
			user.setPassWord(password);
			user.setUserMessageType("USER_REGISTER");
			if(new ClientConnet().registerUserIntoDB(user)){
				
				JOptionPane.showMessageDialog(this,"注册成功！");
				this.dispose();
			
				} 
			
				else{
				JOptionPane.showMessageDialog(this,"可能存在同名用户，注册失败！");
					}
			}                         
			
		}
		
	}
}

