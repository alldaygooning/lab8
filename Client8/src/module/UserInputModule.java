package module;

import java.io.Console;
import java.math.BigInteger;
import java.security.*;
import java.util.*;

import attribute.*;

public class UserInputModule {
	private static final Scanner user = new Scanner(System.in);
	private static final Console console = System.console();
	//Чтение команд
	public static String readCommand() {
		String input;
		while(true) {
			System.out.print(TextColor.bold(">"));
			input = user.nextLine();
			if(input.isBlank()) {
				continue;
			}
			return(input);
		}
	}
	
	
	public static String encrypt(String password){
		try {
			MessageDigest digester = MessageDigest.getInstance("SHA-384");
			byte[] digested = digester.digest(password.getBytes());
			BigInteger signum = new BigInteger(1, digested);
			String hashed = signum.toString(16);
			while(hashed.length() < 32) {
				hashed = "0" + hashed;
			}
			return hashed;
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getLoginInput() {
		String input;
		while(true) {
			System.out.print("Введите имя профиля:");
			input = user.nextLine();
			if(input.isBlank()) {
				continue;
			}
			return input;
		}
	}
	
	public static String getPasswordInput() {
		String input;
		String password;
		while(true) {
			System.out.print("Введите пароль или нажмите enter, чтобы пропустить: ");
			input = user.nextLine();
			if(input.isBlank()) {
				return "";
			}
			password = input;
			System.out.print("Введите пароль ещё раз для подтверждения: ");
			input = user.nextLine();
			if (!password.equals(input)) {
				System.out.println("Пароли не совпадают! Попробуйте снова.");
				continue;
			}
			return password;
		}
	}
	
	public static String getPasswordAuthInput() {
		String input;
		if (console == null) {
			System.out.print("Введите пароль или нажмите enter, чтобы пропустить: ");
			input = user.nextLine();
			if(input.isBlank()) {
				return null;
			}
			return input;
		}
		System.out.print("Введите пароль или нажмите enter, чтобы пропустить: ");
		char[] password = console.readPassword();
		return(new String(password));
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getNameInput() {
		String input;
		while(true) {
			System.out.print("Введите название группы: ");
			input = user.nextLine();
			if(input.isBlank() || !validateName(input)) {
				continue;
			}
			return(input);
		}
	}

	public static String[] getCoordinatesInput() {
		String[] input;
		while(true) {
			System.out.print("Введите координаты x и y группы через пробел: ");
			input = user.nextLine().split(" {1,}");
			if (input.length < 2) {
				continue;
			}
			if(validateCoordinates(input[0], input[1])) {
				return input;
			}	
		}
	}

	public static String getNumberOfParticipantsInput() {
		String input;
		while(true) {
			System.out.print("Введите количество участников группы: ");
			input = user.nextLine();
			if (input.isBlank()) {
				continue;
			}
			if(validateNumberOfParticipants(input)) {
				return input;
			}
		}
	}
	
	public static String getMusicGenreInput() {
		String input;
		while(true) {
			System.out.print("Введите жанр группы (" + MusicGenre.getValues() + "/нажмите enter для установки null)): ");
			input = user.nextLine();
			if(input.isEmpty()) {
				return null;
			}
			if(validateMusicGenre(input)) {
				return input;
			}
		}
	}

	public static String[] getFrontManInput() {
		String[] input = new String[] {getFrontManName(), getFrontManHeight(), getFrontManWeight(), getFrontManHairColor(), getFrontManCountry()};
		return input;
	}
	
	private static String getFrontManName() {
		String input;
		while(true) {
			System.out.print("Введите имя фронтмена группы: ");
			input = user.nextLine();
			if(input.isBlank() || !validateName(input)) {
				continue;
			}
			return(input);
		}
	}
	
	private static String getFrontManHeight() {
		String input;
		while(true) {
			System.out.print("Введите высоту фронтмена группы или нажмите enter для установки null: ");
			input = user.nextLine();
			if(input.isBlank()) {
				return null;
			}
			if(validateFrontManHeight(input)) {
				return input;
			}
		}
	}

	private static String getFrontManWeight() {
		String input;
		while(true) {
			System.out.print("Введите вес фронтмена группы: ");
			input = user.nextLine();
			if (input.isBlank()) {
				continue;
			}
			if(validateFrontManWeight(input)) {
				return input;
			}
		}
	}

	private static String getFrontManHairColor() {
		String input;
		while(true) {
			System.out.print("Введите цвет волос фронтмена группы (" + Color.getValues() + "): ");
			input = user.nextLine();
			if (input.isBlank()) {
				continue;
			}
			if(validateFrontManHairColor(input)) {
				return input;
			}
		}
	}
	
	private static String getFrontManCountry() {
		String input;
		while(true) {
			System.out.print("Введите страну происхождения фронтмена группы (" + Country.getValues() + "/нажмите enter для установки null): ");
			input = user.nextLine();
			if(input.isBlank()) {
				return null;
			}
			if(validateFrontManCountry(input)) {
				return input;
			}
		}
	}

	
	
	
	
	
	
	
	
	
	public static boolean validateBand(List<String> args) {
		if(!validateName(args.get(0))){
			return false;
		}
		if(!validateCoordinates(args.get(1), args.get(2))){
			return false;
		}
		if(!validateNumberOfParticipants(args.get(3))) {
			return false;
		}
		if(!validateMusicGenre(args.get(4))){
			return false;
		}
		if(!validateFrontMan(args.get(5), args.get(6), args.get(7), args.get(8), args.get(9))) {
			return false;
		}
		return true;
	}
	
	
	
	public static boolean validateId(String input) {
		try {
			if(Integer.valueOf(input) < 0) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException e) {
			System.out.println("Ошибка ввода. Ожидалось: целое число от 1 до " + Integer.MAX_VALUE + ". Получено: " + input  + ".");
			return false;
		}
		return true;
	}
	
	private static boolean validateName(String input) {
		if (input.isBlank() || input.length() > 32) {
			System.out.println("Ошибка ввода. Ожидалось: непустая строка длиной не более 32 символов. Получено: " + input);
			return false;
		}
		return true;
	}

	private static boolean validateCoordinates(String inputX, String inputY) {
		if(validateX(inputX) && validateY(inputY)) {
			return true;
		}
		System.out.println("Ошибка ввода. Ожидалось: два числа с плавающей запятой. Второе число не должно превышать 587. Получено: (" + inputX + "; " + inputY + ")");
		return false;
	}
	
	public static boolean validateNumberOfParticipants(String input) {
		try {
			if(Long.valueOf(input)<=0) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException e) {
			System.out.println("Ошибка ввода. Ожидалось: целое число от 1 до " + Long.MAX_VALUE + ". Получено: " + input  + ".");
			return false;
		}
		return true;
	}
	
	private static boolean validateMusicGenre(String input) {
		try {
			MusicGenre.valueOf(input);
		}catch(IllegalArgumentException e) {
			System.out.println("Ошибка ввода. Ожидалось: " + MusicGenre.getValues() + ". Получено: " + input + ".");
			return false;
		}
		return true;
	}
	
	private static boolean validateFrontMan(String inputName, String inputHeight, String inputWeight, String inputHairColor, String inputCountry) {
		return validateFrontManName(inputName) && validateFrontManHeight(inputHeight) && validateFrontManWeight(inputWeight) 
				&& validateFrontManHairColor(inputHairColor) && validateFrontManCountry(inputCountry);
	}
	
	
	private static boolean validateX(String input) {
		try {
			Double.valueOf(input);
		}catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static boolean validateY(String input) {
		try {
			if (Double.valueOf(input) > 587) {
				return false;
			}
		}catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static boolean validateFrontManName(String input) {
		if (input.isBlank()) {
			return false;
		}
		return true;
	}
	
	private static boolean validateFrontManHeight(String input) {
		try {
			if(Long.valueOf(input) <= 0) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException e) {
			System.out.println("Ошибка ввода. Ожидалось: целое число от 1 до " + Long.MAX_VALUE + ". Получено: " + input + ".");
			return false;
		}
		return true;
	}
	
	private static boolean validateFrontManWeight(String input) {
		try {
			if(Integer.parseInt(input) <= 0) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException e) {
			System.out.println("Ошибка ввода. Ожидалось: целое число от 1 до " + Integer.MAX_VALUE + ". Получено: " + input + ".");
			return false;
		}
		return true;
	}

	private static boolean validateFrontManHairColor(String input) {
		try {
			Color.valueOf(input);
		}catch(IllegalArgumentException e) {
			System.out.println("Ошибка ввода. Ожидалось: " + Color.getValues() + ". Получено: " + input + ".");
			return false;
		}
		return true;
	}
	
	private static boolean validateFrontManCountry(String input) {
		try {
			Country.valueOf(input);
		}catch(IllegalArgumentException e) {
			System.out.println("Ошибка ввода. Ожидалось: " + Country.getValues() + ". Получено: " + input + ".");
			return false;
		}
		return true;
	}
}
