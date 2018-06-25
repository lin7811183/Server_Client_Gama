import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Client {
	
	public static void main(String args[]) throws IOException, InterruptedException	{
		
		Client.ClientSock();
		//Client.ServiceCheck();
		//for(;;) {
		//Client.ClientSock();
		//TimeUnit.SECONDS.sleep(1);
		//}
	}
	
	//Client Socket 連線
	public static void ClientSock() throws IOException {
		try {
			//Client 連線
			Socket C1 = new Socket();
			
			C1.connect(new InetSocketAddress("127.0.0.1",9998),1);
			// This stops the request from dragging on after connection succeeds.
			//C1.setSoTimeout(10);
			
			//COMPUTERNAME Name
			String ComName =  System.getenv("COMPUTERNAME");
			if(Client.ServiceCheck() != null) {//0:OK , 1:Error
				String ServiceLine = ComName+","+Client.ServiceCheck()+","+"0";
				System.out.println(ServiceLine);
				//建立字串 
				BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
				DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
				Out.writeUTF(ServiceLine);//"Client :Test tmp";//傳送字串
				//C1.close();
			}
			else {//0:OK , 1:Error
				String ServiceLine = ComName+","+Client.ServiceCheck()+","+"1";
				System.out.println(ServiceLine);
				//建立字串
				BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
				DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
				Out.writeUTF(ServiceLine);//"Client :Test tmp";//傳送字串
				//C1.close();
			}
			/*
			//建立字串
			BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
			DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
			String tmp = ServiceLine;//"Client :Test tmp";//傳送字串
			//String tmp=  In.readLine();
			//	System.out.println("Test");
			Out.writeUTF(tmp);*/
	
			//Client 接收字串
			DataInputStream DinC = new DataInputStream(C1.getInputStream());
			String Re_tmp = new String(DinC.readUTF());//輸入串流轉換為物件
			String Array[] = new String[3];//字串分割與判斷Service OK or Error，若Error重啟服務，並回傳，OK不做事情
			Array = Re_tmp.split(",");//字串分割與判斷Service Ok or Error		
			int CK = Integer.parseInt(Array[3]);
			if(CK == 0) {//
				//System.out.println("This Host Service is OK.");
			}
			else {
				String CMD = "cmd /c net start branchcache";
				// 執行CMD指令
				Process PS = Runtime.getRuntime().exec(CMD);
				// Getting the results
				PS.getOutputStream().close();
				//建立字串 
				String ServiceLine = ComName+",service:branchcache,"+"2";
				BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
				DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
				Out.writeUTF(ServiceLine);//"Client :Test tmp";//傳送字串
				System.out.println(ServiceLine);
			}
			
			
			//關閉Socket
			C1.close();
			}catch(IOException e) {
			System.out.println("Connect Error....");
			}
		
	}
		
	//監控服務
	public static String ServiceCheck() throws IOException {

		//監控服務
		String CMD = "cmd /c sc query | find \"BranchCache\"";
		// 執行CMD指令
		Process PS = Runtime.getRuntime().exec(CMD);
		// Getting the results
		PS.getOutputStream().close();
		String line;
		//System.out.println("Standard Output:");
		BufferedReader stdout = new BufferedReader(new InputStreamReader(PS.getInputStream()));
		line = stdout.readLine();
		//while ((line = stdout.readLine()) != null) {
		//System.out.println(line);
		//}
		stdout.close();
		return line;
	}
		
}


