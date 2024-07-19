package gui.miscellaneous.profile;

import java.awt.*;
import java.io.File;

import application.User;
import gui.*;
import gui.event.ErrorEvent;
import gui.miscellaneous.PasswordField;

public class ProfileFrame extends CustomFrame implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	private static final String title = "Manage profile";
	private static final String pathToIcon = "content" + File.separator + "profile.jpg";
	private static final Dimension launchSize = new Dimension(576, 360);
	
	CustomLabel yourLoginLabel;
	CustomLabel loginLabel;
	CustomLabel enterPasswordLabel;
	CustomLabel confirmPasswordLabel;
	
	CustomLabel passwordMismatch;
	
	PasswordField passwordField;
	PasswordField confirmPasswordField;
	
	LogoutButton logoutButton;
	ChangePasswordButton changePasswordButton;

	public ProfileFrame() {
		super(title, pathToIcon, launchSize);
		
		this.center();
		
		this.setLayout(new GridBagLayout());
		
		this.setLabelsAndFields();
		this.setMainButtons();
		this.setErrorLabels();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void setLabelsAndFields() {
		yourLoginLabel = new CustomLabel("your_login");
		loginLabel = CustomLabel.createWithoutKey(User.getLogin());
		
		enterPasswordLabel = new CustomLabel("enter_password");
		passwordField = new PasswordField();
		
		confirmPasswordLabel = new CustomLabel("confirm_password");
		confirmPasswordField = new PasswordField();
		
		GraphicUtilities.addToGBL(this, yourLoginLabel, 0, 0, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, loginLabel, 2, 0, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		GraphicUtilities.addToGBL(this, enterPasswordLabel, 0, 1, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, passwordField, 2, 1, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		GraphicUtilities.addToGBL(this, confirmPasswordLabel, 0, 2, 2, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 1);
		GraphicUtilities.addToGBL(this, confirmPasswordField, 2, 2, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHWEST, 2, 1);
		
		CustomLabel dummy = new CustomLabel();
		
		GraphicUtilities.addToGBL(this, dummy, 5, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1, 1);
		
	}
	
	private void setMainButtons() {
		logoutButton = new LogoutButton();
		changePasswordButton = new ChangePasswordButton();
		changePasswordButton.addActionListener(new ChangeButtonActionListener(this));
		
		GraphicUtilities.addToGBL(this, logoutButton, 3, 4, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_END, 1, 1);
		GraphicUtilities.addToGBL(this, changePasswordButton, 4, 4, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_END, 1, 1);
		
		CustomLabel dummy = new CustomLabel();
		GraphicUtilities.addToGBL(this, dummy, 2, 4, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_END, 1, 1);
	}
	
	private void setErrorLabels() {
		passwordMismatch = new CustomLabel("mismatch_passwords");
		clearErrorMessages();
		
		GraphicUtilities.addToGBL(this, passwordMismatch, 2, 3, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0.5);
	}
	
	public void clearErrorMessages() {
		passwordMismatch.transparent();
		
		passwordField.clearError();
		confirmPasswordField.clearError();
	}
	
	private void displayMismatchErrorMessage() {
		passwordMismatch.error();
		passwordField.error();
		confirmPasswordField.error();
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
			case 2: displayMismatchErrorMessage();
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
