package command;

import java.io.Serializable;
import java.util.List;

import application.Application;
import wrapper.Response;

public class AddCommand extends Command implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String user, List<String> args) {
		int id = Application.MusicBandHandler.add(user, args);
		return new Response(true, String.valueOf(id));
	}
}
