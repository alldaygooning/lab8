package attribute;

import application.Application;

public enum Color {
    RED("enum_red"),
    BLACK("enum_black"),
    ORANGE("enum_orange"),
    BROWN("enum_brown");
	String stringKey;
	
	public static String getValues() {
		String list = "";
		for (Color color : values()) {
			list = list + color.name() + "/";
		}
		return list.substring(0, list.length()-1);
	}
	
	private Color(String stringKey) {
		this.stringKey = stringKey;
	}
	
	@Override
	public String toString() {
		return (Application.getLocalizationModule().getString(stringKey));
	}
	
	public String toName() {
		switch(this){
		case RED: return "RED";
		case BLACK: return "BLACK";
		case ORANGE: return "ORANGE";
		case BROWN: return "BROWN";
		default: return null;
		}
	}
}