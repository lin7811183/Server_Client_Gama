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

public class Manager {

	private JFrame frmTt;
	private JTextArea Monitor_Area;
    private JButton Monitor_Button;
    private AtomicBoolean running = new AtomicBoolean(false);
    
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
		tabbedPane.setFont(new Font("�L�n������", Font.BOLD, 14));
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
					Monitor_Button_ActionPerformedC(e);
				}
			}
		});
		Monitor_Button.setFont(new Font("�L�n������", Font.BOLD, 13));
		
		JLabel lblNewLabel = new JLabel("Monitor Token");
		lblNewLabel.setFont(new Font("�L�n������", Font.BOLD, 14));
		
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
		Monitor_Area = new JTextArea("Monitor�T��:\n",5,5);
		Monitor_Area.setLineWrap(true);//�۰ʴ���
		scrollPane.setViewportView(Monitor_Area);
		Monitor_Area.setLineWrap(true);//�۰ʴ���
		Monitor_Area.setFont(new Font("�L�n������", Font.BOLD, 13));
		Monitor_Area.setBounds(10,10,200,60);
		Monitor_Area.setBounds(10,10,200,60);
		Monitor.setLayout(gl_Monitor);
		//Monitor_Area.append(Monitor_Button.getText());
		frmTt.getContentPane().setLayout(groupLayout);
		
		}
	
    private void Monitor_Button_ActionPerformedS(ActionEvent evt) {//GEN-FIRST:event_Monitor_Button_ActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();     
        Monitor_Area.append("Monitor�w�}��!!!\n");
    }//GEN-LAST:event_Monitor_Button_ActionPerforme
    
    private void Monitor_Button_ActionPerformedC(ActionEvent evt) {//GEN-FIRST:event_Monitor_Button_ActionPerformed
        Thread closer = new Thread(new ServerColse());
        closer.start(); 
        closer.interrupt();  
        Monitor_Area.append("Monitor�w����!!!\n");

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
	
	//Server Socket �}�ҳs�u Class
    public class ServerStart implements Runnable 
    { 	
        @Override
        public void run() 
        {	
			//Get System Time
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		try {
        			//Server Socket �s�u
        			ServerSocket Server = new ServerSocket(9998);
        			
        			
        			loop:  		
        			while (!Monitor_Button.isSelected()) {
        				Monitor_Area.append("Moniotr is Waiting for connection....\n");
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
        					String Re_Temp = "Server,"+Array[0]+","+Array[1]+",0";
        					Out.writeUTF(Re_Temp);
        					//����Socket
            				S1.close();
        					continue loop;
        				}
        				else if(CK == 1)  {//0:OK , 1:Error
        					DataOutputStream Out = new DataOutputStream(S1.getOutputStream());//Server ��X�r��
        					String Re_Temp = "Server_to_Client,"+Array[0]+","+Array[1]+",1";
        					Monitor_Area.append(df.format(new Date())+","+Array[0]+",branchcache,Crash\n");//���XMonitor�r��
        					Out.writeUTF(Re_Temp);
        					//System.out.println("Test1");
        					DataOutputStream Out2 = new DataOutputStream(S1.getOutputStream());//Server ��X�r��
        					String Re_Temp2 = "Server,"+Array[0]+","+Array[1]+",3";
        					Monitor_Area.append(df.format(new Date())+Array[0]+","+",branchcache,Restart_OK\n");//���XMonitor�r��
        					//����Socket
            				S1.close();
        					continue loop;
        				}
  	   			  				
        				//����Socket
        				S1.close();
        			}
        		}catch(IOException e) {
        			//System.out.println("Error");
    			}
        	}
        }
    
    //Server Socket �����s�u Class
    public class ServerColse implements Runnable 
    {
        @Override
        public void run() 
        {	
        	try { 
                Thread.sleep(999);
            } 
            catch(InterruptedException e) { 
                System.out.println("Stop Socket...."); 
            } 
        }
    }
	
}


