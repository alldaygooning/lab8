package command;

import java.io.Serializable;
import java.util.List;

import module.ConnectionModule;
import wrapper.Response;

public class LastUpdateCommand extends Command implements Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String user, List<String> args) {
		long lastUpdate = ConnectionModule.getLastJsonUpdate();
		Response response = new Response(true, String.valueOf(lastUpdate));
		return response;
	}
}
