package module;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import attribute.*;

public class FactoryModule {
	private final static Random random = new Random();
	public static List<String> createValidBand(){
		List<String> args = new ArrayList<>();
		args.add(UserInputModule.getNameInput());
		Stream.of(UserInputModule.getCoordinatesInput()).forEach(args::add);
		args.add(UserInputModule.getNumberOfParticipantsInput());
		args.add(UserInputModule.getMusicGenreInput());
		Stream.of(UserInputModule.getFrontManInput()).forEach(args::add);
		return(args);
	}
	
	public static List<String> generateRandomBand(){
		List<String> args = new ArrayList<>();
		
		long[] randomLongs = random.longs(2, 0, Long.MAX_VALUE).toArray();
		int randomInt = random.ints(1, 0, 400000).findFirst().getAsInt();
		String[] names = getRandomWord(2);
		
		args.add(names[0]);
		args.add(String.valueOf(random.doubles(1).findFirst().getAsDouble()*1000));
		args.add(String.valueOf(random.doubles(1, Double.MIN_VALUE, 587).findFirst().getAsDouble()));
		args.add(String.valueOf(randomLongs[0]));
		args.add(randomEnum(MusicGenre.class).toName());
		args.add(names[1]);
		args.add(String.valueOf(randomLongs[1]));
		args.add(String.valueOf(randomInt));
		args.add(randomEnum(Color.class).toName());
		args.add(randomEnum(Country.class).toName());
		return(args);
	}
	
	private static <T extends Enum<?>> T randomEnum(Class<T> givenClass) {
		T[] enums = givenClass.getEnumConstants();
		return(enums[random.nextInt(0, enums.length-1)]);
	}
	
	private static String[] getRandomWord(int amount) {
		String[] words = new String[amount];
		try (RandomAccessFile raf = new RandomAccessFile("words.txt", "r");){
			
			long[] randomPositions = random.longs(amount, 0, raf.length()-100).toArray();
			int counter = 0;
			for(long i:randomPositions) {
				raf.seek(i);
				raf.readLine();
				words[counter++] = raf.readLine();
			}
			return words;
		}catch(IOException e) {
			return null;
		}
		
		
		
	}
}
