package attribute;

import application.Application;

public enum MusicGenre {
	NONE("music_genre_none"),
    PSYCHEDELIC_CLOUD_RAP("psychedelic_cloud_rap"),
    JAZZ("jazz"),
    POP("pop"),
    MATH_ROCK("math_rock");
	String stringKey;
	
	private MusicGenre(String stringKey) {
		this.stringKey = stringKey;
	}
	
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
			return NONE;
		}
	}
	
	@Override
	public String toString() {
		return (Application.getLocalizationModule().getString(stringKey));
	}
	public String toName() {
		switch(this){
		case PSYCHEDELIC_CLOUD_RAP: return "PSYCHEDELIC_CLOUD_RAP";
		case JAZZ: return "JAZZ";
		case POP: return "POP";
		case MATH_ROCK: return "MATH_ROCK";
		case NONE: return null;
		default: return null;
		}
	}
}