package command;

import java.io.Serializable;
import java.util.List;

import application.Application;
import application.MusicBandHandler;
import attribute.*;
import exception.*;
import module.*;
import wrapper.*;


public class AddCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;
	
	MusicBandHandler musicBandHandler;
	
	public AddCommand() {
	}
	
	public AddCommand(MusicBandHandler musicBandHandler) {
		this.musicBandHandler = musicBandHandler;
		this.name = "add";
		this.displayName = "add [параметры объекта]" ;
		this.description = "Добавить новый элемент в коллекцию.";
		this.fullDescription = TextColor.bold("Добавить новый элемент в коллекцию.")+"\nadd - для построчного ввода каждого поля.\nadd [параметры"
				+ " объекта] - для ввода всех значений полей объекта в одну строку.\nПри вводе в строку необходимо вводить данные "
				+TextColor.bold("через пробел")+" в следующем формате:\n"
				+ "add [имя группы] Double[координаты x и y (y <= 587)] Long[количество участников группы] [жанр]("+MusicGenre.getValues()+") ["
						+ "имя фронтмена] Long[высота фронтмена] int[вес фронтмена] [цвет волос фронтмена]("+Color.getValues()+ " )[национальность фронтмена]("+ Country.getValues()+").";
	}
	@Override
	public void execute(List<String> args) {
		List<String> sendArgs;
		if(args.size() == 0) {
			sendArgs = FactoryModule.createValidBand();
		}
		else if(args.size() == 10) {
			if(!UserInputModule.validateBand(args)) {
				throw new ExecutionCancelled(0);
			}
			sendArgs = args;
		}
		else {
			throw new IncorrectAmountOfArguments(name);
		}
		Response response = send(sendArgs);
		add(getId(response), sendArgs);
		System.out.println(String.format("Группа %s успешно создана!", sendArgs.get(0)));
	}
	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		Response response = send(args);
		add(getId(response), args);
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
