package Entity;

public class Server {
	private int serverId;
	private String ip;
	private int port;
	
	public Server(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public String toString() {
		return "Server [serverId=" + serverId + ", ip=" + ip + ", port=" + port + "]";
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
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
