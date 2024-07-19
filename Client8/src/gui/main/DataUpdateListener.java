package gui.main;

import java.util.Collection;

public interface DataUpdateListener {
	public void dataUpdated();
	
	public void setData(Collection<?> collection);
	
	public Collection<?> getData();
}
