package attribute;

public enum AccessLevel {
	ADMIN("admin"),
	USER("user");
	private final String name;
	private AccessLevel(String name) {
		this.name = name;
	}
}
