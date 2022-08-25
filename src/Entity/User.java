package Entity;

public class User {
	private int userId;
	private String username;
	private String password;
	private String fullName;
	private int status;
	private String ip;
	private int port;

	public User(String username, String password, String fullName, int status, String ip, int port) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.status = status;
		this.ip = ip;
		this.port = port;
	}

	public User() {
		
	}

	@Override
	public String toString() {
		if(status == 1) {
			return "<html><font color=green>•</font> " + fullName +"</html>";
		}
		return "<html><font color=#999999>•</font> " + fullName +"</html>";
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
