package gui.miscellaneous.profile;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import application.Invoker;
import attribute.NotificationType;
import exception.ConnectionException;
import exception.ExecutionCancelled;
import gui.event.ErrorEvent;
import gui.event.NotificationDisplayRequestEvent;
import gui.miscellaneous.PasswordField;

public class ChangeButtonActionListener implements ActionListener{
	
	ProfileFrame profileFrame;

	public ChangeButtonActionListener(ProfileFrame profileFrame) {
		this.profileFrame = profileFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		profileFrame.clearErrorMessages();
		
		PasswordField passwordField = profileFrame.getPasswordField();
		PasswordField confirmPassowrdField = profileFrame.getConfirmPasswordField();
		
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPassowrdField.getPassword());
		
		if(!password.equals(confirmPassword)) {
			profileFrame.dispatchEvent(new ErrorEvent(this, 2));
			return;
		}
		
		String command = "changepassword";
		List<String> args = new ArrayList<String>();
		args.add(command);
		args.add(password);
		args.add(confirmPassword);
		try {
			Invoker.silentExecute(args);
			profileFrame.dispatchEvent(new NotificationDisplayRequestEvent(profileFrame, NotificationType.SUCCESS, 202));
		}catch(ConnectionException excetion) {
			profileFrame.dispatchEvent(new NotificationDisplayRequestEvent(profileFrame, NotificationType.ERROR, 4));
		}catch(ExecutionCancelled exception) {
			profileFrame.dispatchEvent(new ErrorEvent(profileFrame, exception.reason_id));
		}
	}
}
