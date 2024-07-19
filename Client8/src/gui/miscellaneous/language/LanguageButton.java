package gui.miscellaneous.language;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import gui.*;
import gui.event.LanguageChangeEvent;

public class LanguageButton extends CustomButton implements TranslatedContainer{ 

	private static final long serialVersionUID = 1L;
	
	private static final String pathToIcon = "content" + File.separator + "language.png";
	private static final Dimension preferredSize = new Dimension(40, 40);
	private static final String toolTipKey = "language_button_tooltip";

	private LanguageButton languageButton;
	private static LanguageMenu languageMenu = new LanguageMenu();
	private LanguageChangeListener languageChangeListener;
	
	public LanguageButton() {
		super();
		this.languageButton = this;
		
		this.setScaledIcon(pathToIcon, 35, 35, Image.SCALE_SMOOTH);
		this.setPreferredSize(preferredSize);
		
		this.setToolTipKey(toolTipKey);
		
		this.setListeners();
	}
	
	private void setListeners() {
		languageChangeListener = new LanguageChangeListener() {
			@Override
			public void languageChanged() {
				languageButton.dispatchEvent(new LanguageChangeEvent(languageButton));
			}
		};
		languageMenu.setLanguageChangeListner(languageChangeListener);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				int x = event.getX();
				int y = event.getY();
				
				languageMenu.show(languageButton, x, y);
			}
		});
	}
	
	@Override
	protected void processEvent(AWTEvent e) {
		if(e instanceof LanguageChangeEvent) {
			System.gc();
			for(Window window:Window.getWindows()) {
				if(window instanceof TranslatedContainer) {
					TranslatedContainer translatedContainer = (TranslatedContainer) window;
					translatedContainer.translate();
				}
			}
		}
		super.processEvent(e);
	}

	@Override
	public void translate() {
		super.translate();
	}
}
