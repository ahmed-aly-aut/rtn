package ssh;

public class Main {
	public static void main(String[] args) {
		SSHManager ssh = new SSHManager("aaly", "Aly1234", "10.0.105.229",
				"/home/aaly/.ssh/known_hosts");
		ssh.connect();
		System.out.println("connected");
		System.out.println(ssh.sendCommand("cat test"));
		ssh.close();
		System.out.println("Connection closed");
	}

}
