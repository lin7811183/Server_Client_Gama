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
	
	//Server Socket 連線
	public static void ServertSock() {
		
		try {

			//Server Socket 連線
			ServerSocket Server = new ServerSocket(9998);
			System.out.println("Server is created . Waiting for connection...");
			Socket S1 = Server.accept();
			System.out.println("Client is connected , IP:"+S1.getInetAddress());
			
			//Server 接收Client訊息(串流)
			DataInputStream DinS = new DataInputStream(S1.getInputStream());
			String tmp = new String(DinS.readUTF());//輸入串流轉換為物件
			
			String Array[] = new String[2];//字串分割與判斷Service Ok or Error
			Array = tmp.split(",");//字串分割與判斷Service Ok or Error
			int CK = Integer.parseInt(Array[2]);
			if(CK == 0) {//0:OK , 1:Error
				DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server 輸出字串
				String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",0";
				Out.writeUTF(Re_Temp);
			}
			else {//0:OK , 1:Error
				DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server 輸出字串
				String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",1";
				Out.writeUTF(Re_Temp);
			}
			//String tmp_S = "Server>"+tmp;
			//System.out.println(tmp_S);
						
			//傳送字串
			//String Re_Temp = "Server :Test tmp";
			//Out.writeUTF(Re_Temp);
			
			//關閉Socket
			S1.close();
			}catch(IOException e) {
				//System.out.println("Error");
			}
		}

}
