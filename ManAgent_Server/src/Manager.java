import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import java.awt.Scrollbar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Popup;

import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;

public class Manager {

	private JFrame frmTt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager window = new Manager();
					window.frmTt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Manager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTt = new JFrame();
		frmTt.setTitle("Manager");
		frmTt.setResizable(false);
		frmTt.setBounds(100, 100, 725, 564);
		frmTt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		GroupLayout groupLayout = new GroupLayout(frmTt.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
		);
		
		JPanel Manager = new JPanel();
		tabbedPane.addTab("Manager", null, Manager, null);
		GroupLayout gl_Manager = new GroupLayout(Manager);
		gl_Manager.setHorizontalGroup(
			gl_Manager.createParallelGroup(Alignment.LEADING)
				.addGap(0, 694, Short.MAX_VALUE)
		);
		gl_Manager.setVerticalGroup(
			gl_Manager.createParallelGroup(Alignment.LEADING)
				.addGap(0, 496, Short.MAX_VALUE)
		);
		Manager.setLayout(gl_Manager);
		
		JPanel Monitor = new JPanel();
		tabbedPane.addTab("Monitor", null, Monitor, null);
		
		//Switch on off
		JLabel Switch = new JLabel();
		
		//Monitor_Button
		JButton Monitor_Button = new JButton("Monitor GO");
		Monitor_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				if(Monitor_Button.isSelected()) {
					
					Monitor_Button.setText("Monitor On");
					Monitor_Button.setSelected(false);
					Monitor_Button.setBackground(Color.green);
					//Monitor_Button.setSelected(false);
					Image img = new ImageIcon(this.getClass().getResource("/switch-on.png")).getImage();
					Switch.setIcon(new ImageIcon(img));
					
					Server.ServertSock();

				}
				else {
					Monitor_Button.setText("Monitor Off");
					Monitor_Button.setSelected(true);
					Monitor_Button.setBackground(Color.red);
					Image img = new ImageIcon(this.getClass().getResource("/switch-off.png")).getImage();
					Switch.setIcon(new ImageIcon(img));
				}
			}
		});
		Monitor_Button.setFont(new Font("微軟正黑體", Font.BOLD, 13));


	
		GroupLayout gl_Monitor = new GroupLayout(Monitor);
		gl_Monitor.setHorizontalGroup(
			gl_Monitor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Monitor.createSequentialGroup()
					.addContainerGap(533, Short.MAX_VALUE)
					.addGroup(gl_Monitor.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_Monitor.createSequentialGroup()
							.addComponent(Switch)
							.addGap(59))
						.addGroup(Alignment.TRAILING, gl_Monitor.createSequentialGroup()
							.addComponent(Monitor_Button, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_Monitor.setVerticalGroup(
			gl_Monitor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Monitor.createSequentialGroup()
					.addComponent(Switch)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Monitor_Button)
					.addContainerGap(459, Short.MAX_VALUE))
		);
		Monitor.setLayout(gl_Monitor);
		frmTt.getContentPane().setLayout(groupLayout);
	}
	
	//Server Socket 連線
	public static void ServertSock() {
		
		try {
			for(;;) {
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
			}
			}catch(IOException e) {
				//System.out.println("Error");
			}
		}
}
