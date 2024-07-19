package module;

import java.io.*;
import java.net.DatagramPacket;
import java.net.PortUnreachableException;
import java.time.Instant;
import java.util.*;

import exception.ConnectionException;
import wrapper.*;


public class SendModule {
	
	public static void sendRequest(Request request) {
		final int PACKET_SIZE = 380;
		
		Instant instant = Instant.now();
		long time = instant.toEpochMilli();
		byte[] objectAsBytes = getObjectAsBytes(request);
		
		int packetsAmount = (1 + (objectAsBytes.length/(PACKET_SIZE)));
		int leftovers = objectAsBytes.length - (packetsAmount-1)*PACKET_SIZE;	
		
		for (int i = 1; i <= packetsAmount; i++) {
			byte[] tmp = Arrays.copyOfRange(objectAsBytes, ((i-1)*PACKET_SIZE), (i != packetsAmount)?(i*PACKET_SIZE):((i-1)*PACKET_SIZE)+leftovers);
			Wrapper sendWrapper = new Wrapper(time, packetsAmount, i, tmp);
			byte[] sendData = getObjectAsBytes(sendWrapper);
			if(gremlin()) {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
				try {
					ConnectionModule.clientSocket.send(sendPacket);
				}catch(IllegalArgumentException | PortUnreachableException e) {
					System.out.println("SendModule: cannot send");
					throw new ConnectionException();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static byte[] getObjectAsBytes(Serializable object) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return(byteArrayOutputStream.toByteArray());
	}
	
	
	private static boolean gremlin() { //Симуляция потери фрагмента сообщения
		final double CHANCE = 0;
		Random random = new Random();
		if(random.ints(1, 0, 100).findFirst().getAsInt() < CHANCE) {
			return(false);
		}
		return(true);
	}
	

}
