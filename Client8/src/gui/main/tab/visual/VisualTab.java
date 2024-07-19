package gui.main.tab.visual;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingUtilities;

import gui.*;
import gui.main.DataUpdateListener;
import item.*;

public class VisualTab extends CustomTab implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	List<MusicBand> musicBands;
	
	HashMap<Integer, String> lastIcons;
	
	VisualTab visualTab;
	
	DataUpdateListener dataUpdateListener;
	
	private static final String titleKey = "visual_tab";
	
	int focuseId;

	public VisualTab() {
		super(titleKey);
		this.visualTab = this;
		
		this.setLayout(null);
		
	}
	
	@SuppressWarnings("unchecked")
	public void draw() {
		preserveFocus();
		getLastIcons();
		musicBands = (List<MusicBand>) dataUpdateListener.getData();
		BigDecimal totalNumberOfParticipants = musicBands.stream()
		        .map(band -> band.getNumberOfParticipants())
		        .map(BigDecimal::valueOf)
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		BandButton.setAverageNumberOfParticipants((totalNumberOfParticipants.divide(new BigDecimal(musicBands.size()), RoundingMode.HALF_UP).longValue()));
		this.removeAll();
		for(MusicBand band : musicBands) {
			BandButton bandButton;
			if(lastIcons.containsKey(band.getId())) {
				bandButton = new BandButton(band, lastIcons.get(band.getId()));
			}
			else {
				bandButton = new BandButton(band);
			}
			this.add(bandButton);
			if(band.getId() == focuseId) {
				bandButton.requestFocusInWindow();
			}
		}
		this.repaint();
	}
	
	private void getLastIcons() {
		lastIcons = new HashMap<Integer, String>();
		for(Component component : this.getComponents()) {
			if(component instanceof BandButton) {
				BandButton bandButton = (BandButton)component;
				String lastIcon = bandButton.getLastIcon();
				if (lastIcon.equals(BandButton.firstIcon)) {
					continue;
				}
				lastIcons.put(bandButton.getBandId(), lastIcon);
			}
		}
	}
	
	private void preserveFocus() {
		Window window = SwingUtilities.getWindowAncestor(this);
		Component component = window.getFocusOwner();
		if (component instanceof BandButton) {
			BandButton bandButton = (BandButton)component;
			focuseId = bandButton.getBandId();
		}
	}
	
	@Override
	public void translate() {
		TranslatedContainer.super.translate();
		super.translate();
	}
	
	public void addDataUpdateListener(DataUpdateListener dataUpdateListener) {
		this.dataUpdateListener = dataUpdateListener;
	}
}
