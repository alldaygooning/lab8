package gui.miscellaneous;

import java.awt.*;

import gui.CustomPanel;
import gui.TranslatedContainer;
import gui.miscellaneous.language.LanguageButton;

public class MiscellaneousPanel extends CustomPanel implements TranslatedContainer {
	private static final long serialVersionUID = 1L;

	AboutButton aboutButton;
	ReconnectButton reconnectButton;
	LanguageButton languageButton;
	
	public MiscellaneousPanel() {
		super();
		
		this.setLayout(new FlowLayout());
		
		aboutButton = new AboutButton();
		reconnectButton = new ReconnectButton();
		languageButton = new LanguageButton();
		
		this.add(languageButton);
		this.add(reconnectButton);
		this.add(aboutButton);
	}
	
	@Override
	public void translate() {
		TranslatedContainer.super.translate();
		super.translate();
	}
}
