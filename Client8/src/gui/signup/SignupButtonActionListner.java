package gui.signup;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import application.Invoker;
import attribute.NotificationType;
import exception.ConnectionException;
import exception.ExecutionCancelled;
import gui.event.ErrorEvent;
import gui.event.NotificationDisplayRequestEvent;
import gui.login.*;
import gui.miscellaneous.*;

public class SignupButtonActionListner implements ActionListener{

	SignupFrame signupFrame;
	
	public SignupButtonActionListner(SignupFrame signupFrame) {
		this.signupFrame = signupFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		signupFrame.clearErrorMessages();
		
		LoginField loginField = signupFrame.getLoginField();
		PasswordField passwordField = signupFrame.getPasswordField();
		PasswordField confirmPassowrdField = signupFrame.getConfirmPasswordField();
		
		String login = loginField.getText();
		if (login.isEmpty()) {
			signupFrame.dispatchEvent(new ErrorEvent(loginField, 1));
			return;
		}
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPassowrdField.getPassword());
		
		if(!password.equals(confirmPassword)) {
			signupFrame.dispatchEvent(new ErrorEvent(loginField, 2));
			return;
		}
		String command = "signup";
		List<String> args = new ArrayList<String>();
		args.add(command);
		args.add(login);
		args.add(password);
		args.add(confirmPassword);
		try {
			Invoker.silentExecute(args);
			signupFrame.dispatchEvent(new NotificationDisplayRequestEvent(signupFrame, NotificationType.SUCCESS, 201));
		}catch(ConnectionException excetion) {
			signupFrame.dispatchEvent(new NotificationDisplayRequestEvent(signupFrame, NotificationType.ERROR, 4));
		}catch(ExecutionCancelled exception) {
			signupFrame.dispatchEvent(new ErrorEvent(signupFrame, exception.reason_id));
		}
	}
}
