package command;
import java.io.Serializable;
import java.util.*;

import application.Application;
import wrapper.Response;



public class InfoCommand extends Command implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String user, List<String> input) {
		String info = Application.MusicBandHandler.info();
		return new Response(true, info);
	}
}
