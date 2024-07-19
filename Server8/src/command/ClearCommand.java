package command;

import java.io.Serializable;
import java.util.List;

import application.Application;
import wrapper.Response;

public class ClearCommand extends Command implements Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String user, List<String> args) {
		return new Response(true, String.valueOf(Application.MusicBandHandler.clear(user, args)));
	}
}
