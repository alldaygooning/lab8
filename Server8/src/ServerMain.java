
import java.io.IOException;
import java.sql.SQLException;

import application.Application;
import sun.misc.Signal;

public class ServerMain {
	
	public static void main(String[] args) throws IOException, SQLException {
		Signal.handle(new Signal("INT"), 
				signal -> {
					System.exit(0);
				});
		Application.run();
	}
}
