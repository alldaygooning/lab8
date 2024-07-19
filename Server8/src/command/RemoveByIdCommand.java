package command;

import java.io.Serializable;
import java.util.List;

import application.Application;
import exception.ExecutionCancelled;
import wrapper.Response;

public class RemoveByIdCommand extends Command implements Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String user, List<String> args) {
		try {
			Application.MusicBandHandler.remove(user, args);
			return new Response(true);
		}catch(ExecutionCancelled e) {
			return new Response(false, e.reason_id);
		}
	}
}
