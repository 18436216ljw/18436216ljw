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

public class StartServer {

		public static HashMap hmSocket=new HashMap<String,Socket>();
			
		ServerSocket ss;
		String userName;
		String passWord;
		Message mess;
		Socket s;
		ObjectOutputStream oos;
	@SuppressWarnings("unchecked")
	public  StartServer() {
	
		try {//捕获异常
			ss=new ServerSocket(3456);
			System.out.println("服务器已经启动，监听3456端口");
			while(true){
			
			s=ss.accept();//接收客户端连接请求
			System.out.println("连接成功:"+s);
		
		ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
		User user=(User)ois.readObject();
		userName=user.getUserName();
		passWord=user.getPassWord();
		System.out.println(user.getUserName());
		System.out.println(user.getPassWord());
		
		//1.加载驱动程序
		Class.forName("com.mysql.jdbc.Driver");
		
		
		//2.获取连接对象
		String url="jdbc:mysql://127.0.0.1:3306/yychat";
		String dbuser="root";
		String dbpass="";
		Connection conn=DriverManager.getConnection(url,dbuser,dbpass);
		
		
		//3.创建preparedstatement对象，用来执行sql语句
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
		ptmt.setString(1,userName);
		ptmt.setString(2,passWord);
		
		//4.执行查询，返回结果集
		ResultSet rs=ptmt.executeQuery();
		
		
		//5.根据结果集来判断是否能登陆
		boolean	loginSuccess=rs.next();
		
		
		//密码验证功能
		mess=new Message();
		mess.setSender("Seerver");
		mess.setReceiver(userName);
		//if(passWord.equals("123456")){
		if(loginSuccess){
			
			//告诉客户端密码验证通过,可以创建Message类
	
		mess.setMessageType(Message.message_LoginSuccess);//1为验证通过
		
		//利用数据表中好友信息来更新好友列表 1。服务器查询好友信息表，并发送到
		String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
		ptmt=conn.prepareStatement(friend_Relation_Sql);
		ptmt.setString(1,userName);
		rs=ptmt.executeQuery();
		String friendString="";
		while(rs.next()){//移动结果集中的指针，把好友名字一个个取出来
			
			friendString=friendString+rs.getString("slaveuser")+" ";
		}
		mess.setContent(friendString);
		System.out.println(userName+"的relation数据库中的好友:"+friendString);
		
		}else{	
			mess.setMessageType(Message.message_LoginFailure);//0为验证不通过
			
		}
		senderMessage(s, mess);
		
		
		//if(passWord.equals("123456")){
		if(loginSuccess){
			
			//  1  激活新上线用户图标，在此把新用户登陆信息发送到在该用户之前登陆的所有用户
			mess.setMessageType(Message.message_NewOnlineFriend);
			mess.setSender("Server");
			mess.setContent(userName);
			
			
			Set onlineFriendSet=hmSocket.keySet();
			Iterator it=onlineFriendSet.iterator();
			String friendName;
			
			while(it.hasNext()){
				friendName=(String)it.next();
				mess.setReceiver(friendName);
				Socket s1=(Socket)hmSocket.get(friendName);
				senderMessage(s1, mess);
			}
			
			
			hmSocket.put(userName,s);
			new ServerReceiverThread(s).start();
			
		}
			}
	
	
		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void senderMessage(Socket s,Message mess) throws IOException {
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


