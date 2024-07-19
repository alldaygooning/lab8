package gui.miscellaneous;

import application.Application;
import gui.*;
import module.LocalizationModule;

public class AboutTextArea extends CustomTextArea implements TranslatedContainer{
	private static final long serialVersionUID = 1L;

	String stringKey;
	static LocalizationModule lm = Application.getLocalizationModule();
	
	public AboutTextArea(String stringKey) {
		super();
		this.stringKey = stringKey;
		
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setEditable(false);
		
		
		setText();
	}

	@Override
	public void translate() {
		setText();
	}
	
	private void setText() {
		this.setText(lm.getString(stringKey));
	}
}
