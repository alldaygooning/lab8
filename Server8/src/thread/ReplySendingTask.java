package thread;

import java.io.IOException;

import module.ResponseModule;
import wrapper.*;

public class ReplySendingTask implements Runnable{

	@Override
	public void run() {
		try {
			ResponsePackage responsePackage = ResponseModule.responses.take();
			ResponseModule.sendResponse(responsePackage.response, responsePackage.clientAddress, responsePackage.clientChannel);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
