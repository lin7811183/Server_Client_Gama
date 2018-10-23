import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Test {
	
	

	public static void main(String[] args) {
		ResultSet ResultSet;
		// TODO Auto-generated method stub
		try {
			//¸ü¤JJDBC Driver 
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost;"+ "databaseName=ManagerDB;user=sa;password=!QAZ2wsx;");
	        DatabaseMetaData metadata = conn.getMetaData();           
	        //System.out.println("Database Name: "+ metadata.getDatabaseProductName());
	        System.out.println("Connect MS DB");
	        Statement Statement = conn.createStatement();
	        String query = "SELECT [MachineName],count([MachineName]) as'Crash_Count' FROM [MngDB2].[dbo].[GameCrashReport] Where [TimeCreated] >= '2018-04-05 05:11:07.0000000' and [TimeCreated] <='2018-04-10 05:11:07.0000000' Group By [MachineName]";
	        ResultSet = Statement.executeQuery(query);
	        while (ResultSet.next()) {
	            System.out.println(ResultSet.getString(1)+","+ResultSet.getString(2));
	        }
	        
		}catch(Exception x) {
			System.out.println("MS DB Connet Error....");
		}
		
	}

}
