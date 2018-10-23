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
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Popup;
import javax.swing.table.DefaultTableModel;

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Manager {

	private JFrame frmTt;
    private AtomicBoolean running = new AtomicBoolean(false);
    private JTable Manage_table;
    private JTextArea Monitor_Area;
    private JButton Monitor_button;
    private JButton ShowButton;
    private ResultSet ResultSet;

    
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
		frmTt.setBounds(100, 100, 742, 639);
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
		ShowButton = new JButton("Show All Host");
		ShowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MSGameWorldList();
				try {
					Show_GameWorld();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		});	
		ShowButton.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		
		//Monitor_button
		Monitor_button = new JButton("Monitor GO");
		Monitor_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
					if(Monitor_button.isSelected()) {
						Monitor_button.setText("Monitor On");
						Monitor_button.setSelected(false);
						//Monitor_Button.setBackground(Color.green);
						Image img = new ImageIcon(this.getClass().getResource("/switch-on.png")).getImage();
						//Switch.setIcon(new ImageIcon(img));	
						//Monitor_Area.append("On\n");	
						Monitor_Button_ActionPerformedS(e);
					}
					else {
						Monitor_button.setText("Monitor Off");
						Monitor_button.setSelected(true);
						//Monitor_Button.setBackground(Color.red);
						Image img = new ImageIcon(this.getClass().getResource("/switch-off.png")).getImage();
						//Switch.setIcon(new ImageIcon(img));
						Monitor_Button_ActionPerformedC(e);
					}
			}
		});
		Monitor_button.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		//------------------------------------------------------------------------------------------------------
		GroupLayout gl_Manager = new GroupLayout(Manager);
		gl_Manager.setHorizontalGroup(
			gl_Manager.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_Manager.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Manager.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_Manager.createSequentialGroup()
							.addComponent(ShowButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 414, Short.MAX_VALUE)
							.addComponent(Monitor_button, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_Manager.setVerticalGroup(
			gl_Manager.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Manager.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Manager.createParallelGroup(Alignment.BASELINE)
						.addComponent(ShowButton)
						.addComponent(Monitor_button, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addContainerGap())
		);
		//------------------------------------------------------------------------------------------------------
		//Monitor_Area
		Monitor_Area = new JTextArea("Monitor\u8A0A\u606F:\n", 5, 5);
		Monitor_Area.setLineWrap(true);
		Monitor_Area.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		scrollPane_2.setViewportView(Monitor_Area);
		
		//Manage_table
		Manage_table = new JTable();
		Manage_table.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		scrollPane_1.setViewportView(Manage_table);
		Manager.setLayout(gl_Manager);
		//Monitor_Area.append(Monitor_Button.getText());
		frmTt.getContentPane().setLayout(groupLayout);
		


		}
	
	//GameWorldList
	public ArrayList<GameWorldList> MSGameWorldList(){
		ArrayList<GameWorldList> GameWorldList = new ArrayList<>();
		try {
			//載入JDBC Driver 
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;"+ "databaseName=ManagerDB;user=sa;password=!QAZ2wsx;");
	        DatabaseMetaData metadata = conn.getMetaData();           
	        //System.out.println("Database Name: "+ metadata.getDatabaseProductName());
	        System.out.println("Connect MS DB");
	        Statement Statement = conn.createStatement();
	        String query = "SELECT * FROM [ManagerDB].[dbo].[Host_Service_Type]";
	        ResultSet = Statement.executeQuery(query);
	        GameWorldList List;
	        while (ResultSet.next()) {
	        	List = new GameWorldList(ResultSet.getString("Host_Name"),ResultSet.getString("Host_Service"),ResultSet.getString("Service_Type"));
	        	GameWorldList.add(List);
	            System.out.println(ResultSet.getString(1) + ", " + ResultSet.getString(2) + ", " + ResultSet.getString(3));
	        }
	        
		}catch(Exception x) {
			System.out.println("MS DB Connet Error....");
		}
		return GameWorldList;
	}
	
	//
	//Show Game World List
	public void Show_GameWorld() throws SQLException {
			ArrayList<GameWorldList> GameWorldList = new ArrayList<>();
			DefaultTableModel Model = (DefaultTableModel)Manage_table.getModel();
			Object[] row = new Object[4];

				row[0] = ResultSet.getString(1);
				row[1] = ResultSet.getString(2);
				row[2] = ResultSet.getString(3);
				row[3] = ResultSet.getString(3);
				Model.addRow(row);


		
	}

	//Socket 連線 Thread
    private void Monitor_Button_ActionPerformedS(ActionEvent evt) {//GEN-FIRST:event_Monitor_Button_ActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();     
        Monitor_Area.append("Monitor已開啟!!!\n");
        starter.interrupt();
    }//GEN-LAST:event_Monitor_Button_ActionPerforme
    
    //Socket 關閉連線 Thread
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
        			while (!Monitor_button.isSelected()) {
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


