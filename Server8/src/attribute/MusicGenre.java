package attribute;

public enum MusicGenre {
    PSYCHEDELIC_CLOUD_RAP,
    JAZZ,
    POP,
    MATH_ROCK;
	
	public static String getValues() {
		String list = "";
		for (MusicGenre musicGenre : values()) {
			list = list + musicGenre.name() + "/";
		}
		return list.substring(0, list.length()-1);
	}

	public static MusicGenre valueOfNullable(String string) {
		MusicGenre genre;
		try {
			genre = MusicGenre.valueOf(string);
			return genre;
		}catch(NullPointerException e) {
			return null;
		}
	}
}