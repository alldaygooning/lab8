package attribute;

import application.Application;

public enum Country {
	NONE("country_none"),
    RUSSIA("country_russia"),
    FRANCE("country_france"),
    JAPAN("country_japan");
	String stringKey;
	
	public static String getValues() {
		String list = "";
		for (Country country : values()) {
			list = list + country.name() + "/";
		}
		return list.substring(0, list.length()-1);
	}
	public static Country valueOfNullable(String value) {
		Country country;
		try {
			country = Country.valueOf(value);
			return country;
		}catch(NullPointerException e) {
			return NONE;
		}
	}
	
	private Country(String stringKey) {
		this.stringKey = stringKey;
	}
	
	@Override
	
	public String toString() {
		return (Application.getLocalizationModule().getString(stringKey));
	}
	
	public String toName() {
		switch(this){
		case RUSSIA: return "RUSSIA";
		case FRANCE: return "FRANCE";
		case JAPAN: return "JAPAN";
		case NONE: return null;
		default: return null;
		}
	}
}