package command;

import java.util.List;

import application.Application;
import application.MusicBandHandler;
import module.FactoryModule;
import wrapper.Request;
import wrapper.Response;



public class PopulateCommand extends Command{
	
	MusicBandHandler musicBandHandler;
	
	public PopulateCommand(MusicBandHandler musicBandHandler) {
		this.musicBandHandler = musicBandHandler;
		this.name = "populate";
		this.displayName = "populate [число]";
		this.description = "Создать случайную группу [число] раз.";
	}

	public void execute(List<String> args) {
		int times = 1;
		List<String> sendArgs;
		if(args.size() != 0) {
			try {
				times = Integer.parseInt(args.get(0));
			}catch(NumberFormatException e) {}
		}
		for (int i = 0; i < times; i++) {
			sendArgs = FactoryModule.generateRandomBand();
			Response response = send(sendArgs);
			add(getId(response), sendArgs);
			System.out.println(String.format("Группа %s успешно создана!", sendArgs.get(0)));
		}
	}
	
	private Response send(List<String> args) {
		Application.send(new Request(new AddCommand(), args));
		Response response = Application.receive();
		return response;
	}
	
	private void add(int id, List<String> args) {
		musicBandHandler.add(id, args);
	}
	
	private int getId(Response response) {
		return (Integer.parseInt(response.text));
	}
}
