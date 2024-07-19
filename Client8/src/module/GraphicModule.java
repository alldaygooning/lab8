package module;

import java.awt.Color;
import java.util.*;

import javax.swing.*;

import com.formdev.flatlaf.*;

import application.*;
import gui.GraphicUtilities;
import gui.login.*;
import gui.main.DataUpdateListener;
import gui.main.MainFrame;

public class GraphicModule {
	
	static MainFrame mainFrame;
	static MusicBandHandler musicBandHandler;
	
	static DataUpdateListener dataUpdateListener;
	
	public static void turnOn() {
		Map<String, String> globalSettings = new HashMap<String, String>();
		globalSettings.put("@accentColor", "#543310");
		FlatLaf.setGlobalExtraDefaults(globalSettings);
		FlatLightLaf.setup();
		
		UIManager.put("Component.borderColor", Color.LIGHT_GRAY);
		UIManager.put("TextComponent.arc", 5);
		UIManager.put("Component.error.borderColor", GraphicUtilities.errorColor);
		UIManager.put("PasswordField.showCapsLock", false);
		UIManager.put("TabbedPane.background", GraphicUtilities.transparent);
		UIManager.put("TabbedPane.focusColor", GraphicUtilities.transparent);
		UIManager.put("TabbedPane.contentSeparatorHeight", 0);
		UIManager.put("Table.selectionInactiveBackground", GraphicUtilities.secondaryColor);
		UIManager.put("Table.selectionInactiveForeground", java.awt.Color.WHITE);
		new LoginFrame();
	}
	
	public static void setMusicBandHandler(MusicBandHandler musicBandHandler) {
		GraphicModule.musicBandHandler = musicBandHandler;
	}
	
	public static void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		GraphicModule.dataUpdateListener = dataUpdateListener;
		GraphicModule.musicBandHandler.addDataUpdateListener(dataUpdateListener);
	}
}