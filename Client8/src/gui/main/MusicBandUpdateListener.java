package gui.main;

import java.util.*;

import item.MusicBand;

public class MusicBandUpdateListener implements DataUpdateListener{

	List<MusicBand> musicBands;
	List<DataUpdateListener> listeners = new ArrayList<DataUpdateListener>();
	
	@Override
	public void dataUpdated() {
		for(DataUpdateListener listner : listeners) {
			listner.dataUpdated();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setData(Collection<?> collection) {
		musicBands = (List<MusicBand>) collection;
	}

	@Override
	public Collection<?> getData() {
		return musicBands;
	}
	
	public void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		this.listeners.add(dataUpdateListener);
	}
}
