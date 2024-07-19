package application;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import collection.CollectionHandlerServer;
import item.User;
import module.*;
import sun.misc.Signal;

public class Application {
	
	volatile public static CollectionHandlerServer MusicBandHandler = new CollectionHandlerServer();
	public static final Logger logger = (Logger) LogManager.getLogger(Application.class);
	
	public static void run() throws IOException, SQLException {
		Signal.handle(new Signal("INT"), 
				signal -> {
					Application.logger.info("Shutting down...");
					DatabaseModule.shutDown();
					System.exit(0);
				});
		
		ConnectionModule.turnOn();
		DatabaseModule.turnOn();
		
		MusicBandHandler.loadCollection(DatabaseModule.fetchMusicBands());
		User.users.addAll(DatabaseModule.fetchUsers());
		
		ConnectionModule.startJsonRefresh();
		
		ConnectionModule.conversation();
	}
}
