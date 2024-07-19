package item;

import java.util.*;

public class User {
	
	public static List<User> users = new ArrayList<User>();
	
	String name;
	String password;
	String access;
	
	public User(String name, String password, String access) {
		this.name = name;
		this.password = password;
		this.access = access;
	}
	
	public void changePassword(String newPassword) {
		this.password = newPassword;
	}
	
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getAccess() {
		return access;
	}
	
	public static User findUserByName(String name) {
		return users.stream().filter(user -> user.getName().equals(name)).findAny().orElse(null);
	}
}
