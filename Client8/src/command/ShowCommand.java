package command;

import java.util.List;
import java.util.Optional;

import application.MusicBandHandler;
import exception.ConnectionException;

public class ShowCommand extends Command{

	MusicBandHandler musicBandHandler;
	
	public ShowCommand(MusicBandHandler musicBandHandler) {
		this.name = "show";
		this.displayName = "show [номер страницы]";
		this.description = "Вывести в стандартный поток вывода все элементы сортированной коллекции в строковом представлении.";
		this.musicBandHandler = musicBandHandler;
	}
	
	@Override
	public void execute(List<String> args) throws ConnectionException {
		Integer page = null;
		if (args.size() != 0) {
			try {
				page = Integer.valueOf(args.get(0));
			}catch(NumberFormatException e) {
			}
		}
		System.out.println(musicBandHandler.show(Optional.ofNullable(page)));
	}
}
