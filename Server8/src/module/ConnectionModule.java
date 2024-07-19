package module;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.nio.channels.*;
import java.time.*;
import java.util.*;

import application.Application;
import thread.*;


public class ConnectionModule {
	private static final int TIME_OUT_MS = 25;
	
	private static Selector selector;
	private static DatagramChannel serverChannel;
	private static SocketAddress serverSocketAddress;
	
	private static HttpClient httpClient;
	private static RefreshingThread refreshingThread;
	
	
	
	public static void turnOn() throws IOException {
		selector = Selector.open();
		serverChannel = DatagramChannel.open();
		serverChannel.configureBlocking(false);
		choosePort();
		serverChannel.register(selector, SelectionKey.OP_READ);
		httpClient = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20))
				.build();
	}
	
	
	private static void choosePort() throws IOException {
		int[] ports = new int[] {51000, 51128, 51256, 51384, 51512};
		int attempt = 0;
		final long RETRY_TIME = 1000;
		final long TIME_STEP = 2;
		while(!serverChannel.socket().isBound()) {
			for(int port: ports) {
				try {
					serverSocketAddress = new InetSocketAddress(port);
					serverChannel.bind(serverSocketAddress);
					break;
				}catch(BindException e) {
					Application.logger.error("Default port " + port + " occupied");
					continue;
				}
			}
			if(!serverChannel.socket().isBound()) {
				long delay = (long) (RETRY_TIME*Math.pow(TIME_STEP, attempt));
				Application.logger.info("Couldn't start the server. All default ports were occupied. Retrying in " + delay/1000 + " seconds...");
				try {
					Thread.sleep(delay);
					attempt++;
					continue;
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Application.logger.info("Server started on "  + serverSocketAddress);
	}
	
	private static void listen() throws IOException{
		selector.select(TIME_OUT_MS);
		Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
		while(selectedKeys.hasNext()) {
			action(selectedKeys.next());
			selectedKeys.remove();
		}
	}
	
	private static void action(SelectionKey selectedKey) {
		if(selectedKey.isReadable()) {
			ReadModule.readKey(selectedKey);
		}
	}
	
	public static void conversation() throws IOException {
		PacketAssemblingThread packetAssemblingThread = new PacketAssemblingThread();
		packetAssemblingThread.start();
		while(true) {
			listen();
			ResponseModule.respond();
		}
	}
	
	public static HttpClient getHttpClient() {
		return httpClient;
	}
	
	public static void startJsonRefresh() {
		if (refreshingThread == null) {
			refreshingThread = new RefreshingThread();
		}
		else {
			refreshingThread.interrupt();
			refreshingThread = new RefreshingThread();
		}
		refreshingThread.start();
	}
	
	public static long getLastJsonUpdate() {
		return refreshingThread.getLastUpdate();
	}
}
