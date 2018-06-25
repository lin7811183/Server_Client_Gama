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
	
	//Client Socket �s�u
	public static void ClientSock() throws IOException {
		try {
			//Client �s�u
			Socket C1 = new Socket();
			
			C1.connect(new InetSocketAddress("127.0.0.1",9998),1);
			// This stops the request from dragging on after connection succeeds.
			//C1.setSoTimeout(10);
			
			//COMPUTERNAME Name
			String ComName =  System.getenv("COMPUTERNAME");
			if(Client.ServiceCheck() != null) {//0:OK , 1:Error
				String ServiceLine = ComName+","+Client.ServiceCheck()+","+"0";
				System.out.println(ServiceLine);
				//�إߦr�� 
				BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
				DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
				Out.writeUTF(ServiceLine);//"Client :Test tmp";//�ǰe�r��
				//C1.close();
			}
			else {//0:OK , 1:Error
				String ServiceLine = ComName+","+Client.ServiceCheck()+","+"1";
				System.out.println(ServiceLine);
				//�إߦr��
				BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
				DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
				Out.writeUTF(ServiceLine);//"Client :Test tmp";//�ǰe�r��
				//C1.close();
			}
			/*
			//�إߦr��
			BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
			DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
			String tmp = ServiceLine;//"Client :Test tmp";//�ǰe�r��
			//String tmp=  In.readLine();
			//	System.out.println("Test");
			Out.writeUTF(tmp);*/
	
			//Client �����r��
			DataInputStream DinC = new DataInputStream(C1.getInputStream());
			String Re_tmp = new String(DinC.readUTF());//��J��y�ഫ������
			String Array[] = new String[3];//�r����λP�P�_Service OK or Error�A�YError���ҪA�ȡA�æ^�ǡAOK�����Ʊ�
			Array = Re_tmp.split(",");//�r����λP�P�_Service Ok or Error		
			int CK = Integer.parseInt(Array[3]);
			if(CK == 0) {//
				//System.out.println("This Host Service is OK.");
			}
			else {
				String CMD = "cmd /c net start branchcache";
				// ����CMD���O
				Process PS = Runtime.getRuntime().exec(CMD);
				// Getting the results
				PS.getOutputStream().close();
				//�إߦr�� 
				String ServiceLine = ComName+",service:branchcache,"+"2";
				BufferedReader In = new BufferedReader (new InputStreamReader(System.in));
				DataOutputStream Out = new DataOutputStream(C1.getOutputStream());
				Out.writeUTF(ServiceLine);//"Client :Test tmp";//�ǰe�r��
				System.out.println(ServiceLine);
			}
			
			
			//����Socket
			C1.close();
			}catch(IOException e) {
			System.out.println("Connect Error....");
			}
		
	}
		
	//�ʱ��A��
	public static String ServiceCheck() throws IOException {

		//�ʱ��A��
		String CMD = "cmd /c sc query | find \"BranchCache\"";
		// ����CMD���O
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


