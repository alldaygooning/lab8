package gui.main.tab.visual;

import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import application.*;
import gui.*;
import item.*;
import module.LocalizationModule;

public class BandButton extends CustomButton implements TranslatedContainer{
	private static final long serialVersionUID = 1L;
	
	static double averageNumberOfParticipants;
	static final String pathToIcon = "content" + File.separator + "skeleton" + File.separator + "frame_";
	public static final String firstIcon = "content" + File.separator + "skeleton" + File.separator + "frame_0.png";
	
	double sizeModifier;
	MusicBand band;
	
	private final int baseWidth = 100;
	private final int baseHeight = 110;
	private final int maxWidth = 200;
	private final int maxHeight = 200;
	private final int minWidth = 5;
	private final int minHeight = 5;
	
	int scaleWidth;
	int scaleHeight;
	
	int finalWidth;
	int finalHeight;
	
	int x;
	int y;
	
	String lastIcon = pathToIcon + 0 + ".png";
	
	BandButton bandButton;
	
	public BandButton(MusicBand band, String lastIcon) {
		this(band);
		this.lastIcon = lastIcon;
		this.setScaledIcon(lastIcon, bandButton.scaleWidth, bandButton.scaleHeight, Image.SCALE_FAST, band.getOwner().equals(User.getLogin()));//Повторяющаяся строка, пока не подумал как исправить 
	}
	
	public BandButton(MusicBand band) {
		this.bandButton = this;
		this.band = band;
		
		x = (int) Math.round(band.getX());
		y = (int) Math.round(band.getY());
		
		sizeModifier = (band.getNumberOfParticipants()/averageNumberOfParticipants);
		
		finalWidth = Math.min(maxWidth, (int) (baseWidth * sizeModifier));
		finalHeight = Math.min(maxHeight, (int) (baseHeight * sizeModifier));
		
		finalWidth = Math.max(minWidth, (int) (baseWidth * sizeModifier));
		finalHeight = Math.max(minHeight, (int) (baseHeight * sizeModifier));
		
		scaleWidth = Math.max(bandButton.finalWidth - 5, bandButton.minWidth);
		scaleHeight = Math.max(bandButton.finalHeight - 5, bandButton.minHeight);
		
		this.setBounds(x, y, finalWidth, finalHeight);
		this.setScaledIcon(lastIcon, bandButton.scaleWidth, bandButton.scaleHeight, Image.SCALE_FAST, band.getOwner().equals(User.getLogin()));
		
		setListeners();
		setToolTip();
	}
	
	public static void setAverageNumberOfParticipants(double average) {
		averageNumberOfParticipants = average;
	}
	
	private void setListeners() {
		this.addFocusListener(new FocusAdapter() {
			Timer timer = new Timer(50, new ActionListener() {
				int i = 1;
				@Override
				public void actionPerformed(ActionEvent e) {
					String path = pathToIcon + i + ".png";
					bandButton.lastIcon = path;
					String owner = bandButton.band.getOwner();
					bandButton.setScaledIcon(path, bandButton.scaleWidth, bandButton.scaleHeight, Image.SCALE_SMOOTH, owner.equals(User.getLogin()));
					i++;
					if (i > 43) {
						i = 1;
					}
				}
			});
			@Override
			public void focusGained(FocusEvent e) {
				timer.setRepeats(true);
				timer.start();
				super.focusGained(e);
			}
			@Override
			public void focusLost(FocusEvent e) {
				timer.stop();
				super.focusLost(e);
			}
		});
	}
	
	public String getLastIcon() {
		return this.lastIcon;
	}
	
	public int getBandId() {
		return this.band.getId();
	}
	
	public void setToolTip() {
		LocalizationModule lm = Application.getLocalizationModule();
		String text = lm.getString("display_id") + ": " + band.getId() + "\n"
				+ lm.getString("display_creation_date") + ": " + lm.getFormattedDate(Date.from(band.getCreationDate().atStartOfDay(ZoneId.systemDefault()).toInstant())) + "\n"
				+ lm.getString("enter_name") + ": " + band.getName() + "\n"
				+ lm.getString("enter_x") + ": " + lm.getFormattedNumber(band.getX()) + " " + lm.getString("enter_y") + ": " + lm.getFormattedNumber(band.getY()) + "\n"
				+ lm.getString("enter_participants")+ ": " + lm.getFormattedLong(band.getNumberOfParticipants()) + "\n"
				+ lm.getString("enter_genre") + ": " + band.getGenre() + "\n\n"
				+ lm.getString("enter_frontman_name") + ": " + band.getFrontMan().getName() + "\n"
				+ lm.getString("enter_frontman_height") + ": " + lm.getFormattedLong(band.getFrontMan().getHeight()) + "\n"
				+ lm.getString("enter_frontman_weight") + ": " + lm.getFormattedNumber(band.getFrontMan().getWeight()) + "\n"
				+ lm.getString("enter_frontman_color") + ": " + band.getFrontMan().getHairColor() + "\n"
				+ lm.getString("enter_frontman_country") + ": " + band.getFrontMan().getNationality();
		this.setToolTipText(text);
	}
	
	@Override
	public void translate() {
		this.setToolTip();
	}
}
