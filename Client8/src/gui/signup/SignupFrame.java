package gui.signup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import gui.*;
import gui.event.ErrorEvent;
import gui.login.*;
import gui.miscellaneous.*;



public class SignupFrame extends CustomFrame implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String title = "Best Sign-up Ever";
	private static final String pathToIcon = "content" + File.separator + "signupLogo.jpg";
	private static final Dimension launchSize = new Dimension(720, 360);
	
	SignupFrame signupFrame;
	
	CustomLabel enterLoginLabel;
	CustomLabel enterPasswordLabel;
	CustomLabel confirmPasswordLabel;
	
	CustomLabel missingLoginLabel;
	CustomLabel existingProfileLabel;
	CustomLabel passwordMismatch;
	
	LoginField loginField;
	PasswordField passwordField;
	PasswordField confirmPasswordField;
	
	LoginButton loginButton;
	SignupButton signupButton;
	
	MiscellaneousPanel miscellaneousPanel;
	
	public SignupFrame() {
		super(title, pathToIcon, launchSize);
		this.signupFrame = this;
		
		this.center();
		
		this.setLayout(new GridBagLayout());
		
		this.setLabelsAndFields();
		this.setMainButtons();
		this.setErrorLabels();
		this.setMiscellaneous();
		
		this.setVisible(true);
		this.setDefaultFocus();
	}
	
	private void setLabelsAndFields() {
		enterLoginLabel = new CustomLabel("enter_login");
		loginField = new LoginField();
		
		enterPasswordLabel = new CustomLabel("enter_password");
		passwordField = new PasswordField();
		
		confirmPasswordLabel = new CustomLabel("confirm_password");
		confirmPasswordField = new PasswordField();
		
		GraphicUtilities.addToGBL(this, enterLoginLabel, 0, 3, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, loginField, 2, 3, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		GraphicUtilities.addToGBL(this, enterPasswordLabel, 0, 5, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, passwordField, 2, 5, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		GraphicUtilities.addToGBL(this, confirmPasswordLabel, 0, 7, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, confirmPasswordField, 2, 7, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		CustomLabel dummy = new CustomLabel();
		
		GraphicUtilities.addToGBL(this, dummy, 5, 3, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 1);
	}

	private void setMainButtons() {
		loginButton = new LoginButton();
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				signupFrame.dispose();
				new LoginFrame();
			}
		});
		signupButton = new SignupButton();
		signupButton.addActionListener(new SignupButtonActionListner(this));
		
		GraphicUtilities.addToGBL(this, loginButton, 3, 9, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1);
		GraphicUtilities.addToGBL(this, signupButton, 4, 9, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1);
		
		CustomLabel dummy = new CustomLabel();
		
		GraphicUtilities.addToGBL(this, dummy, 2, 9, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1);
	}
	
	private void setErrorLabels() {
		missingLoginLabel = new CustomLabel("missing_login");
		existingProfileLabel = new CustomLabel("existing_profile");
		passwordMismatch = new CustomLabel("mismatch_passwords");
		clearErrorMessages();
		
		GraphicUtilities.addToGBL(this, missingLoginLabel, 2, 4, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0.5);
		GraphicUtilities.addToGBL(this, existingProfileLabel, 2, 4, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0.5);
		GraphicUtilities.addToGBL(this, passwordMismatch, 2, 8, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0.5);
		
		CustomLabel dummy = new CustomLabel("missing_login"); //Нужно задать какой-то текст, чтобы эта пустышка занила правильное количетсво места
		dummy.transparent();
		
		GraphicUtilities.addToGBL(this, dummy, 2, 6, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0.5);
	}
	
	private void setMiscellaneous() {
		miscellaneousPanel = new MiscellaneousPanel();
		
		GraphicUtilities.addToGBL(this, miscellaneousPanel, 3, 1, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 2);
	}
	
	private void setDefaultFocus() {
		loginField.requestFocusInWindow();
	}
	
	public void clearErrorMessages() {
		missingLoginLabel.transparent();
		existingProfileLabel.transparent();
		passwordMismatch.transparent();
		
		loginField.clearError();
		passwordField.clearError();
		confirmPasswordField.clearError();
	}
	
	public LoginField getLoginField() {
		return this.loginField;
	}
	
	public PasswordField getPasswordField() {
		return this.passwordField;
	}
	
	public PasswordField getConfirmPasswordField() {
		return this.confirmPasswordField;
	}
	
	@Override
	protected void processEvent(AWTEvent e) {
		if(e instanceof ErrorEvent) {
			switch (((ErrorEvent)e).getError()) {
			case 1: displayMissingErrorMessage();
				return;
			case 2: displayMismatchErrorMessage();
				return;
			case 50: displayExistingProfileErrorMessage();
				return;
			default:
				return;
			}
		}
		super.processEvent(e);
	}
	
	private void displayMissingErrorMessage() {
		missingLoginLabel.error();
		loginField.error();
	}
	
	private void displayMismatchErrorMessage() {
		passwordMismatch.error();
		passwordField.error();
		confirmPasswordField.error();
	}
	
	private void displayExistingProfileErrorMessage() {
		existingProfileLabel.error();
		loginField.error();
	}

	@Override
	public void translate() {
		super.translate();
	}
}
