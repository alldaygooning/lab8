package module;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.time.Duration;
import java.util.ArrayList;

import application.MusicBandHandler;
import command.*;
import exception.*;
import thread.RefreshingThread;
import wrapper.*;


public class ConnectionModule {
	
	private static final int TIME_OUT_MS = 1000;
	
	private static InetSocketAddress serverAddress;
	static DatagramSocket clientSocket;
	private static String serverIP;
	private static int lastKnownPort;
	
	private static HttpClient httpClient;
	public static RefreshingThread refreshingThread;
	
	public static void turnOn(String serverIP) throws SocketException {
		clientSocket = new DatagramSocket();
		clientSocket.setSoTimeout(TIME_OUT_MS);
		ConnectionModule.serverIP = serverIP;
		choosePort();
		httpClient = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20))
				.build();;
	}
	
	
	
	
	private static void choosePort() {
		int[] ports = new int[] {51000, 51128, 51256, 51384, 51512};
		for (int port: ports) {
			try {
				serverAddress = new InetSocketAddress(serverIP, port);
				clientSocket.connect(serverAddress);
				connect();
				lastKnownPort = port;
				break;
			}catch(SocketException e) {
				e.printStackTrace();
			}catch(ConnectionException e) {
				clientSocket.disconnect();
				continue;
			}
		}
	}
	
	private static void connect() {
		SendModule.sendRequest(new Request(new ConnectCommand(), new ArrayList<String>()));
		try {
			Response response = ReceiveModule.receiveResponse();
			if (!response.successful) {
				throw new ConnectionException();
			}
			if(refreshingThread != null) {
				continueJsonRefresh();
			}
		}catch(SocketTimeoutException e) {
			throw new ConnectionException();
		}
	}
	
	public static void reconnectOnLastKnownPort() {
		if (lastKnownPort != 0) {
			try {
				serverAddress = new InetSocketAddress(serverIP, lastKnownPort);
				clientSocket.connect(serverAddress);
				connect();
			}catch(SocketException e) {
				e.printStackTrace();
			}catch(ConnectionException e) {
				clientSocket.disconnect();
			}
		}
	}
	
	public static boolean isConnected() {
		return clientSocket.isConnected();
	}
	
	public static void reconnect() {
		choosePort();
	}
	
	public static void disconnect() {
		clientSocket.disconnect();
	}
	
	public static HttpClient getHttpClient() {
		return httpClient;
	}
	
	public static void startJsonRefresh(MusicBandHandler musicBandHandler) {
		refreshingThread = new RefreshingThread();
		refreshingThread.setHandler(musicBandHandler);
		refreshingThread.start();
	}
	
	public static void restartJsonRefresh() {
		refreshingThread.interrupt();
		refreshingThread = new RefreshingThread();
		refreshingThread.start();
	}

	public static void continueJsonRefresh() {
		synchronized(refreshingThread) {
			refreshingThread.notify();
		}
		System.out.println("JSON refresh is online again");
	}
}
