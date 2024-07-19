package gui.main;

import java.awt.*;
import java.io.File;

import gui.*;
import gui.main.tab.MainTabbedPane;
import gui.miscellaneous.MiscellaneousPanel;
import gui.miscellaneous.profile.ProfileButton;

public class MainFrame extends CustomFrame implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String title = "Best Application Ever";
	private static final String pathToIcon = "content" + File.separator + "logo.png";
	private static final Dimension launchSize = new Dimension(1190, 720);
	
	MainTabbedPane tabbedPane;
	MiscellaneousPanel miscellaneousPanel;
	ProfileButton profileButton;

	public MainFrame() {
		super(title, pathToIcon, launchSize);
		
		this.center();
		
		this.setLayout(new GridBagLayout());
		
		this.setTabbedPane();
		this.setMiscellaneous();
		
		this.setVisible(true);
		this.setDefaultFocus();
	}
	
	private void setTabbedPane() {
		tabbedPane = new MainTabbedPane();
		
		GraphicUtilities.addToGBL(this, tabbedPane, 0, 3, 3, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 1, 40);
	}
	
	private void setMiscellaneous() {
		miscellaneousPanel = new MiscellaneousPanel();
		profileButton = new ProfileButton();
		
		GraphicUtilities.addToGBL(this, miscellaneousPanel, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, profileButton, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 1, 1);
	}
	
	private void setDefaultFocus() {
		tabbedPane.requestFocusInWindow();
	}
	
	@Override
	public void translate() {
		super.translate();
	}
}
