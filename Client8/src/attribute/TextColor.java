package attribute;

public enum TextColor {
	RESET("\u001B[0m"),
	BLACK_BOLD("\u001B[30;1m"),
	RED_BOLD("\u001B[31;1m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m"),
	BOLD("\u001B[1m");
	
	public final String code;
	private TextColor(String colorCode) {
		this.code = colorCode;
	}
	public static String red(String input) {
		return("\u001B[31m" + input + "\u001B[0m");
	}
	public static String bold(String input) {
		return("\u001B[1m" + input + "\u001B[0m");
	}
	public static String faint(String input) {
		return("\u001B[90m" + input + "\u001B[0m");
	}
	
	
	@Override
	public String toString() {
		return(this.code);
	}
}
