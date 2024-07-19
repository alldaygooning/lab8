package command;

import java.io.Serializable;
import java.util.*;

import application.Application;
import application.MusicBandHandler;
import attribute.*;
import exception.*;
import module.*;
import wrapper.*;


public class UpdateCommand extends Command implements Serializable, SilentExecute{
	private static final long serialVersionUID = 1L;
	private MusicBandHandler musicBandHandler;

	public UpdateCommand() {}
	
	public UpdateCommand(MusicBandHandler musicBandHandler) {
		this.musicBandHandler = musicBandHandler;
		this.name ="update";
		this.displayName = "update [id] [параметры объекта]";
		this.description = "Обновить значение элемента коллекции, id которого равен заданному.";
		this.fullDescription = TextColor.bold("Обновить значение элемента коллекции, id которого равен заданному.")+"\nupdate [id] - для построчного "
				+ "ввода каждого поля нового объекта.\nupdate [id] [параметры"
				+ " объекта] - для ввода всех значений полей нового объекта в одну строку."
				+ "\nПри вводе в строку необходимо вводить данные "
				+TextColor.bold("через пробел")+" в следующем формате:\n"
				+ "update [id] [имя группы] Double[координаты x и y (y <= 587)] Long[количество участников группы] [жанр]("+MusicGenre.getValues()+") ["
						+ "имя фронтмена] Long[высота фронтмена] int[вес фронтмена] [цвет волос фронтмена]("+Color.getValues()+ " )[национальность фронтмена]("+ Country.getValues()+").";;
	}

	public void execute(List<String> args) {
		List<String> sendArgs = new ArrayList<String>();
		if (args.size() == 1) {
			if(!UserInputModule.validateId(args.get(0))) {
				throw new ExecutionCancelled(0);
			}
			sendArgs.add(args.get(0));
			sendArgs.addAll(FactoryModule.createValidBand());
		}
		else if(args.size() == 11) {
			if(!UserInputModule.validateBand(args.subList(1, 11)) || !UserInputModule.validateId(args.get(0))) {
				throw new ExecutionCancelled(0);
			}
			sendArgs = args;
		}
		else {
			throw new IncorrectAmountOfArguments(name);
		}
		silentExecute(sendArgs);
		System.out.println(String.format("Группа под номером %s успешно обновлена!", args.get(0)));
	}

	@Override
	public void silentExecute(List<String> args) throws ConnectionException {
		send(args);
		update(args);
	}
	
	private Response send(List<String> args) {
		Application.send(new Request(new UpdateCommand(), args));
		Response response = Application.receive();
		return response;
	}
	
	private void update(List<String> args) {
		musicBandHandler.update(args);
	}
}
