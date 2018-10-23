
public class GameWorldList {
	
	private String Host_Name , Host_Service , Service_Type;
	
	public GameWorldList (String Host_Name ,String Host_Service ,String Service_Type) {
		this.Host_Name = Host_Name;
		this.Host_Service = Host_Service;
		this.Service_Type = Service_Type;
	}
	public String getHost_Name(){
		return Host_Name;
	}
	public String getHost_Service(){
		return Host_Service;
	}
	public String getService_Type(){
		return Service_Type;
	}
}
