package gui.login;

import java.awt.event.*;
import java.util.*;

import application.*;
import attribute.NotificationType;
import exception.*;
import gui.event.ErrorEvent;
import gui.event.NotificationDisplayRequestEvent;
import gui.main.MainFrame;
import gui.miscellaneous.*;

public class LoginButtonActionListner implements ActionListener{
	
	LoginFrame loginFrame;
	
	public LoginButtonActionListner(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		loginFrame.clearErrorMessages();
		
		LoginField loginField = loginFrame.getLoginField();
		PasswordField passwordField = loginFrame.getPasswordField();
		
		String login = loginField.getText();
		if (login.isEmpty()) {
			loginFrame.dispatchEvent(new ErrorEvent(loginField, 1));
			return;
		}
		
		String password = new String(passwordField.getPassword());
		
		String command = "login";
		
		List<String> args = new ArrayList<String>();
		args.add(command);
		args.add(login);
		args.add(password);
		
		try {
			Invoker.silentExecute(args);
			loginFrame.dispose();
			new MainFrame();
		}catch(ConnectionException excetion) {
			loginFrame.dispatchEvent(new NotificationDisplayRequestEvent(loginFrame, NotificationType.ERROR, 4));
		}catch(ExecutionCancelled exception) {
			loginFrame.dispatchEvent(new ErrorEvent(loginFrame, exception.reason_id));
		}
	}
}
