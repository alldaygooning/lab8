package module;

import java.text.*;
import java.util.*;

public class LocalizationModule {
	
	private static final String path = "config.resource_bundle";
	
	private Locale currentLocale;
	private ResourceBundle currentBundle;
	
	private DateFormat dateFormat;
	private NumberFormat numberFormat;
	
	public LocalizationModule() {
		changeLanguage("ru", "RU");
	}
	
	public String getStringByCode(int id) {
		return currentBundle.getString(String.valueOf(id));
	}
	
	public String getString(String string) {
		return currentBundle.getString(string);
	}
	
	public void changeLanguage(String language, String country) {
		currentLocale = new Locale(language, country);
		currentBundle = ResourceBundle.getBundle(path, currentLocale);
		dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
		numberFormat = NumberFormat.getNumberInstance(currentLocale);
	}
	
	public String getFormattedDate(Date date) {
		return dateFormat.format(date);
	}
	
	public String getFormattedNumber(Number number) {
		return numberFormat.format(number);
	}
	
	public String getFormattedLong(Long number) {
		String text;
		if(number > 1000000000) {
			text = String.format("%.3f" + this.getString("billion_short"), number/ 1000000000.0);
		}
		else if(number > 1000000) {
			text = String.format("%.3f" + this.getString("million_short"), number/ 1000000.0);
		}
		else {
			text = String.format("%.3f" + this.getString("thousand_short"), number/ 1000.0);
		}
		return text;
	}
}
