package application;

import java.net.*;
import java.util.*;

import javax.swing.*;


import attribute.TextColor;
import module.*;
import wrapper.*;
import command.*;
import exception.*;

public class Application {
	
	private static LocalizationModule localizationModule;
	private static MusicBandHandler musicBandHandler;
	
	public static void run(String serverIP, String mode) throws SocketException {
		
		ConnectionModule.turnOn(serverIP);

		musicBandHandler = new MusicBandHandler();
		
		ConnectionModule.startJsonRefresh(musicBandHandler);
		Invoker.introduce(new ReconnectCommand(), new HelpCommand(), new LogInCommand(), new InfoCommand(),
				new AddCommand(musicBandHandler), new UpdateCommand(musicBandHandler), new LogOutCommand(), new ShowCommand(musicBandHandler),
				new PopulateCommand(musicBandHandler), new SignUpCommand(), new ChangePasswordCommand(), new RemoveByIdCommand(musicBandHandler),
				new ClearCommand(musicBandHandler));
		
		localizationModule = new LocalizationModule();
		
		switch (mode) {
		case "gui": runGUI();
			break;
		case "terminal": runInTerminal();
			break;
		case "dual": runGUI();
			runInTerminal();
			break;
		default: System.out.println("Illegal mode selected");
			break;
		}
		
	}
	private static void runInTerminal() {
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n"
				+ "▒                                               ▒\n"
				+ "▒               Добро пожаловать                ▒\n"
				+ "▒          в программу Никиты Копытова!         ▒\n"
				+ "▒                                               ▒\n"
				+ "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n"
				+ "▒                                               ▒\n"
				+ "▒      Здесь Вы можете управлять коллекцией     ▒\n"
				+ "▒              музыкальных групп.               ▒\n"
				+ "▒       Чтобы начать работу введите команду     ▒\n"
				+ "▒                     "+TextColor.bold("help")+"                      ▒\n"
				+ "▒                                               ▒\n"
				+ "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		
		if (!ConnectionModule.isConnected()) {
			System.out.println(TextColor.red("Ошибка! Не удалось подключиться к серверу. Некоторые функции будут недостпуны."));
		}
		else {
			System.out.println("Соединение установлено!");
		}
		
		String userInput;
		
		while(true){
			 try{
				userInput = UserInputModule.readCommand();
				
				if(!ConnectionModule.isConnected()) {
					ConnectionModule.reconnectOnLastKnownPort();
				}
				
				Invoker.execute(new ArrayList<String>(Arrays.asList(userInput.split("\\s+"))));
				
				
			}catch(ConnectionException e) {
				if(ConnectionModule.isConnected()) {
					System.out.println(TextColor.red("Соединение с сервером потеряно. Некоторые фукнции будут недоступны."));
					ConnectionModule.disconnect();
				}
				System.out.println(TextColor.red("Отсутствует подключение. Используйте " 
						+ TextColor.BOLD + "reconnect") + TextColor.red(" для повтороного подключения."));
			}catch(UnknownCommandException e) {
				System.out.println(TextColor.red("Неизвестная команда \"" + e.getMessage() + "\". Используйте "+TextColor.BOLD +"help") + TextColor.red(" для просмотра справки по командам."));
			}catch(ExecutionCancelled e) {
				if (e.reason_id == 0) { 
					//Ноль согнализирует о том, что сообщение об ошибке выводить не надо.
					//Вероятно об этом позаботилась другая служба (например как это делает UserInputModule при валидации полей).
					continue;
				}
				System.out.println(TextColor.red(localizationModule.getStringByCode(e.reason_id)) + "\nИсполнение команы отменено.");
			}catch(IllegalAccessError e) {
				System.out.println(TextColor.red("Для использование этой команды необходимо авторизоваться!"));
			}catch(IncorrectAmountOfArguments e) {
				System.out.println(TextColor.red("Неверное количество аргументов! Введите " + TextColor.bold("help " + e.getMessage())) 
						+ TextColor.red(" для получения справки по командам."));
			}
		}
	}
		
	public static void runGUI() {
		SwingUtilities.invokeLater(() -> {
			GraphicModule.setMusicBandHandler(musicBandHandler);
			GraphicModule.turnOn();
		});
	}
	
	synchronized public static void send(Request request) {
		User.appendCredentials(request);
		SendModule.sendRequest(request);
	}
	
	synchronized public static Response receive() {
		try {
			Response response = ReceiveModule.receiveResponse();
			if(!response.successful) {
				throw new ExecutionCancelled(response.reason_id);
			}
			return response;
		}catch(SocketTimeoutException e) {
			throw new ExecutionCancelled(2);
		}
	}
	
	public static LocalizationModule getLocalizationModule() {
		return localizationModule;
	}
}
// Если в гуи случается какой-то exception, то пускай его ловит не сам элемент, а выше!





	

