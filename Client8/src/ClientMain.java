import java.net.SocketException;
import java.util.Set;

import application.Application;

public class ClientMain {
	
	private static final String defaultMode = "dual";
	
	public static void main(String[] args) throws SocketException {
		String serverIP;
		if(args.length > 0) {
			serverIP = args[0];
		}
		else {
			serverIP = "192.168.10.80";
		}
		Set<String> modes = Set.of("gui", "dual", "terminal");
		String mode = modes.stream().filter(modeName -> modeName.equals(System.getProperty("mode"))).findFirst().orElse(defaultMode);
		Application.run(serverIP, mode);
	}
}
