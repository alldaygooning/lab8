package thread;

import java.net.*;
import java.nio.*;
import java.nio.channels.DatagramChannel;

import module.ReadModule;

public class PacketAssemblingTask implements Runnable{
	ByteBuffer buffer;
	SocketAddress clientAddress;
	DatagramChannel clientChannel;
	public PacketAssemblingTask(ByteBuffer buffer, SocketAddress clientAddress, DatagramChannel clientChannel) {
		this.buffer = buffer;
		this.clientAddress = clientAddress;
		this.clientChannel = clientChannel;
	}
	
	@Override
	public void run() {
		ReadModule.read(buffer, clientAddress, clientChannel);
	}
}
