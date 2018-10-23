import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class Chart {
	
	private JFrame frame;
	ResultSet ResultSet;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chart window = new Chart();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Chart() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(true);
		frame.setBounds(100, 100, 746, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setForeground(Color.GRAY);
		
		
		
		JButton btnTest = new JButton("\u76F4\u7DDA\u5716");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				//¸ü¤JJDBC Driver 
		        try {
		        	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;"+ "databaseName=ManagerDB;user=sa;password=!QAZ2wsx;");
			        DatabaseMetaData metadata = conn.getMetaData();           
			        //System.out.println("Database Name: "+ metadata.getDatabaseProductName());
			        System.out.println("Connect MS DB");
			        Statement Statement = conn.createStatement();
			        String query = "SELECT [MachineName],count([MachineName]) as'Crash_Count' FROM [MngDB2].[dbo].[GameCrashReport] Where [TimeCreated] >= '2018-04-05 05:11:07.0000000' and [TimeCreated] <='2018-04-10 05:11:07.0000000' Group By [MachineName]";
			        ResultSet = Statement.executeQuery(query);
			        while (ResultSet.next()) {
			        	int Column = Integer.parseInt(ResultSet.getString(2));
			            System.out.println(ResultSet.getString(1)+","+ResultSet.getString(2));
			            dataset.setValue(Column,ResultSet.getString(1),ResultSet.getString(1));
			        }

					JFreeChart Chart = ChartFactory.createBarChart3D("Lineage M GameWorld Crash Chart", "GameWorld", "Crash_Count", dataset, PlotOrientation.VERTICAL, true, true, false);
					CategoryPlot catPlot = Chart.getCategoryPlot();
					catPlot.setRangeGridlinePaint(Color.BLACK);
					//ChartFrame chartFrm = new ChartFrame("Lineage M GameWorld Crah Grapth", Chart, true);
					//chartFrm.setVisible(true);
					//chartFrm.setSize(500, 400);
					ChartPanel chartPanel = new ChartPanel(Chart);
					panel.removeAll();
					panel.add(chartPanel);
					panel.updateUI();
				} catch (ClassNotFoundException e1) {
					System.out.println("MS DB Connet Error....");
					e1.printStackTrace();
				} catch (SQLException e1) {
					System.out.println("MS DB Connet Error....");
					e1.printStackTrace();
				}
		        /*
				for(int i=1;i<=10;i++) {
					dataset.setValue(i, "Point", "People_"+i);
					//dataset.setValue(60, "Point", "java");
				}*/

			}
		});
		
		JButton btnNewButton = new JButton("\u6298\u7DDA\u5716");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
 		        try {
		        	DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		        	//¸ü¤JJDBC Driver
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;"+ "databaseName=ManagerDB;user=sa;password=!QAZ2wsx;");
			        DatabaseMetaData metadata = conn.getMetaData();           
			        //System.out.println("Database Name: "+ metadata.getDatabaseProductName());
			        System.out.println("Connect MS DB");
			        Statement Statement = conn.createStatement();
			        //String query = "SELECT [MachineName],convert(varchar(6), [TimeCreated], 112) as'Log'into #tmp1 FROM [MngDB2].[dbo].[GameCrashReport] Where convert(varchar(6), [TimeCreated], 112) >= '201711' and convert(varchar(6), [TimeCreated], 112) <= '201806' and [MachineName] = 'Game57.LineageM.TW' Group By [MachineName],[TimeCreated]";
			        String query1 = "SELECT [MachineName],convert(varchar(6), [TimeCreated], 112) as'Log'into #tmp1 FROM [MngDB2].[dbo].[GameCrashReport] Where convert(varchar(6), [TimeCreated], 112) >= '201711' and convert(varchar(6), [TimeCreated], 112) <= '201806' and [MachineName] in('Game57.LineageM.TW','Game39.LineageM.TW') Group By [MachineName],[TimeCreated] Select [MachineName],[Log],Count([Log]) From #tmp1 Group By [MachineName],[Log]";
			        ResultSet = Statement.executeQuery(query1);
			        while (ResultSet.next()) {
			        	int Column = Integer.parseInt(ResultSet.getString(3));
			            System.out.println(ResultSet.getString(1)+","+ResultSet.getString(2)+","+ResultSet.getString(3));
			            dataset2.setValue(Column, ResultSet.getString(1), ResultSet.getString(2));
			        }

					JFreeChart Chart1 = ChartFactory.createLineChart("Lineage M GameWorld Crash Chart", "Date", "Crash_Count", dataset2, PlotOrientation.VERTICAL, true, true, false);
					CategoryPlot catPlot = Chart1.getCategoryPlot();
					catPlot.setRangeGridlinePaint(Color.BLACK);
					//ChartFrame chartFrm = new ChartFrame("Lineage M GameWorld Crah Grapth", Chart, true);
					//chartFrm.setVisible(true);
					//chartFrm.setSize(500, 400);
					ChartPanel chartPanel = new ChartPanel(Chart1);
					panel.removeAll();
					panel.add(chartPanel);
					panel.updateUI();
				} catch (ClassNotFoundException e1) {
					System.out.println("MS DB Connet Error....");
					e1.printStackTrace();
				} catch (SQLException e1) {
					System.out.println("MS DB Connet Error....");
					e1.printStackTrace();
				}
		        /*
				for(int i=1;i<=10;i++) {
					dataset.setValue(i, "Point", "People_"+i);
					//dataset.setValue(60, "Point", "java");
				}*/

			}
			
		});
		
		
		
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnTest)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTest)
						.addComponent(btnNewButton))
					.addGap(30))
		);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		frame.getContentPane().setLayout(groupLayout);
	}
}


