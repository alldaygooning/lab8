package command;

public interface Authorization {
	public default boolean requiresAuthorization() {
		return true;
	}
}
