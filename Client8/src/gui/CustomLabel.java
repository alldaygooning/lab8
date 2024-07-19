package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import application.Application;
import module.LocalizationModule;

public class CustomLabel extends JLabel implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private String stringKey;
	private static final LocalizationModule localizationModule = Application.getLocalizationModule();
	
	public CustomLabel() {
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBackground(GraphicUtilities.transparent);
	}
	
	public CustomLabel(String stringKey) {
		this();
		this.stringKey = stringKey;
		this.setText(localizationModule.getString(stringKey));
		
		this.setForeground(GraphicUtilities.textColor);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
	}
	
	public CustomLabel(String text, Font font) {
		this(text);
		this.setFont(font);
	}
	
	public void error() {
		this.setForeground(GraphicUtilities.errorColor);
	}
	
	public void transparent() {
		this.setForeground(GraphicUtilities.transparent);
	}
	
	public void setTextColor(Color color) {
		this.setForeground(color);
	}

	@Override
	public void translate() {
		if (stringKey != null) {
			this.setText(localizationModule.getString(stringKey));
			this.revalidate();
		}
	}
	
	public static CustomLabel createWithoutKey(String text) {
		CustomLabel customLabel = new CustomLabel();
		customLabel.setText(text);
		return customLabel;
	}
}