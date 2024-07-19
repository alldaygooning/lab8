package module;


import java.io.*;
import java.net.DatagramPacket;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import exception.ConnectionException;
import wrapper.*;

public class ReceiveModule {
	
	public static Response receiveResponse() throws SocketTimeoutException{
		DatagramPacket receivePacket;
		WrapperAssembler wrapperAssembler = new WrapperAssembler();
		List<Byte> bytes;
		
		Response response = null;
		
		while(response == null) {
			try {
				receivePacket = new DatagramPacket(new byte[508], 508);
				ConnectionModule.clientSocket.receive(receivePacket);
				bytes = new ArrayList<Byte>(Arrays.asList(ArrayUtils.toObject(receivePacket.getData())));
				while((bytes.size() > 0)&&(bytes.get(bytes.size()-1) == 0)) {
					bytes.remove(bytes.size()-1);
				}
				
				byte[] receiveData = new byte[508];
				int i = 0;
				for(Byte current:bytes) {
					receiveData[i++] = current;
				}
				Wrapper receiveWrapper = (Wrapper) getObjectFromBytes(receiveData);
				if(wrapperAssembler.nextExpected == receiveWrapper.currentNumber) {
					wrapperAssembler.addData(receiveWrapper.storedData);
					if(wrapperAssembler.currentTotal == receiveWrapper.totalNumber) {
						response = (Response) getObjectFromBytes(wrapperAssembler.data);
					}
				}
			}catch(SocketTimeoutException e) {
				throw new SocketTimeoutException();
			}catch(PortUnreachableException e) {
				throw new ConnectionException();
			}catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return response;
		
	}
	
	
	
	private static Object getObjectFromBytes(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		Object object = objectInputStream.readObject();
		return(object);
	}
}