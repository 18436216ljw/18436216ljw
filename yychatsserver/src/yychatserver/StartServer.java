package yychatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {

		public static HashMap hmSocket=new HashMap<String,Socket>();
		
	@SuppressWarnings("unchecked")
	public  StartServer() {
		
		ServerSocket ss;
		String userName;
		String passWord;
		try {//�����쳣
			ss=new ServerSocket(3456);
			System.out.println("�������Ѿ�����������3456�˿�");
			while(true){
			
			Socket s=ss.accept();//���տͻ�����������
			System.out.println("���ӳɹ�:"+s);
		
		ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
		User user=(User)ois.readObject();
		userName=user.getUserName();
		passWord=user.getPassWord();
		System.out.println(user.getUserName());
		System.out.println(user.getPassWord());
		
		//������֤����
		Message mess=new Message();
		mess.setSender("Seerver");
		mess.setReceiver(userName);
		if(passWord.equals("123456")){
			
			
			
			
			//���߿ͻ���������֤ͨ��,���Դ���Message��
	
		mess.setMessageType("Message.message_LoginSuccess");//1Ϊ��֤ͨ��
		
		}else{	
			
			mess.setMessageType("Message.message_LoginFailure");//0Ϊ��֤��ͨ��
			
		}
		ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
		if(passWord.equals("123456")){
			new ServerReceiverThread(s).start();
			hmSocket.put(userName,s);
		}
			}
	
	
		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


