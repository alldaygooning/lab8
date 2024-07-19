package module;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.*;

import application.Application;
import thread.ReplySendingTask;
import wrapper.*;

public class ResponseModule {
	public static final BlockingQueue<ResponsePackage> responses = new ArrayBlockingQueue<ResponsePackage>(1000);
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/4);
	
	
	public static void sendResponse(Response response, SocketAddress clientAddress, DatagramChannel clientChannel) throws IOException {
		
		final int PACKET_SIZE = 380;
		
		ByteBuffer sendBuffer = ByteBuffer.allocate(508);
		byte[] responseAsBytes = getObjectAsBytes(response);
		int packetsAmount = (1 + responseAsBytes.length/(PACKET_SIZE));
		int leftovers = responseAsBytes.length - (packetsAmount-1)*PACKET_SIZE;
		Application.logger.info("Sending reply to " + clientAddress + " ("+packetsAmount+" packets)");
		if (packetsAmount > 100) {
			Application.logger.warn("Reply message approaching packetsAmount ("+ packetsAmount + "/" + 128 +
					":\n" + response);
		}
		for (int i = 1; i <= packetsAmount; i++) {
			Wrapper sendWrapper = new Wrapper(packetsAmount, i, Arrays.copyOfRange(responseAsBytes, ((i-1)*PACKET_SIZE), (i != packetsAmount)?(i*PACKET_SIZE):((i-1)*PACKET_SIZE)+leftovers));
			byte[] sendData = getObjectAsBytes(sendWrapper);
			sendBuffer.clear();
			sendBuffer.put(sendData);
			sendBuffer.flip();
			clientChannel.send(sendBuffer, clientAddress);
			Application.logger.info(i + "/" + packetsAmount + " packets");
		}
		Application.logger.info("Sent reply to " + clientAddress.toString());
	}
	
	
	
	
	private static byte[] getObjectAsBytes(Serializable object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
		return(byteArrayOutputStream.toByteArray());
	}
	
	public static void respond() {
		int amountOfThreads = responses.size();
		for (int i = 0; i < amountOfThreads; i++) {
			executor.execute(new ReplySendingTask());
		}
	}
}
