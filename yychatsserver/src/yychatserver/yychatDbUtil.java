package yychatserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class yychatDbUtil {
	public static final String MYSOLDRIVER="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
	public static final String DBUSER="root";
	public static final String DBPASS="";
	
	public static void loadDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
		//2����ȡ���ӵĶ���
	public static Connection getConnection(){
	loadDriver();
	Connection conn=null;
	try {
		conn = DriverManager.getConnection(URL,DBUSER,DBPASS);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return conn;
}
	public static int addRelation(String majorUser,String slaveUser,String relationType){
		int count =0;
		Connection conn =getConnection();
		String relation_Add_Sql="insert into relation (majoruser,slaveuser,relationtype) values(?,?,?)";
		PreparedStatement ptmt=null;
		
		try {
			ptmt=conn.prepareStatement(relation_Add_Sql);	
			ptmt.setString(1,majorUser);
			ptmt.setString(2,slaveUser);
			ptmt.setString(3,relationType);
			//java.util.Date date=new java.util.Date();
			//4��ִ�в�ѯ�����ؽ����
			count=ptmt.executeUpdate();//�����¼������
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeDB(conn,ptmt);
		}
		return count;
	}	
	
public static void addUser(String userName,String passWord){
	Connection conn =getConnection();
	String user_Login_Sql="insert into user (username,password,registertimestamp) values(?,?,?)";
	PreparedStatement ptmt=null;
	
	try {
		ptmt=conn.prepareStatement(user_Login_Sql);	
		ptmt.setString(1,userName);
		ptmt.setString(2,passWord);
		//java.util.Date date=new java.util.Date();
		Date date=new Date();
		java.sql.Timestamp timestamp=new java.sql.Timestamp(date.getTime());
		ptmt.setTimestamp(3, timestamp);
		//4��ִ�в�ѯ�����ؽ����
		int count=ptmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally{
		closeDB(conn,ptmt);
	}
}
	
public static boolean seekUser(String userName) {
	boolean seekUserResult=false;
	Connection conn =getConnection();
	String user_Login_Sql="select * from user where username=?";
	PreparedStatement ptmt=null;
	ResultSet rs=null;
	
	try {
		ptmt=conn.prepareStatement(user_Login_Sql);	
		ptmt.setString(1,userName);
		
		//4��ִ�в�ѯ�����ؽ����
		rs =ptmt.executeQuery();
		//5�����ݽ�������ж��Ƿ��ܵ�¼
		seekUserResult =rs.next();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally{
		closeDB(conn,ptmt,rs);
	}
	
	return seekUserResult;
}
	
	public static boolean loginValidate(String userName,String passWord){
		boolean loginSuccess=false;
		Connection conn =getConnection();
		//3������PreparedStatement��������ִ��SQL��䣬��׼
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		
		try {
			ptmt=conn.prepareStatement(user_Login_Sql);	
			ptmt.setString(1,userName);
			ptmt.setString(2,passWord);
	
			//4��ִ�в�ѯ�����ؽ����
			rs =ptmt.executeQuery();
			//5�����ݽ�������ж��Ƿ��ܵ�¼
			loginSuccess =rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeDB(conn,ptmt,rs);
		}
		System.out.println("loginSuccessΪ��"+loginSuccess);
		return loginSuccess;
		}
	public static boolean seekRelation(String majorUser,String slaveUser,String relationType) {
		Connection conn =getConnection();
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		boolean seekRelationResult=false;
		String seek_Relation_Sql="select * from relation where majoruser=? and slaveUser=? and relationtype=?";
		try {
			ptmt=conn.prepareStatement(seek_Relation_Sql);
			ptmt.setString(1,majorUser);
			ptmt.setString(2,slaveUser);
			ptmt.setString(3,relationType);
			rs=ptmt.executeQuery();
			seekRelationResult =rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
				closeDB(conn,ptmt,rs);
			}
		return seekRelationResult;
		
	}
	
public static String getFriendString(String userName) {
	Connection conn =getConnection();
	PreparedStatement ptmt=null;
	ResultSet rs=null;
	String friendString="";
	String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
	try {
		ptmt=conn.prepareStatement(friend_Relation_Sql);
		ptmt.setString(1,userName);
	rs=ptmt.executeQuery();
	while(rs.next()){
		friendString=friendString+rs.getString("slaveuser")+" ";
					}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
			closeDB(conn,ptmt,rs);
		}
	return friendString;
	
}
public static void closeDB(Connection conn,PreparedStatement ptmt){
	if(conn!=null){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if(ptmt!=null){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
	public static void closeDB(Connection conn,PreparedStatement ptmt,ResultSet rs){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ptmt!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
