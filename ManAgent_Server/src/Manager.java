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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Manager {

	private JFrame frmTt;
	private JTextArea Monitor_Area;
    private JButton Monitor_Button;
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
		frmTt.setBounds(100, 100, 725, 611);
		frmTt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		tabbedPane.setBounds(100, 100, 725, 611);
		
		GroupLayout groupLayout = new GroupLayout(frmTt.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 699, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 562, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel Manager = new JPanel();
		Manager.setBounds(100, 100, 725, 611);
		
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
		Monitor.setBounds(100, 100, 725, 611);
		
		//Switch on off
		JLabel Switch = new JLabel();
		
		//Monitor_Button
		Monitor_Button = new JButton("Monitor GO");
		Monitor_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				if(Monitor_Button.isSelected()) {
					Monitor_Button.setText("Monitor On");
					Monitor_Button.setSelected(false);
					//Monitor_Button.setBackground(Color.green);
					Image img = new ImageIcon(this.getClass().getResource("/switch-on.png")).getImage();
					Switch.setIcon(new ImageIcon(img));	
					//Monitor_Area.append("On\n");	
					Monitor_Button_ActionPerformedS(e);
				}
				else {
					Monitor_Button.setText("Monitor Off");
					Monitor_Button.setSelected(true);
					//Monitor_Button.setBackground(Color.red);
					Image img = new ImageIcon(this.getClass().getResource("/switch-off.png")).getImage();
					Switch.setIcon(new ImageIcon(img));
					//Monitor_Button_ActionPerformedC(e);
					Monitor_Area.append("Monitor已關閉!!!.\n");
				}
			}
		});
		Monitor_Button.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		
		JLabel lblNewLabel = new JLabel("Monitor Token");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		

		//------------------------------------------------------------------------------------------------------
		GroupLayout gl_Monitor = new GroupLayout(Monitor);
		gl_Monitor.setHorizontalGroup(
			gl_Monitor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Monitor.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Monitor.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Monitor.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Monitor.createSequentialGroup()
							.addGap(56)
							.addComponent(Switch, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addComponent(Monitor_Button, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_Monitor.setVerticalGroup(
			gl_Monitor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Monitor.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addGroup(gl_Monitor.createParallelGroup(Alignment.LEADING)
						.addComponent(Switch, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Monitor.createParallelGroup(Alignment.LEADING)
						.addComponent(Monitor_Button)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 468, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		//------------------------------------------------------------------------------------------------------
		Monitor_Area = new JTextArea("Monitor訊息:\n",5,5);
		Monitor_Area.setLineWrap(true);//自動換行
		scrollPane.setViewportView(Monitor_Area);
		Monitor_Area.setLineWrap(true);//自動換行
		Monitor_Area.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		Monitor_Area.setBounds(10,10,200,60);
		Monitor_Area.setBounds(10,10,200,60);
		Monitor.setLayout(gl_Monitor);
		//Monitor_Area.append(Monitor_Button.getText());
		frmTt.getContentPane().setLayout(groupLayout);
		
		}
	
    private void Monitor_Button_ActionPerformedS(ActionEvent evt) {//GEN-FIRST:event_Monitor_Button_ActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();  
        Monitor_Area.append("Monitor已開啟!!!.\n");
    }//GEN-LAST:event_Monitor_Button_ActionPerforme
    
    private void Monitor_Button_ActionPerformedC(ActionEvent evt) {//GEN-FIRST:event_Monitor_Button_ActionPerformed
        Thread closer = new Thread(new ServerColse());
        closer.start(); 
        Monitor_Area.append("Monitor已關閉!!!.\n");
    }//GEN-LAST:event_Monitor_Button_ActionPerforme

	private void add(JTextArea monitor_Area) {
		// TODO Auto-generated method stub
		
	}
	

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
	
	//Server Socket 開啟連線 Class
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {	
        	for(int i = 1;i<=999999999;i++) {
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
        				System.out.println(i);
        			}
        			else {//0:OK , 1:Error
        				DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server 輸出字串
        				String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",1";
        				Monitor_Area.append(Array[0]+",branchcache,Error\n");//產出Monitor字串
        				Out.writeUTF(Re_Temp);
        				System.out.println(i);
        			}
        			//Server接收Serviec重啟訊息
        			DataInputStream DinS2 = new DataInputStream(S1.getInputStream());
        			String tmp2 = new String(DinS2.readUTF());//輸入串流轉換為物件
    				Monitor_Area.append(tmp2);//產出Monitor字串
    	   			
    				
        			//關閉Socket
        			S1.close();
    				System.out.println(i);
    				}catch(IOException e) {
    				//System.out.println("Error");
    				}
        		}
        }
    }
    
    //Server Socket 關閉連線 Class
    public class ServerColse implements Runnable 
    {
        @Override
        public void run() 
        {	
        		try { 			
        			//Server Socket 連線
        			ServerSocket Server = new ServerSocket(9998);
        			System.out.println("Server Monitor Port Close");
        			Socket S1 = Server.accept();
        			//關閉Socket
        			S1.close(); 			
    				}catch(IOException e) {
    				//System.out.println("Error");
    				}
        }
    }
	
}


