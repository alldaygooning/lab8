package module;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

import attribute.TextColor;
import thread.*;
import wrapper.*;

public class ReadModule {
	public static ConcurrentMap<SocketAddress, WrapperAssembler> wrappers = new ConcurrentHashMap<>();
	public static ArrayList<String> blackList = new ArrayList<String>(); //Добавить отчистку старых списков
	public static BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<Runnable>(1000);
	public static void readKey(SelectionKey key) {
		ByteBuffer buffer = ByteBuffer.allocate(508);
		DatagramChannel clientChannel = (DatagramChannel) key.channel();
		try {
			SocketAddress clientAddress = clientChannel.receive(buffer);
			PacketAssemblingTask task = new PacketAssemblingTask(buffer, clientAddress, clientChannel);
			tasks.put(task);
			
		}catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static synchronized void read(ByteBuffer buffer, SocketAddress clientAddress, DatagramChannel clientChannel) {
		buffer.flip();
		byte[] receivedData = Arrays.copyOfRange(buffer.array(), 0, buffer.limit());
		Wrapper receivedWrapper = (Wrapper) getObjectFromBytes(receivedData);
		String requestID = getRequestID(clientAddress, receivedWrapper.t);
		
		if(!wrappers.containsKey(clientAddress) && !blackList.contains(requestID)) { 
			WrapperAssembler assembler = new WrapperAssembler();
			assembler.newExpectation(receivedWrapper.totalNumber);
			assembler.newTime(receivedWrapper.t);
			wrappers.put(clientAddress, assembler);
			System.out.println(TextColor.bold("Added entry for " + clientAddress));
		}
		else if(wrappers.containsKey(clientAddress) && wrappers.get(clientAddress).requestTime != receivedWrapper.t) {
			WrapperAssembler assembler = new WrapperAssembler();
			assembler.newExpectation(receivedWrapper.totalNumber);
			assembler.newTime(receivedWrapper.t);
			wrappers.replace(clientAddress, assembler);
			System.out.println("Readded entry for " + clientAddress);
		}
		
		WrapperAssembler assembler = wrappers.get(clientAddress);
		
		if (assembler == null) {
			return;
		}
		
		if(assembler.nextExpected == receivedWrapper.currentNumber) {
			assembler.addData(receivedWrapper.storedData);
			System.out.println(Instant.now()  + " " + Thread.currentThread().getName() + " " + assembler.currentTotal + "/" + assembler.totalExpected);
		}
		else {
			blackList.add(requestID);
			wrappers.remove(clientAddress);
			ResponseModule.responses.add(new ResponsePackage(new Response(false, 1), clientAddress, clientChannel));
			System.out.println(Instant.now() + " " +Thread.currentThread().getName() + " blacklisted " + requestID + " because expected: " + assembler.nextExpected + " received: " + receivedWrapper.currentNumber );
			return;
		}
		
		if(assembler.checkIfAllCollected()) {
			wrappers.remove(clientAddress);
			Request request = (Request) getObjectFromBytes(assembler.data);
			System.out.println(TextColor.faint(request.login + " " + request.password + " " + request.command.getClass() + " " + request.args));
			CommandModule.executor().execute(new CommandExecutingTask(request, clientAddress, clientChannel));
		}
	}
	
	
	
	private static Object getObjectFromBytes(byte[] data){
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return(objectInputStream.readObject());
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}return null;
	}
	
	private static String getRequestID(SocketAddress clientAddress, long time) {
		return (clientAddress.toString() + String.valueOf(time));
	}
}
