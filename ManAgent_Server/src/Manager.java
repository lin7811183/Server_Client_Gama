import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.net.SocketFactory;
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
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
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
import javax.swing.JTable;
import java.sql.*;

public class Manager {

	private JFrame frmTt;
	private JTextArea Monitor_Area;
    private JButton Monitor_Button;
    private AtomicBoolean running = new AtomicBoolean(false);
    private JTable Manage_table;
    private Connection Con;
    private Statement ST;
    private Statement SQLStatement;
    private ResultSet RS;
    
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
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		//Show Table Button
		JButton ShowButton = new JButton("Show All Host");
		ShowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final String fileName = "D:/Test/Test_Manage.mdb";
				try {
					//載入JDBC Driver 
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					//連結 ODBC 設定
					//String URL = "jdbc:odbc:Driver={Microthreadsoft Access Driver (*.mdb, *.accdb)};DBQ="+fileName;
					//System.out.println(URL);
					//Con = DriverManager.getConnection(URL,"","");
					//ST = Con.createStatement();
					// 其後由此 Statement 物件執行 SQL 指令時，回傳的會是可捲動且唯讀的 ResultSet 物件
					//SQLStatement = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
					//String query = "Select * From Manage_Host";
					//RS = ST.executeQuery(query);
				}catch(Exception x) {
					System.out.println("MS AccessDB Connet Error....");
				}
				
			}
		});
		
		
		
		ShowButton.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		GroupLayout gl_Manager = new GroupLayout(Manager);
		gl_Manager.setHorizontalGroup(
			gl_Manager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Manager.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Manager.createParallelGroup(Alignment.LEADING)
						.addComponent(ShowButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_Manager.setVerticalGroup(
			gl_Manager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Manager.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(ShowButton)
					.addContainerGap(183, Short.MAX_VALUE))
		);
		
		Manage_table = new JTable();
		Manage_table.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		scrollPane_1.setViewportView(Manage_table);
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
					Monitor_Button_ActionPerformedC(e);
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
        Monitor_Area.append("Monitor已開啟!!!\n");
        starter.interrupt();
    }//GEN-LAST:event_Monitor_Button_ActionPerforme
    
    private void Monitor_Button_ActionPerformedC(ActionEvent evt) {//GEN-FIRST:event_Monitor_Button_ActionPerformed
        Thread closer = new Thread(new ServerColse());
        closer.start(); 
        closer.interrupt();  
        Monitor_Area.append("Monitor已關閉!!!\n");

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
			//Get System Time
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		try {
        			//Server Socket 連線
        			ServerSocket Server = new ServerSocket(9998);

        			
        			loop:  		
        			while (!Monitor_Button.isSelected()) {
        				Monitor_Area.append("Moniotr is listenning....\n");
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
        					String Re_Temp = "Server,"+Array[0]+","+Array[1]+",0";
        					Out.writeUTF(Re_Temp);
        					//關閉Socket
            				S1.close();
        					continue loop;
        				}
        				else if(CK == 1)  {//0:OK , 1:Error
        					DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server 輸出字串
        					String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",1";
        					Monitor_Area.append(df.format(new Date())+","+Array[0]+",branchcache,Crash\n");//產出Monitor字串
        					Out.writeUTF(Re_Temp);
        					//System.out.println("Test1");
        					DataOutputStream Out2 = new DataOutputStream(S1.getOutputStream());//Server 輸出字串
        					String Re_Temp2 = "Server,"+Array[0]+","+Array[1]+",3";
        					Monitor_Area.append(df.format(new Date())+Array[0]+","+",branchcache,Restart_OK\n");//產出Monitor字串
        					//關閉Socket
            				S1.close();
        					continue loop;
        				}
  	   			  				
        				//關閉Socket
        				S1.close();
        			}
        		}catch(IOException e) {
        			//System.out.println("Error");
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
                Thread.sleep(10);
            } 
            catch(InterruptedException e) { 
                System.out.println("Stop Socket...."); 
            } 
        }
    }
    
    
    
}


