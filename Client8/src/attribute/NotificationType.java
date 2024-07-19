package attribute;

public enum NotificationType {
	INFO,
	WARNING,
	ERROR,
	SUCCESS;
	
	public String getStringKey() {
		switch(this) {
		case ERROR: return "notification_error";
		case INFO: return "notification_info";
		case SUCCESS: return "notification_success";
		default: return null;
		}
	}
}
