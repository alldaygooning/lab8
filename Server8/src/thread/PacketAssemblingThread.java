package thread;

import module.ReadModule;

public class PacketAssemblingThread extends Thread{
	
	public PacketAssemblingThread() {
	
	}
	
	
	@Override
	public void run(){
		Thread.currentThread().setName("Packeto-3000");
		while(true) {
			try {
				Thread thread = new Thread (ReadModule.tasks.take());
				thread.start();
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
