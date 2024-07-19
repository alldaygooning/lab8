package attribute;

public enum Color {
    RED,
    BLACK,
    ORANGE,
    BROWN;
	public static String getValues() {
		String list = "";
		for (Color color : values()) {
			list = list + color.name() + "/";
		}
		return list.substring(0, list.length()-1);
	}
}