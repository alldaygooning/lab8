package gui.main.tab.tool.help;

import java.awt.*;

import gui.*;

public class HelpPanel extends CustomPanel implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	CustomLabel deleteKeyNameLabel1;
	CustomLabel deleteKeyNameLabel2;
	
	CustomLabel deleteSelectedLabel;
	CustomLabel deleteAllLabel;
	CustomLabel holdLabel;
	
	HoldProgressBar holdProgressBar;
	HoldProgressBarController barController;
	
	public HelpPanel() {
		super();
		
		this.setLayout(new GridBagLayout());
		
		this.setLabels();
		this.setProgressBar();
	}
	
	
	private void setLabels() {
		deleteKeyNameLabel1 = new CustomLabel("delete_key", new Font(Font.DIALOG, Font.BOLD, 10));
		deleteKeyNameLabel2 = new CustomLabel("delete_key", new Font(Font.DIALOG, Font.BOLD, 10));
		
		deleteSelectedLabel = new CustomLabel("delete_selected", new Font(Font.DIALOG, Font.PLAIN, 10));
		deleteAllLabel = new CustomLabel("delete_all", new Font(Font.DIALOG, Font.PLAIN, 10));
		holdLabel = new CustomLabel("hold", new Font(Font.DIALOG, Font.PLAIN, 10));
		
		GraphicUtilities.addToGBL(this, deleteKeyNameLabel1, 1, 0, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 1, 1, new Insets(0, 0, 0, 0));
		GraphicUtilities.addToGBL(this, deleteSelectedLabel, 2, 0, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 1, 1, new Insets(0, 0, 0, 0));
		
		GraphicUtilities.addToGBL(this, holdLabel, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_END, 1, 1, new Insets(1, 1, 1, 1));
		GraphicUtilities.addToGBL(this, deleteKeyNameLabel2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 1, 1, new Insets(1, 1, 1, 1));
		GraphicUtilities.addToGBL(this, deleteAllLabel, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, 1, 1, new Insets(1, 1, 1, 1));
	}
	
	private void setProgressBar() {
		holdProgressBar = new HoldProgressBar();
		
		GraphicUtilities.addToGBL(this, holdProgressBar, 0, 2, 3, GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1);
	}
	
	public void translate() {
		TranslatedContainer.super.translate();
	}
	
	public void setBarController(HoldProgressBarController barController) {
		this.barController = barController;
		this.barController.setHoldProgressBar(holdProgressBar);
	}
}
