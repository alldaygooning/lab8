package wrapper;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class ResponsePackage {
	public Response response;
	public SocketAddress clientAddress;
	public DatagramChannel clientChannel;
	public ResponsePackage(Response response, SocketAddress clientAddress, DatagramChannel clientChannel) {
		this.response = response;
		this.clientAddress = clientAddress;
		this.clientChannel = clientChannel;
	}
	
	
}
