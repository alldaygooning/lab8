package application;

import module.ConnectionModule;
import wrapper.Request;

public class User {
	private static String login;
	private static String password;
	
	public static boolean isAuthorized() {
		if (login != null && password != null) {
			return true;
		}
		return false;
	}
	
	public static void appendCredentials(Request request) {
		request.login = login;
		request.password = password;
	}
	
	public static void login(String login, String password) {
		User.login = login;
		User.password = password;
		ConnectionModule.continueJsonRefresh();
	}
	
	public static void logout() {
		User.login = null;
		User.password = null;
	}
	
	
	public static String getLogin() {
		return login;
	}
	
	public static void setPassword(String password) {
		User.password = password;
	}
}
