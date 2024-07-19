package command;

import java.io.Serializable;
import java.util.List;

import application.*;
import exception.ConnectionException;
import wrapper.*;

public class ClearCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;

	MusicBandHandler musicBandHandler;
	
	public ClearCommand() {};
	
	public ClearCommand(MusicBandHandler musicBandHandler) {
		this.musicBandHandler = musicBandHandler;
		this.name = "clear";
		this.displayName = name;
		this.description = "Очистить коллекцию.";
	}
	
	public void execute(List<String> args) {
		Response response = send(args);
		clear();
		System.out.println("Удалено " + response.text + " элементов");
	}
	
	private Response send(List<String> args) {
		Application.send(new Request(new ClearCommand(), args));
		Response response = Application.receive();
		return response;
	}
	
	private void clear() {
		musicBandHandler.clear();
	}

	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		send(args);
		clear();
	}
}
