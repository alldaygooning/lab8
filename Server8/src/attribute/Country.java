package attribute;

public enum Country {
    RUSSIA,
    FRANCE,
    JAPAN;
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
			return null;
		}
	}
}