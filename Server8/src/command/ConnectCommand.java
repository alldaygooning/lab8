package command;

import java.io.Serializable;
import java.util.List;

import wrapper.Response;



public class ConnectCommand extends Command implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	
	
	@Override
	public Response execute(String user, List<String> args) {
		return new Response(true);
	}
	
	@Override
	public boolean requiresAuthorization() {
		return false;
	}
}
