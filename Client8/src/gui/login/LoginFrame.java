package gui.login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import gui.*;
import gui.event.ErrorEvent;
import gui.miscellaneous.*;
import gui.signup.*;

public class LoginFrame extends CustomFrame implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String title = "Best Login Ever";
	private static final String pathToIcon = "content" + File.separator + "loginLogo.png";
	private static final Dimension launchSize = new Dimension(576, 288);
	
	LoginFrame loginFrame;
	
	CustomLabel enterLoginLabel;
	CustomLabel enterPasswordLabel;
	
	CustomLabel missingLoginLabel;
	CustomLabel incorrectLoginLabel;
	CustomLabel incorrectPasswordLabel;
	
	LoginField loginField;
	PasswordField passwordField;
	
	LoginButton loginButton;
	SignupButton signupButton;
	
	MiscellaneousPanel miscellaneousPanel;
	
	public LoginFrame() {
		super(title, pathToIcon, launchSize);
		this.loginFrame = this;
		
		this.center();
		
		this.setLayout(new GridBagLayout());
		
		this.setLabelsAndFields();
		this.setMainButtons();
		this.setErrorLabels();
		this.setMiscellaneous();
		
		this.setVisible(true);
		this.setDefaultFocus();
	}
	
	private void setDefaultFocus() {
		loginField.requestFocusInWindow();
	}
	
	private void setMainButtons() {
		loginButton = new LoginButton();
		loginButton.addActionListener(new LoginButtonActionListner(this));
		signupButton = new SignupButton();
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginFrame.dispose();
				new SignupFrame();
			}
		});
		
		GraphicUtilities.addToGBL(this, loginButton, 3, 8, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1);
		GraphicUtilities.addToGBL(this, signupButton, 4, 8, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1);
		
		CustomLabel dummy = new CustomLabel();
		
		GraphicUtilities.addToGBL(this, dummy, 2, 8, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1);
	}
	
	private void setLabelsAndFields() {
		enterLoginLabel = new CustomLabel("enter_login");
		loginField = new LoginField();
		
		enterPasswordLabel = new CustomLabel("enter_password");
		passwordField = new PasswordField();
		
		
		GraphicUtilities.addToGBL(this, enterLoginLabel, 0, 3, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, loginField, 2, 3, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		GraphicUtilities.addToGBL(this, enterPasswordLabel, 0, 5, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, passwordField, 2, 5, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		CustomLabel dummy = new CustomLabel();
		
		GraphicUtilities.addToGBL(this, dummy, 5, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 1);
	}
	
	private void setErrorLabels() {
		missingLoginLabel = new CustomLabel("missing_login");
		incorrectLoginLabel = new CustomLabel("incorrect_login");
		incorrectPasswordLabel = new CustomLabel("incorrect_password");
		clearErrorMessages();
		
		GraphicUtilities.addToGBL(this, missingLoginLabel, 2, 4, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 1);
		GraphicUtilities.addToGBL(this, incorrectPasswordLabel, 2, 7, 3, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 1, 1);
		GraphicUtilities.addToGBL(this, incorrectLoginLabel, 2, 4, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 1);
	}
	
	private void setMiscellaneous() {
		miscellaneousPanel = new MiscellaneousPanel();
		
		GraphicUtilities.addToGBL(this, miscellaneousPanel, 3, 1, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 2);
	}
	
	public void clearErrorMessages() {
		missingLoginLabel.transparent();
		incorrectLoginLabel.transparent();
		incorrectPasswordLabel.transparent();
		
		loginField.clearError();
		passwordField.clearError();
	}
	
	private void displayMissingErrorMessage() {
		missingLoginLabel.error();
		loginField.error();
	}
	
	private void displayIncorrectLoginMessage() {
		incorrectLoginLabel.error();
		loginField.error();
	}
	
	private void displayIncorrectPasswordMessage() {
		incorrectPasswordLabel.error();
		passwordField.error();
	}
	
	public LoginField getLoginField() {
		return this.loginField;
	}
	
	public PasswordField getPasswordField() {
		return this.passwordField;
	}

	@Override
	protected void processEvent(AWTEvent e) {
		if(e instanceof ErrorEvent) {
			switch (((ErrorEvent)e).getError()) {
			case 1: displayMissingErrorMessage();
				return;
			case 55: displayIncorrectLoginMessage();
				return;
			case 56: displayIncorrectPasswordMessage();
				return;
			default:
				return;
			}
		}
		super.processEvent(e);
	}

	@Override
	public void translate() {
		super.translate();
	}
}