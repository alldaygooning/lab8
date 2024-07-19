package gui.miscellaneous.language;


import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import application.Application;
import module.LocalizationModule;

public class LanguageMenu extends JPopupMenu{
	private static final long serialVersionUID = 1L;
	
	LanguageMenuItem english;
	LanguageMenuItem russian;
	LanguageMenuItem estonian;
	LanguageMenuItem greek;
	LanguageMenuItem spanish;
	
	ButtonGroup buttonGroup = new ButtonGroup();
	
	LanguageMenu languageMenu;
	ButtonModel selectedItem;
	
	LanguageChangeListener languageChangeListener;
	
	private static final LocalizationModule localizationModule = Application.getLocalizationModule();
	
	public LanguageMenu() {
		this.languageMenu = this;
		
		this.createItems();
		
		this.addItemsToButtonGroup();
		
		this.addItemsToMenu();
		
		this.addActionListeners();
	}
	
	private void createItems() {
		english = new LanguageMenuItem("English");
		russian = new LanguageMenuItem("Русский");
		estonian = new LanguageMenuItem("Esonian");
		greek = new LanguageMenuItem("Ελληνικά");
		spanish = new LanguageMenuItem("Español");
	}
	
	private void addItemsToButtonGroup() {
		buttonGroup.add(english);
		buttonGroup.add(russian);
		buttonGroup.add(estonian);
		buttonGroup.add(greek);
		buttonGroup.add(spanish);
		
		buttonGroup.setSelected(russian.getModel(), true);
	}
	
	private void addItemsToMenu() {
		this.add(english);
		this.add(russian);
		this.add(estonian);
		this.add(greek);
		this.add(spanish);
	}
	
	private void addActionListeners() {
		english.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localizationModule.changeLanguage("en", "US");
				postLanguageChangeEvent();
			}
		});
		russian.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localizationModule.changeLanguage("ru", "RU");
				postLanguageChangeEvent();
			}
		});
		estonian.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localizationModule.changeLanguage("et", "EE");
				postLanguageChangeEvent();
			}
		});
		greek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localizationModule.changeLanguage("el", "GR");
				postLanguageChangeEvent();
			}
		});
		spanish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localizationModule.changeLanguage("es", "HN");
				postLanguageChangeEvent();
			}
		});
		
		languageMenu.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				selectedItem = buttonGroup.getSelection();
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				
			}
		});
	}
	
	private void postLanguageChangeEvent() {
		if(selectedItem != buttonGroup.getSelection()) {
			this.languageChangeListener.languageChanged();
		}
	}
	
	public void setLanguageChangeListner(LanguageChangeListener languageChangeListener) {
		this.languageChangeListener = languageChangeListener;
	}
}
