import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class Server {
	
	public static void main (String args[]) throws InterruptedException {
		
		for(;;) {
			Server.ServertSock();
			//TimeUnit.SECONDS.sleep(5);
		}
		
	}
	
	//Server Socket �s�u
	public static void ServertSock() {
		
		try {

			//Server Socket �s�u
			ServerSocket Server = new ServerSocket(9998);
			System.out.println("Server is created . Waiting for connection...");
			Socket S1 = Server.accept();
			System.out.println("Client is connected , IP:"+S1.getInetAddress());
			
			//Server ����Client�T��(��y)
			DataInputStream DinS = new DataInputStream(S1.getInputStream());
			String tmp = new String(DinS.readUTF());//��J��y�ഫ������
			
			String Array[] = new String[2];//�r����λP�P�_Service Ok or Error
			Array = tmp.split(",");//�r����λP�P�_Service Ok or Error
			int CK = Integer.parseInt(Array[2]);
			if(CK == 0) {//0:OK , 1:Error
				DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server ��X�r��
				String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",0";
				Out.writeUTF(Re_Temp);
			}
			else {//0:OK , 1:Error
				DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server ��X�r��
				String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",1";
				Out.writeUTF(Re_Temp);
			}
			//String tmp_S = "Server>"+tmp;
			//System.out.println(tmp_S);
						
			//�ǰe�r��
			//String Re_Temp = "Server :Test tmp";
			//Out.writeUTF(Re_Temp);
			
			//����Socket
			S1.close();
			}catch(IOException e) {
				//System.out.println("Error");
			}
		}

}
